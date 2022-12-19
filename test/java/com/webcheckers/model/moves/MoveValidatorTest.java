package com.webcheckers.model.moves;

import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Model-Tier")
public class MoveValidatorTest {
    MoveGenerator generator;
    MoveValidator CuT;
    Board board;
    @BeforeEach
    public void setup(){
        board = new Board(new Player("white"), new Player("red"));
        generator = mock(MoveGenerator.class);
        CuT = new MoveValidator(generator);
    }

    @Test
    public void unreasonableMoveTest() {
        Position start = mock(Position.class);
        Position end = mock(Position.class);

        MoveValidator.ValidationStatus[] invalidStatuses = {MoveValidator.ValidationStatus.INVALID_END, MoveValidator.ValidationStatus.INVALID_START, MoveValidator.ValidationStatus.END_ALREADY_OCCUPIED};
        for (MoveValidator.ValidationStatus status : invalidStatuses) {
            when(generator.isMoveReasonable(any(Position.class), any(Position.class), any(Board.class))).thenReturn(MoveValidator.ValidationStatus.INVALID_END);
            assertEquals(CuT.validateMove(start, end, board), MoveValidator.ValidationStatus.INVALID_END);
        }
    }

    @Test
    public void reasonableButBadMoveTest() {
        Position start = mock(Position.class);
        Position end = mock(Position.class);

        when(generator.isMoveReasonable(any(Position.class), any(Position.class), any(Board.class))).thenReturn(MoveValidator.ValidationStatus.VALID);
        when(generator.generateMove(any(Position.class), any(Position.class), any(Board.class))).thenReturn(null);
        assertEquals(CuT.validateMove(start, end, board), MoveValidator.ValidationStatus.INVALID_END);
    }

    @Test
    public void legalMoveTest() {
        Position start = mock(Position.class);
        Position end = mock(Position.class);
        Move move = new SimpleMove(start, end);

        when(generator.isMoveReasonable(any(Position.class), any(Position.class), any(Board.class))).thenReturn(MoveValidator.ValidationStatus.VALID);
        when(generator.generateMove(any(Position.class), any(Position.class), any(Board.class))).thenReturn(move);
        assertEquals(CuT.validateMove(start, end, board), MoveValidator.ValidationStatus.VALID);
    }

}
