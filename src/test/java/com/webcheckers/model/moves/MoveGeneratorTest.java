package com.webcheckers.model.moves;

import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.model.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@Tag("Model-Tier")
public class MoveGeneratorTest {
    MoveGenerator CuT;

    Board board;
    Space startSpace;
    Position startPos;
    Space endSpace;
    Position endPos;

    @BeforeEach
    public void setup(){
        board = mock(Board.class);

        startPos = mock(Position.class);
        startSpace = mock(Space.class);

        endPos = mock(Position.class);
        endSpace = mock(Space.class);

        when(board.getSpaceAtPosition(startPos)).thenReturn(startSpace);
        when(board.getSpaceAtPosition(endPos)).thenReturn(endSpace);

        CuT = new MoveGenerator();

    }

    @Test
    public void illegalStartTest() {
        when(startSpace.isLegalSquare()).thenReturn(false);
        assertEquals(CuT.isMoveReasonable(startPos, endPos, board), MoveValidator.ValidationStatus.INVALID_START);



    }

    @Test
    public void occupiedStartTest() {
        when(startSpace.isLegalSquare()).thenReturn(true);
        when(startSpace.isOccupied()).thenReturn(false);

        assertEquals(CuT.isMoveReasonable(startPos, endPos, board), MoveValidator.ValidationStatus.INVALID_START);
    }

    @Test
    public void illegalEndTest() {
        when(startSpace.isLegalSquare()).thenReturn(true);
        when(startSpace.isOccupied()).thenReturn(true);

        when(endSpace.isLegalSquare()).thenReturn(false);
        assertEquals(CuT.isMoveReasonable(startPos, endPos, board), MoveValidator.ValidationStatus.INVALID_END);
    }

    @Test
    public void occupiedEndTest() {
        when(startSpace.isLegalSquare()).thenReturn(true);
        when(startSpace.isOccupied()).thenReturn(true);

        when(endSpace.isLegalSquare()).thenReturn(true);
        when(endSpace.isOccupied()).thenReturn(true);

        assertEquals(CuT.isMoveReasonable(startPos, endPos, board), MoveValidator.ValidationStatus.END_ALREADY_OCCUPIED);
    }

    @Test
    public void reasonableMoveTest() {
        when(startSpace.isLegalSquare()).thenReturn(true);
        when(startSpace.isOccupied()).thenReturn(true);

        when(endSpace.isLegalSquare()).thenReturn(true);
        when(endSpace.isOccupied()).thenReturn(false);

        assertEquals(CuT.isMoveReasonable(startPos, endPos, board), MoveValidator.ValidationStatus.VALID);
    }

    @Test
    public void generateSimpleMoveTest(){
        Board board = new Board(new Player("white"), new Player("red"));
        assertNotNull(CuT.generateSimpleMoves(new Position(2, 1), board));
    }

    @Test
    public void generateBadMoveTest(){
        when(startSpace.isLegalSquare()).thenReturn(false);

        assertNull(CuT.generateMove(startPos, endPos, board));
    }

    @Test
    public void generateBadButReasonableTest(){
        CuT = spy(CuT);
        doReturn(MoveValidator.ValidationStatus.VALID).when(CuT).isMoveReasonable(startPos, endPos, board);
        doReturn(new ArrayList<Move>()).when(CuT).generateSimpleMoves(startPos, board);
        doReturn(new ArrayList<Move>()).when(CuT).generateJumpMoves(startPos, board);
        assertNull(CuT.generateMove(startPos, endPos, board));
    }

    @Test
    public void generateGoodMove(){
        CuT = spy(CuT);
        Move move = new SimpleMove(startPos, endPos);
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.add(move);
        doReturn(MoveValidator.ValidationStatus.VALID).when(CuT).isMoveReasonable(startPos, endPos, board);
        doReturn(moves).when(CuT).generateSimpleMoves(startPos, board);
        doReturn(new ArrayList<Move>()).when(CuT).generateJumpMoves(startPos, board);
        assertEquals(move, CuT.generateMove(startPos, endPos, board));
    }

    @Test
    public void generateImpossibleMoveWithLegalStart(){
        CuT = spy(CuT);
        Move move = new SimpleMove(startPos, endPos);
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.add(move);
        doReturn(MoveValidator.ValidationStatus.VALID).when(CuT).isMoveReasonable(startPos, endPos, board);
        doReturn(moves).when(CuT).generateSimpleMoves(startPos, board);
        doReturn(new ArrayList<Move>()).when(CuT).generateJumpMoves(startPos, board);
        assertNull(CuT.generateMove(startPos, new Position(100, 100), board));
    }

    @Test
    public void generateValidJumpMovesTest(){
        CuT = spy(CuT);

        doReturn(true).when(CuT).isValidJumpMove(any(Position.class), any(Board.class), anyInt(), anyInt());
        List<Move> jumpMoves = CuT.generateJumpMoves(new Position(1,1 ), board);
        assertEquals(jumpMoves.size(), 4);
        assertTrue(jumpMoves.contains(new JumpMove(new Position(1,1 ), new Position(3, 3))));
    }

    @Test
    public void testIsValidJumpMove(){
        Space jumpedSpace = mock(Space.class);
        Piece piece = mock(Piece.class);
        Piece movingPiece = mock(Piece.class);

        when(jumpedSpace.isOccupied()).thenReturn(true);
        when(jumpedSpace.getPiece()).thenReturn(piece);

        when(piece.getColor()).thenReturn(Piece.color.RED);
        when(startPos.getRow()).thenReturn(3);
        when(startPos.getCell()).thenReturn(3);

        when(startSpace.getPiece()).thenReturn(movingPiece);
        when(movingPiece.getColor()).thenReturn(Piece.color.WHITE);//opposite of jumpedpiece
        when(movingPiece.getType()).thenReturn(Piece.type.KING);
        when(board.getSpaceAtPosition(new Position(5,5))).thenReturn(endSpace);
        when(endSpace.isOccupied()).thenReturn(false);
        when(board.getSpaceAtPosition(new Position(4,4))).thenReturn(jumpedSpace);

        assertTrue(CuT.isValidJumpMove(startPos, board, 1, 1));
    }

}
