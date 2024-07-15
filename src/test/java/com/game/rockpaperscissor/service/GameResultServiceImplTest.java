package com.game.rockpaperscissor.service;

import com.game.rockpaperscissor.enums.Choice;
import com.game.rockpaperscissor.enums.GameResult;
import com.game.rockpaperscissor.model.Game;
import com.game.rockpaperscissor.model.Round;
import com.game.rockpaperscissor.service.impl.GameResultServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class to test GameResultServiceImpl service class
 */
@ExtendWith(MockitoExtension.class)
public class GameResultServiceImplTest {

    private GameResultServiceImpl gameResultService;

    @BeforeEach
    public void setUp() {
        gameResultService = new GameResultServiceImpl();
    }

    @Test
    public void testDetermineWinnerWhenBothPlayersMakeSameMoveGameIsTie() {
        Game game = new Game("Player1");
        game.addPlayer("Player2");
        Round round = new Round(Choice.ROCK, Choice.ROCK, null);
        game.addRound(round);
        gameResultService.determineWinner(game);
        assertEquals(GameResult.TIE, game.getLatestRound().get().getResult());
    }

    @Test
    public void testDetermineWinnerWhenPlayerOneWins() {
        Game game = new Game("Player1");
        game.addPlayer("Player2");
        Round round = new Round(Choice.ROCK, Choice.SCISSORS, null);
        game.addRound(round);
        gameResultService.determineWinner(game);
        assertEquals(GameResult.PLAYER1_WINS, game.getLatestRound().get().getResult());
    }

    @Test
    public void testDetermineWinnerWhenPlayerTwoWins() {
        Game game = new Game("Player1");
        game.addPlayer("Player2");
        Round round = new Round(Choice.ROCK, Choice.PAPER, null);
        game.addRound(round);
        gameResultService.determineWinner(game);
        assertEquals(GameResult.PLAYER2_WINS, game.getLatestRound().get().getResult());
    }

}