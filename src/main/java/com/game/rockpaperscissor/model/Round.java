package com.game.rockpaperscissor.model;

import com.game.rockpaperscissor.enums.Choice;
import com.game.rockpaperscissor.enums.GameResult;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Round class represents a round in a game of Rock-Paper-Scissors.
 * It contains information about the moves made by both players and the result of the round.
 */
@Data
@NoArgsConstructor
@Entity
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Choice playerOneMove;

    @Enumerated(EnumType.STRING)
    private Choice playerTwoMove;

    @Enumerated(EnumType.STRING)
    private GameResult result;

    public Round(Choice playerOneMove, Choice playerTwoMove, GameResult result) {
        this.playerOneMove = playerOneMove;
        this.playerTwoMove = playerTwoMove;
        this.result = result;
    }
}
