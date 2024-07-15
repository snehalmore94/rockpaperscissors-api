package com.game.rockpaperscissor.model;

import com.game.rockpaperscissor.enums.GameResult;
import com.game.rockpaperscissor.enums.GameStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * Represents a game of Rock-Paper-Scissors.
 */
@Data
@NoArgsConstructor
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus = GameStatus.ONGOING;

    private String playerOneName;
    private Integer playerOneScore = 0;

    private String playerTwoName;
    private Integer playerTwoScore = 0;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Round> rounds = new ArrayList<>();

    /**
     * Constructs a new Game with the specified player one name.
     *
     * @param playerOneName the name of player one
     */
    public Game(String playerOneName) {
        this.playerOneName = playerOneName;
    }

    /**
     * Adds player two to the game.
     *
     * @param playerTwoName the name of player two
     * @throws IllegalStateException if the game already has two players
     */
    public void addPlayer(String playerTwoName) {
        if (this.playerTwoName != null) {
            throw new IllegalStateException("Game already has two players");
        }
        this.playerTwoName = playerTwoName;
    }

    /**
     * Checks if a player with the given name is in the game.
     *
     * @param playerName the name of the player to check
     * @return true if the player is in the game, false otherwise
     */
    public boolean isPlayerInGame(String playerName) {
        return playerName.equals(playerOneName) || playerName.equals(playerTwoName);
    }

    /**
     * Checks if both players have joined the game.
     *
     * @return true if both players have joined, false otherwise
     */
    public boolean areBothPlayersJoined() {
        return playerOneName != null && playerTwoName != null;
    }

    /**
     * Adds a round to the game.
     *
     * @param round the round to add
     */
    public void addRound(Round round) {
        this.rounds.add(round);
    }

    /**
     * Retrieves the latest round of the game.
     *
     * @return an Optional containing the latest round, or an empty Optional if no rounds exist
     */
    public Optional<Round> getLatestRound() {
        if (rounds.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(rounds.get(rounds.size() - 1));
    }

    /**
     * Sets the result of the latest round.
     *
     * @param result the result of the game
     */
    public void setResult(GameResult result) {
        getLatestRound().ifPresent(latestRound -> latestRound.setResult(result));
    }

    /**
     * Sets the status of the game.
     *
     * @param status the status of the game
     */
    public void setStatus(GameStatus status) {
        this.gameStatus = status;
    }
}

