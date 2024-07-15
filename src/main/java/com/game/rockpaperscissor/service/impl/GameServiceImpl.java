package com.game.rockpaperscissor.service.impl;

import com.game.rockpaperscissor.enums.Choice;
import com.game.rockpaperscissor.enums.GameResult;
import com.game.rockpaperscissor.enums.GameStatus;
import com.game.rockpaperscissor.exception.GameFinishedException;
import com.game.rockpaperscissor.exception.GameNotFoundException;
import com.game.rockpaperscissor.model.Game;
import com.game.rockpaperscissor.model.Round;
import com.game.rockpaperscissor.repository.GameRepository;
import com.game.rockpaperscissor.service.EmailService;
import com.game.rockpaperscissor.service.GameResultService;
import com.game.rockpaperscissor.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The GameServiceImpl class is an implementation of the GameService interface.
 * It provides the functionality to create and manage games of Rock-Paper-Scissors.
 */
@Service
public class GameServiceImpl implements GameService {

    private final EmailService emailService;
    private final GameResultService gameResultService;
    private final GameRepository gameRepository;
    Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    @Autowired
    public GameServiceImpl(EmailService emailService, GameResultService gameResultService, GameRepository gameRepository) {
        this.emailService = emailService;
        this.gameResultService = gameResultService;
        this.gameRepository = gameRepository;
    }

    /**
     * Creates a new game with the specified player name and sends an email to the second player.
     *
     * @param playerName    The name of the first player.
     * @param player2Email  The email address of the second player.
     * @return The created game.
     */
    @Override
    public Game createGame(final String playerName, final String player2Email) {
        Game game = new Game(playerName);
        gameRepository.save(game);
        emailService.sendEmail(player2Email, "Join Rock Paper Scissors Game", "Game ID: " + game.getId());
        return game;
    }

    /**
     * Joins an existing game with the specified game ID and player name.
     *
     * @param id            The ID of the game to join.
     * @param playerTwoName The name of the second player.
     * @return The joined game.
     * @throws GameNotFoundException if the game with the specified ID is not found.
     */
    @Override
    public Game joinGame(final Long id, final String playerTwoName) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException("Game not found"));
        game.addPlayer(playerTwoName);
        gameRepository.save(game);
        return game;
    }

    /**
     * Makes a move in the specified game with the given player name and move choice.
     * @param id
     * @param playerName
     * @param move
     * @return
     */
    @Override
    public Game makeMove(final Long id, final String playerName, final Choice move) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException("Game not found"));

        validateGameStatus(game);
        validatePlayersJoined(game);
        validatePlayerInGame(game, playerName);

        Round latestRound = createOrGetLatestRound(game);
        setPlayerMove(game,latestRound, playerName, move);

        if (latestRound.getPlayerOneMove() != null && latestRound.getPlayerTwoMove() != null) {
            updateScoresAndCheckGameFinished(game, latestRound);
        }

        gameRepository.save(game);
        return game;
    }

    private void validateGameStatus(Game game) {
        if (game.getGameStatus() == GameStatus.FINISHED) {
            logger.error("The game is already finished. No further moves are allowed.");
            throw new GameFinishedException("The game is already finished. No further moves are allowed.");
        }
    }

    private void validatePlayersJoined(Game game) {
        if (!game.areBothPlayersJoined()) {
            logger.error("Both players must join the game before making a move");
            throw new IllegalStateException("Both players must join the game before making a move");
        }
    }

    private void validatePlayerInGame(Game game, String playerName) {
        if (!game.isPlayerInGame(playerName)) {
            logger.error("Player not part of the game");
            throw new IllegalArgumentException("Player not part of the game");
        }
    }

    private Round createOrGetLatestRound(Game game) {
        Optional<Round> latestRoundOpt = game.getLatestRound();
        if (latestRoundOpt.isEmpty() || (latestRoundOpt.get().getPlayerOneMove() != null && latestRoundOpt.get().getPlayerTwoMove() != null)) {
            Round latestRound = new Round();
            game.addRound(latestRound);
            return latestRound;
        } else {
            return latestRoundOpt.get();
        }
    }

    private void setPlayerMove(Game game, Round round, String playerName, Choice move) {
        if (playerName.equals(game.getPlayerOneName())) {
            round.setPlayerOneMove(move);
        } else {
            round.setPlayerTwoMove(move);
        }
    }

    private void updateScoresAndCheckGameFinished(Game game, Round round) {
        gameResultService.determineWinner(game);

        if (round.getResult() == GameResult.PLAYER1_WINS) {
            game.setPlayerOneScore(game.getPlayerOneScore() + 1);
        } else if (round.getResult() == GameResult.PLAYER2_WINS) {
            game.setPlayerTwoScore(game.getPlayerTwoScore() + 1);
        }

        if (game.getPlayerOneScore() == 3 || game.getPlayerTwoScore() == 3 || game.getPlayerOneScore().equals(game.getPlayerTwoScore())) {
            game.setStatus(GameStatus.FINISHED);
        }
    }

    /**
     * Retrieves the game Result with the specified ID.
     * @param id
     * @return
     */
    @Override
    public Game getGame(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException("Game not found"));
    }
}
