package com.game.rockpaperscissor.service;

import com.game.rockpaperscissor.enums.Choice;
import com.game.rockpaperscissor.exception.EmailSendingException;
import com.game.rockpaperscissor.model.Game;
import jakarta.mail.MessagingException;

/**
 * The GameService interface provides methods performing game Actions.
 */
public interface GameService {
    Game createGame(String playerName, String player2Email) throws EmailSendingException;

    Game joinGame(Long id, String playerName);

    Game makeMove(Long id, String playerName, Choice move);

    Game getGame(Long id);
}
