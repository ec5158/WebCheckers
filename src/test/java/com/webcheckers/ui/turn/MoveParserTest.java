package com.webcheckers.ui.turn;

import com.webcheckers.model.Board;
import com.webcheckers.model.moves.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class MoveParserTest {
    MoveParser CuT;
    MoveGenerator generator;
    MoveValidator validator;
    Board board;
    @BeforeEach
    public void setup(){
        generator = mock(MoveGenerator.class);
        validator = mock(MoveValidator.class);
        board = mock(Board.class);
        CuT = new MoveParser(generator, validator);
    }

    @Test
    public void testParseMove(){
        Position startPos = new Position(1, 1);
        Position endPos = new Position(2,2);
        Move testMove = new SimpleMove(startPos, endPos);
        when(generator.generateMove(startPos, endPos, board)).thenReturn(testMove);
        assertEquals(CuT.parseMove(testMove.toJson(), board), testMove);
    }

    @Test
    public void testValidateMove(){
        Position startPos = new Position(1, 1);
        Position endPos = new Position(2,2);
        Move testMove = new SimpleMove(startPos, endPos);
        when(validator.validateMove(startPos, endPos, board)).thenReturn(MoveValidator.ValidationStatus.VALID);
        assertEquals(CuT.validateMove(testMove.toJson(), board), MoveValidator.ValidationStatus.VALID);
    }
}
