package com.game.rockpaperscissor.exception;

/**
 * Exception thrown when a game is already finished.
 */
public class GameFinishedException extends RuntimeException{

    /**
     * Constructs a new GameFinishedException
     * @param message
     */
    public GameFinishedException(String message) {
        super(message);
    }
}
