package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
class RowTest {
    /**
     * The component-under-test (CuT).
     */
    private Row CuT;
    private int index;
    private Space[] spaces;

    @BeforeEach
    public void setUp(){
        this.index = 1;
        spaces = new Space[8];
        CuT = new Row(index, spaces);
    }

    /**
     * Tests the getIndex method and that
     * it returns the right index.
     */
    @Test
    public void getIndexTest() {
        int rightIndex = 1;
        int wrongIndex1 = -1;
        int wrongIndex2 = 7;
        assertEquals(CuT.getIndex(), rightIndex);
        assertNotEquals(CuT.getIndex(), wrongIndex1);
        assertNotEquals(CuT.getIndex(), wrongIndex2);
    }

    /**
     * Tests the iterator method is not null.
     */
    @Test
    public void iteratorTest(){
        assertNotNull(CuT.iterator());
    }
}