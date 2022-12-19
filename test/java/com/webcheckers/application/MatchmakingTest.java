package com.webcheckers.application;

import com.webcheckers.model.Board;
import com.webcheckers.model.Match;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@Tag("Application-tier")
public class MatchmakingTest {
    GameCenter center;
    Matchmaking matchmaking;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        matchmaking = spy(new Matchmaking());
    }

    @Test
    public void registerMatchTest(){
        Player one = mock(Player.class);
        Player two = mock(Player.class);
        matchmaking.registerMatch(one, two, new HashMap<String, Object>());
        verify(one).setInGame(true);
        verify(two).setInGame(true);
    }

    @Test
    public void closeMatch(){
        Player one = mock(Player.class);
        Player two = mock(Player.class);

        matchmaking.closeMatch(one, two);

        verify(one).setInGame(false);
        verify(two).setInGame(false);
    }

    @Test
    public void getMatchTest(){
        Player player = mock(Player.class);
        Match match = mock(Match.class);
        Map<Player, Match> matchMap = mock(HashMap.class);

        when(matchMap.containsKey(any(Player.class))).thenReturn(true);
        when(matchMap.get(any(Player.class))).thenReturn(match);

        when(matchmaking.getMatchMap()).thenReturn(matchMap);
        when(matchmaking.getMatch(any())).thenCallRealMethod();

        Match returnedMatch  = matchmaking.getMatch(player);

        assertEquals(match, returnedMatch);
    }

    @Test
    public void getBoardTest(){
        matchmaking = mock(Matchmaking.class);
        Player player = mock(Player.class);
        Match match = mock(Match.class);
        Board mockBoard = mock(Board.class);

        when(match.getBoard()).thenReturn(mockBoard);

        when(matchmaking.getMatch(any(Player.class))).thenReturn(match);
        when(matchmaking.getBoard(any(Player.class))).thenCallRealMethod();

        Board board = matchmaking.getBoard(player);

        assertEquals(board, mockBoard);
    }

    @Test
    public void getViewModelTest(){
        matchmaking = mock(Matchmaking.class);
        Player player = mock(Player.class);
        Match match = mock(Match.class);
        Map<String, Object> mockModel = mock(HashMap.class);

        when(matchmaking.getMatch(any(Player.class))).thenReturn(match);
        when(matchmaking.getViewModel(any(Player.class))).thenCallRealMethod();

        Map<String, Object> model = matchmaking.getViewModel(player);

        assertEquals(model, mockModel);
    }
}
