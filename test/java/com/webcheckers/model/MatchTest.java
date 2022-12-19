package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import com.webcheckers.model.moves.Move;
import com.webcheckers.ui.TemplateEngineTester;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-Tier")
class MatchTest {

    /**
     * The component-under-test (CuT).
     */
    private Match match;

    private Player p1, p2;
    private TemplateEngineTester testHelper;
    private Map<String, Object> vm;
    private Match CuT;

    @BeforeEach
    public void setup(){
        p1 = mock(Player.class);
        p2 = mock(Player.class);
        testHelper = new TemplateEngineTester();
        vm = new HashMap<String, Object>();
        CuT = new Match(p1, p2, vm);
    }

    @Test
    public void testNewTurn() {
        CuT.newTurn();
        assertEquals(Match.ActiveColor.WHITE, vm.get(Match.ATTR_ACTIVE_COLOR));
    }

    @Test
    public void setCurrentMoveTest() {
        Move move = mock(Move.class);
        CuT.setCurrentMove(move);
        assertEquals(move, CuT.getCurrentMove());
    }

    @Test
    public void getViewModelTest() {
        Map<String, Object> vmTest = CuT.getViewModel();
        assertEquals(vm, vmTest);
    }

    @Test
    public void getBoardTest() {
        assertNotNull(CuT.getBoard());
    }
}