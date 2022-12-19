package com.webcheckers.model.moves;

import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@Tag("Model-Tier")
public class JumpMoveTest {
    JumpMove CuT;
    Position start, end, mid;
    Board board;

    @BeforeEach
    public void setup(){
        start = new Position(1,1);
        end = new Position(3,3);
        mid = new Position(2, 2);
        CuT = new JumpMove(start, end);
    }

    @Test
    public void getEndTest(){
        assertEquals(CuT.getEnd(), end);
    }

    @Test
    public void getStartTest(){
        assertEquals(CuT.getStart(), start);
    }

    @Test
    public void makeMoveTest(){
        Board board = mock(Board.class);
        Space startSpace = mock(Space.class);
        Space endSpace = mock(Space.class);
        Piece piece = mock(Piece.class);
        Space midSpace = mock(Space.class);

        when(startSpace.getPiece()).thenReturn(piece);
        when(board.getSpaceAtPosition(start)).thenReturn(startSpace);
        when(board.getSpaceAtPosition(end)).thenReturn(endSpace);
        when(board.getSpaceAtPosition(mid)).thenReturn(midSpace);
        CuT.apply(board);
        verify(board).getSpaceAtPosition(mid);
        verify(startSpace).setPiece(null);
        verify(endSpace).setPiece(piece);
    }

    @Test
    public void equalsTest(){
        assertTrue(new SimpleMove(start,end).equals(new SimpleMove(start,end)));
    }


}
