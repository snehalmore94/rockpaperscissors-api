package com.game.rockpaperscissor.model;

import com.game.rockpaperscissor.enums.Choice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Move {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Move cannot be null")
    private Choice move;
}

