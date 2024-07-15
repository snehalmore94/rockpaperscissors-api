package com.game.rockpaperscissor.controller;

import com.game.rockpaperscissor.annotations.ApiResponsesCommon;
import com.game.rockpaperscissor.dto.JoinGameRequest;
import com.game.rockpaperscissor.model.Game;
import com.game.rockpaperscissor.model.Move;
import com.game.rockpaperscissor.model.Player;
import com.game.rockpaperscissor.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/games")
@Tag(name = "Game API", description = "API for managing Rock Paper Scissor games")
public class GameController {
    private final GameService gameService;
    Logger logger = LoggerFactory.getLogger(GameController.class);


    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Create a new game and send the game ID via email.
     *
     * @param player The player initiating the game.
     * @return ResponseEntity containing a message with the game ID.
     */
    @PostMapping
    @Operation(summary = "Create a new game", description = "Create a new game and send the game ID via email")
    @ApiResponsesCommon
    public ResponseEntity<String> createGame(@RequestBody Player player) {
        logger.info("Creating a new game for player: {}", player.getName());
        String player2Email = player.getEmailId();
        Game game = gameService.createGame(player.getName(), player2Email);
        logger.info("Game created with ID: {}", game.getId());
        return ResponseEntity.ok("Game created and ID is sent." + " Game ID is : " + game.getId());
    }

    /**
     * Join an existing game by providing the game ID and name of the second player.
     *
     * @param id The ID of the game to join.
     * @param request The request containing the name of the second player.
     * @return ResponseEntity containing the updated game state.
     */
    @PutMapping("/{id}/join")
    @Operation(summary = "Join an existing game", description = "Join an existing game by providing the game ID and name of the second player")
    @ApiResponsesCommon
    public ResponseEntity<Game> joinGame(@PathVariable Long id, @RequestBody JoinGameRequest request) {
        logger.info("Player {} is attempting to join game with ID: {}", request.getPlayerTwoName(), id);
        Game game = gameService.joinGame(id, request.getPlayerTwoName());
        logger.info("Player {} joined game with ID: {}", request.getPlayerTwoName(), id);
        return ResponseEntity.ok(game);
    }

    /**
     * Make a move in the game by providing the game ID and your move.
     *
     * @param id The ID of the game.
     * @param move The move made by the player.
     * @return ResponseEntity containing the updated game state.
     */
    @PutMapping("/{id}/move")
    @Operation(summary = "Make a move in the game", description = "Make a move in the game by providing the game ID and your move by selecting either of ROCK, PAPER, or SCISSORS")
    @ApiResponses({
            @ApiResponse(responseCode = "409", description = "Both players must join the game before making a move"),
            @ApiResponse(responseCode = "410", description = "The game is already finished. No further moves are allowed."),
    })
    @ApiResponsesCommon
    public ResponseEntity<Game> makeMove(@PathVariable Long id, @RequestBody Move move) {
        logger.info("Player {} is making a move in game with ID: {}", move.getName(), id);
        Game game = gameService.makeMove(id, move.getName(), move.getMove());
        logger.info("Move made by player {} in game with ID: {}", move.getName(), id);
        return ResponseEntity.ok(game);
    }

    /**
     * Get the details of the game by providing the game ID.
     *
     * @param id The ID of the game.
     * @return ResponseEntity containing the game details.
     */
    @GetMapping("/{id}/result")
    @Operation(summary = "Get the game details", description = "Get the details of the game by providing the game ID")
    @ApiResponsesCommon
    public ResponseEntity<Game> getGame(@PathVariable Long id) {
        logger.info("Retrieving game details with ID: {}", id);
        Game game = gameService.getGame(id);
        logger.info("Game Details retrieved with ID: {}", id);
        return ResponseEntity.ok(game);
    }
}
