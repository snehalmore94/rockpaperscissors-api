package com.game.rockpaperscissor.dto;

import lombok.Data;

/**
 * DTO for joining an existing game.
 * This class is used to encapsulate the data required for a player to join an existing game.
 */
@Data
public class JoinGameRequest {

    /**
     * The name of the second player who wants to join the game.
     */
    private String playerTwoName;

}