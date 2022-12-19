package com.webcheckers.model.moves;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class PositionTest {

    Position CuT;
    int row;
    int col;

    @BeforeEach
    public void setup(){
        row = 1;
        col = 1;
        CuT = new Position(row,col);
    }

    @Test
    public void getRowTest(){
        assertEquals(CuT.getRow(), row);
    }
    @Test
    public void getColTest(){
        assertEquals(CuT.getCell(), col);
    }

    @Test
    public void isValidTest(){
        assertTrue(CuT.isValid());
        assertFalse(new Position(-1, 1).isValid());
        assertFalse(new Position(-1, -1).isValid());
        assertFalse(new Position(1, -1).isValid());
        assertFalse(new Position(9, 1).isValid());
        assertFalse(new Position(1, 9).isValid());
    }

    @Test
    public void toJsonTest(){
        Gson gson = new Gson();
        Position pos = gson.fromJson(CuT.toJson(), Position.class);
        assertEquals(pos, CuT);
    }


}
