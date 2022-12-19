package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-Tier")
class PlayerTest {
    private Player CuT;
    private final String PLAYER_NAME = "John-E";

    @BeforeEach
    public void setup(){
        CuT = new Player(PLAYER_NAME);
    }

    @Test
    public void setInGameTest() {
        CuT.setInGame(true);
        assertTrue(CuT.isInGame());
        CuT.setInGame(false);
        assertFalse(CuT.isInGame());
    }

    
}