package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Model-Tier")
class BoardTest {

    /**
     * The component-under-test (CuT).
     */
    private Board CuT;
    private Player white_player;
    private Player red_player;

    private static final int BOARD_SIZE = 8;
    private static final int LOWER_BOUND = 0;
    private static final int UPPER_BOUND = 7;
    private static final int INVALID_ROW_INDEX_1 = -1;
    private static final int INVALID_ROW_INDEX_2 = 8;

    @BeforeEach
    public void setup(){
        white_player = new Player("white");
        red_player = new Player("red");
        CuT = new Board(white_player, red_player);
    }

    /**
     * Tests that all the get player methods work, specifically
     * looking for the first active player to be the red player.
     */
    @Test
    public void testGetPlayers(){
        assertEquals(CuT.getRedPlayer(), red_player);
        assertEquals(CuT.getWhitePlayer(), white_player);
    }

    /**
     * Tests if the spaces on the board that should have a piece
     * on them at the beginning of the game have a piece.
     */
    @Test
    public void testBoardPiecePlacement(){
        for (int row = 0; row < BOARD_SIZE; row++){
            for (int col = 0; col < BOARD_SIZE; col++){
                if (row < 3){ // the red player's side of the board with their pieces
                    if(CuT.getRow(row)[col].isDarkSquare()){
                        assertNotNull(CuT.getRow(row)[col].getPiece(), "No piece at space that should have a piece for red player's side");
                    }
                }
                else if(row > 4){ // the white player's side of the board with their pieces
                    if(CuT.getRow(row)[col].isDarkSquare()){
                        assertNotNull(CuT.getRow(row)[col].getPiece(), "No piece at space that should have a piece for white player's side");
                    }
                }
            }
        }
    }

    /**
     * Tests that the board's row that are in valid indexes are not null.
     * Also tests that Board.getRow(index) works.
     */
    @Test
    public void testValidRowIndexes(){
        for (int i = LOWER_BOUND; i < UPPER_BOUND; i++){
            assertNotNull(CuT.getRow(i), "Board.getRow at a valid index was null");
        }
    }

    /**
     * Tests that for invalid row indexes, the getRow(index) method throws
     * an exception.
     * Also tests that Board.getRow(index) works.
     */
    @Test
    public void testInvalidRowIndexes(){
        assertThrows(IllegalArgumentException.class, () -> {CuT.getRow(INVALID_ROW_INDEX_1);}, "Board.getRow allowed numbers < 0");
        assertThrows(IllegalArgumentException.class, () -> {CuT.getRow(INVALID_ROW_INDEX_2);}, "Board.getRow allowed numbers > 7");
    }
}