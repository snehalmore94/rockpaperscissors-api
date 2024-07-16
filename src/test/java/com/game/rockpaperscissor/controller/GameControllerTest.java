package com.game.rockpaperscissor.controller;

import com.game.rockpaperscissor.dto.JoinGameRequest;
import com.game.rockpaperscissor.exception.GameNotFoundException;
import com.game.rockpaperscissor.model.Game;
import com.game.rockpaperscissor.model.Move;
import com.game.rockpaperscissor.model.Player;
import com.game.rockpaperscissor.service.GameService;
import com.game.rockpaperscissor.enums.Choice;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test case to verify the behavior of the createGame method in the GameController.
     */
    @Test
    public void testCreateGameWhenAndReturnGameId() throws Exception {

        Player player = new Player("PlayerOne", "playerone@example.com");
        Game game = new Game("PlayerOne");
        game.setId(1L);

        Mockito.when(gameService.createGame(Mockito.anyString(), Mockito.anyString())).thenReturn(game);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isOk())
                .andExpect(content().string("Game created and ID is sent. Game ID is : 1"));
    }

    /**
     * Test case to verify the behavior of the joinGame method in the GameController.
     */
    @Test
    public void testJoinGameAndReturnUpdatedGame() throws Exception {
        Long gameId = 1L;
        JoinGameRequest request = new JoinGameRequest();
        request.setPlayerTwoName("PlayerTwo");

        Game game = new Game("PlayerOne");
        game.setId(gameId);
        game.addPlayer("PlayerTwo");

        Mockito.when(gameService.joinGame(Mockito.eq(gameId), Mockito.anyString())).thenReturn(game);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/games/{id}/join", gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerTwoName").value("PlayerTwo"));
    }

    /**
     * Test case to verify the behavior of the makeMove method in the GameController.
     */
    @Test
    public void testMakeMoveAndReturnUpdatedGame() throws Exception {
        Long gameId = 1L;
        Move move = new Move();
        move.setName("PlayerOne");
        move.setMove(Choice.ROCK);

        Game game = new Game("PlayerOne");
        game.setId(gameId);

        Mockito.when(gameService.makeMove(Mockito.eq(gameId), Mockito.anyString(), Mockito.any(Choice.class))).thenReturn(game);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/games/{id}/move", gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(move)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(gameId));
    }

    /**
     * Test case to verify the behavior of the getGameResult method in the GameController.
     */
    @Test
    public void testGetGameAndReturnGameResult() throws Exception {
        Long gameId = 1L;
        Game game = new Game("PlayerOne");
        game.setId(gameId);

        Mockito.when(gameService.getGame(Mockito.eq(gameId))).thenReturn(game);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/games/{id}/result", gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(gameId));
    }
}
