package com.game.rockpaperscissor.service;

import com.game.rockpaperscissor.enums.Choice;
import com.game.rockpaperscissor.exception.GameNotFoundException;
import com.game.rockpaperscissor.model.Game;
import com.game.rockpaperscissor.repository.GameRepository;
import com.game.rockpaperscissor.service.impl.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test clas to test GameServiceImpl service class
 */
public class GameServiceImplTest {

    private GameServiceImpl gameService;

    @Mock
    private EmailService emailService;

    @Mock
    private GameResultService gameResultService;

    @Mock
    private GameRepository gameRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gameService = new GameServiceImpl(emailService, gameResultService, gameRepository);
    }

    @Test
    public void testCreateGame() {
        String playerName = "Player1";
        String player2Email = "player2@example.com";
        Game game = new Game(playerName);
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        Game createdGame = gameService.createGame(playerName, player2Email);

        assertNotNull(createdGame);
        assertEquals(playerName, createdGame.getPlayerOneName());
        verify(gameRepository, times(1)).save(any(Game.class));
        verify(emailService, times(1)).sendEmail(eq(player2Email), eq("Join Rock Paper Scissors Game"), anyString());
    }

    @Test
    public void testJoinGame() {
        Long gameId = 1L;
        String playerTwoName = "Player2";
        Game game = new Game("Player1");
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        Game joinedGame = gameService.joinGame(gameId, playerTwoName);

        assertNotNull(joinedGame);
        assertEquals(playerTwoName, joinedGame.getPlayerTwoName());
        verify(gameRepository, times(1)).findById(gameId);
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    public void testJoinGameWhenGameNotFound() {
        Long gameId = 1L;
        String playerTwoName = "Player2";
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> gameService.joinGame(gameId, playerTwoName));
        verify(gameRepository, times(1)).findById(gameId);
        verify(gameRepository, never()).save(any(Game.class));
    }

    @Test
    public void testMakeMove() {
        Long gameId = 1L;
        String playerName = "Player1";
        Choice move = Choice.ROCK;
        Game game = new Game("Player1");
        game.addPlayer("Player2");

        when(gameRepository.findById(eq(gameId))).thenReturn(Optional.<Game>of(game));
        when(gameRepository.save(any(Game.class))).thenReturn(game);

            Game updatedGame = gameService.makeMove(gameId, playerName, move);
            assertNotNull(updatedGame);
            verify(gameRepository, times(1)).findById(gameId);
            verify(gameRepository, times(1)).save(any(Game.class));

    }
    @Test
    public void testGetGame() {
        Long gameId = 1L;
        Game game = new Game("Player1");
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        Game retrievedGame = gameService.getGame(gameId);
        assertNotNull(retrievedGame);
        assertEquals(game, retrievedGame);
        verify(gameRepository, times(1)).findById(gameId);
    }

}