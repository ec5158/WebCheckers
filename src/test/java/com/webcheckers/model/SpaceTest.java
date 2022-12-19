package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@Tag("Model-Tier")
class SpaceTest {
    /**
     * The component-under-test (CuT).
     */
    private Space CuT;
    private int index;
    private boolean isDarkSpace;
    private Piece aPiece;

    /**
     * Sets up the CuT for testing
     */
    @BeforeEach
    public void setUp(){
        index = 1;
        isDarkSpace = true;
        CuT = new Space(index, isDarkSpace);
        aPiece = mock(Piece.class);
    }

    /**
     * Tests the getCellIdx method
     */
    @Test
    public void getCellIdxTest(){
        assertEquals(1, CuT.getCellIdx());
    }

    /**
     * Tests if it is valid to put a piece
     * on this space using isValid method
     */
    @Test
    public void isValidTest() {
        assertTrue(CuT.isValid());

        CuT = new Space(index, !isDarkSpace);
        assertFalse(CuT.isValid());

        CuT = new Space(index, isDarkSpace, aPiece);
        assertFalse(CuT.isValid());

        CuT = new Space(index, !isDarkSpace, aPiece);
        assertFalse(CuT.isValid());
    }

    /**
     * Tests the getPiece method
     */
    @Test
    public void getPieceTest(){
        assertNull(CuT.getPiece());

        CuT = new Space(index, isDarkSpace, aPiece);
        assertEquals(aPiece, CuT.getPiece());
    }

    /**
     * Tests the setPiece method
     */
    @Test
    public void setPieceTest(){
        CuT.setPiece(aPiece);
        assertNotNull(CuT.getPiece());
    }

    /**
     * Tests the isOccupied method
     */
    @Test
    public void isOccupiedTest(){
        assertFalse(CuT.isOccupied());

        CuT = new Space(index, isDarkSpace, aPiece);
        assertTrue(CuT.isOccupied());
    }

    /**
     * Tests the isLegalSquare method and if
     * adding a piece changes the result
     */
    @Test
    public void isLegalSquareTest(){
        assertTrue(CuT.isLegalSquare());

        CuT = new Space(index, !isDarkSpace);
        assertFalse(CuT.isLegalSquare());

        CuT = new Space(index, isDarkSpace, aPiece);
        assertTrue(CuT.isLegalSquare());

        CuT = new Space(index, !isDarkSpace, aPiece);
        assertFalse(CuT.isLegalSquare());
    }

    /**
     * Tests the isDarkSquare method and if
     * adding a piece changes the result
     */
    @Test
    public void isDarkSquareTest(){
        assertTrue(CuT.isDarkSquare());

        CuT = new Space(index, !isDarkSpace);
        assertFalse(CuT.isDarkSquare());

        CuT = new Space(index, isDarkSpace, aPiece);
        assertTrue(CuT.isDarkSquare());

        CuT = new Space(index, !isDarkSpace, aPiece);
        assertFalse(CuT.isDarkSquare());
    }
}