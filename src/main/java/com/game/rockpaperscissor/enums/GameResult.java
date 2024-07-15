package com.game.rockpaperscissor.enums;

import lombok.Getter;

/**
 * Enum representing the possible results of a Rock-Paper-Scissors game.
 */
@Getter
public enum GameResult {
    /**
     * Represents a tie result in the game.
     */
    TIE("It's a tie!"),

    /**
     * Represents a win for Player 1.
     */
    PLAYER1_WINS("Player 1 wins!"),

    /**
     * Represents a win for Player 2.
     */
    PLAYER2_WINS("Player 2 wins!");

    /**
     * The message associated with the game result.
     */
    private final String message;

    /**
     * Constructor for the GameResult enum.
     *
     * @param message The message associated with the game result.
     */
    GameResult(String message) {
        this.message = message;
    }

}
