package com.game.rockpaperscissor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Player class represents a player in a game.
 * It contains the first player name and email ID of the second Player.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private String name;
    private String emailId;
}
