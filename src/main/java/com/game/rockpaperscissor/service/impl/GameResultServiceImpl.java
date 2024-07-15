package com.game.rockpaperscissor.service.impl;

import com.game.rockpaperscissor.enums.Choice;
import com.game.rockpaperscissor.enums.GameResult;
import com.game.rockpaperscissor.model.Game;
import com.game.rockpaperscissor.model.Round;
import com.game.rockpaperscissor.service.GameResultService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameResultServiceImpl implements GameResultService {

    /**
     * Determines the winner of the game based on the moves made by the players in the latest round.
     * @param game
     */
    @Override
    public void determineWinner(Game game) {
        Optional<Round> latestRoundOpt = game.getLatestRound();
        if (latestRoundOpt.isEmpty()) {
            throw new IllegalStateException("No rounds available to determine the winner");
        }
        Round latestRound = latestRoundOpt.get();
        Choice move1 = latestRound.getPlayerOneMove();
        Choice move2 = latestRound.getPlayerTwoMove();

        if (move1 == null || move2 == null) {
            throw new IllegalStateException("Both players must make a move before determining the winner");
        }

        GameResult result;
        String resultMessage;

        if (move1.equals(move2)) {
            result = GameResult.TIE;
            resultMessage = GameResult.TIE.getMessage();
        } else {
            result = switch (move1) {
                case ROCK -> (move2 == Choice.SCISSORS) ? GameResult.PLAYER1_WINS : GameResult.PLAYER2_WINS;
                case PAPER -> (move2 == Choice.ROCK) ? GameResult.PLAYER1_WINS : GameResult.PLAYER2_WINS;
                case SCISSORS -> (move2 == Choice.PAPER) ? GameResult.PLAYER1_WINS : GameResult.PLAYER2_WINS;
            };
            resultMessage = (result == GameResult.PLAYER1_WINS) ? game.getPlayerOneName() + " wins!" : game.getPlayerTwoName() + " wins!";
        }

        game.setResult(result);
    }
}