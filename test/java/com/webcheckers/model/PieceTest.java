package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.webcheckers.model.moves.*;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
class PieceTest {
    /**
     * The component-under-test (CuT).
     */
    private Piece CuT;
    private Position start, end;
    private SimpleMove testMove;

    /**
     * Tests if the constructor creates pieces
     * with the right color and type.
     */
    @Test
    public void createPiecesTest() {
        CuT = new Piece(Piece.color.RED, Piece.type.SINGLE);
        assertEquals(CuT.getColor(), Piece.color.RED);
        assertEquals(CuT.getType(), Piece.type.SINGLE);

        CuT = new Piece(Piece.color.WHITE, Piece.type.SINGLE);
        assertEquals(CuT.getColor(), Piece.color.WHITE);
        assertEquals(CuT.getType(), Piece.type.SINGLE);
    }

    /**
     * Tests if the setKing changes the piece's
     * type to king and does so without changing
     * color.
     */
    @Test
    public void setKingTest() {
        CuT = new Piece(Piece.color.RED, Piece.type.SINGLE);
        CuT.setKing();
        assertEquals(CuT.getColor(), Piece.color.RED);
        assertEquals(CuT.getType(), Piece.type.KING);

        CuT = new Piece(Piece.color.WHITE, Piece.type.SINGLE);
        CuT.setKing();
        assertEquals(CuT.getColor(), Piece.color.WHITE);
        assertEquals(CuT.getType(), Piece.type.KING);
    }

    /**
     * Tests that a piece set to the position/row that would
     * trigger becoming a king does so.
     */
    @Test
    void kingMeTest() {
        CuT = new Piece(Piece.color.RED, Piece.type.SINGLE);
        start = new Position(6, 5);
        end = new Position(7, 6);
        testMove = new SimpleMove(start, end);
        assertTrue(CuT.checkKingStatus(CuT, testMove));

        CuT = new Piece(Piece.color.WHITE, Piece.type.SINGLE);
        start = new Position(1, 5);
        end = new Position(0, 6);
        testMove = new SimpleMove(start, end);
        assertTrue(CuT.checkKingStatus(CuT, testMove));
    }

    /**
     * Tests the equal method of the piece.
     */
    @Test
    void equalsTest() {
        CuT = new Piece(Piece.color.RED, Piece.type.SINGLE);
        assertFalse(CuT.equals(new Piece(Piece.color.WHITE, Piece.type.SINGLE)));
        assertFalse(CuT.equals(new Piece(Piece.color.RED, Piece.type.KING)));
        assertFalse(CuT.equals(new Piece(Piece.color.WHITE, Piece.type.KING)));
        assertTrue(CuT.equals(new Piece(Piece.color.RED, Piece.type.SINGLE)));
    }
}