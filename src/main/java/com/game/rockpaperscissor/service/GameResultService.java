package com.game.rockpaperscissor.service;

import com.game.rockpaperscissor.model.Game;

/**
 * The GameResultService interface provides methods to determine Winner.
 */
public interface GameResultService {
    void determineWinner(Game game);
}
