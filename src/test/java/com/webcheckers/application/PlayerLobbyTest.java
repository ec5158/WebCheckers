package com.webcheckers.application;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.webcheckers.model.Player;
import com.webcheckers.util.UsernameValidator.UsernameValidity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Tests PlayerLobby
 * @author Shane Burke
 */
@Tag("Application-tier")
public class PlayerLobbyTest {
    private Player p1, p2, p3;

    private final String PLAYERNAME_ONE = "John";
    private final String PLAYERNAME_TWO = "Jane";
    private final String PLAYERNAME_THREE = "Jack";

    private final String NO_PLAYERS_MESSAGE 
        = "<i>There are no other players available to play at this time.</i>";

    private final String ONE_PLAYER_MESSAGE
        = "There is 1 player online.";

    private final String TWO_PLAYERS_MESSAGE
        = "There are 2 players online.";

    PlayerLobby CuT;

    @BeforeEach
    public void setup() {
        CuT = new PlayerLobby();

        p1 = mock(Player.class);
        p2 = mock(Player.class);
        p3 = mock(Player.class);

        when(p1.getName()).thenReturn(PLAYERNAME_ONE);
        when(p2.getName()).thenReturn(PLAYERNAME_TWO);
        when(p3.getName()).thenReturn(PLAYERNAME_THREE);
    }

    /**
     * Test the signing in of one player.
     * Asserts method completion with valid username.
     */
    @Test
    public void signinOne() {
        CuT = new PlayerLobby();
        UsernameValidity nameValid = CuT.signin(p1);

        assertTrue(nameValid == UsernameValidity.VALID);
    }

    /**
     * Tests signing in of two players.
     * Asserts both users signed in with valid usernames.
     */
    @Test
    public void signinTwo() {
        CuT = new PlayerLobby();

        UsernameValidity nameValidOne = CuT.signin(p1);
        UsernameValidity nameValidTwo = CuT.signin(p2);

        assertTrue(nameValidOne == UsernameValidity.VALID);
        assertTrue(nameValidTwo == UsernameValidity.VALID);
    }

    /**
     * Tests the "number of players" message with no players signed in.
     * Asserts that the message is as expected.
     */
    @Test
    public void getNumPlayersMessageNoPlayers() {
        CuT = mock(PlayerLobby.class);

        when(CuT.getNumPlayersMessage()).thenCallRealMethod();
        when(CuT.getNumPlayers()).thenReturn(0);
        assertEquals(CuT.getNumPlayersMessage(), NO_PLAYERS_MESSAGE);
    }

    /**
     * Tests the "number of players" message with one player signed in.
     * Asserts that the message is as expected.
     */
    @Test
    public void getNumPlayersMessageOnePlayer() {
        CuT = mock(PlayerLobby.class);
        
        when(CuT.getNumPlayersMessage()).thenCallRealMethod();
        when(CuT.getNumPlayers()).thenReturn(1);
        assertEquals(CuT.getNumPlayersMessage(), ONE_PLAYER_MESSAGE);
    }

    /**
     * Tests the "number of players" message with two players signed in.
     * Asserts that the message is as expected.
     */
    @Test
    public void getNumPlayersMessageTwoPlayers() {
        CuT = mock(PlayerLobby.class);
        
        when(CuT.getNumPlayersMessage()).thenCallRealMethod();
        when(CuT.getNumPlayers()).thenReturn(2);
        assertEquals(CuT.getNumPlayersMessage(), TWO_PLAYERS_MESSAGE);
    }

    /**
     * Tests getOtherPlayers. 
     * Asserts that getOtherPlayers with {p1, p2, p3} for p1 returns {p2, p3}
     */
    @Test
    public void getOtherPlayers() {
        CuT = mock(PlayerLobby.class);

        Set<Player> playerSet = new HashSet<Player>();

        playerSet.add(p1);
        playerSet.add(p2);
        playerSet.add(p3);

        List<Player> otherPlayers = new ArrayList<Player>();
        otherPlayers.add(p2);
        otherPlayers.add(p2);

        when(CuT.getPlayerSet()).thenReturn(playerSet);
        when(CuT.getOtherPlayers(any(Player.class))).thenCallRealMethod();
        List<Player> returnedOtherPlayers = Arrays.asList(CuT.getOtherPlayers(p1));

        assert(returnedOtherPlayers.containsAll(otherPlayers) && returnedOtherPlayers.size() == 2);
    }

    /**
     * Tests the retrieval of a Player based on username
     * Asserts that the correct Player is returned by getPlayer()
     */
    @Test
    public void getPlayer() {
        CuT = mock(PlayerLobby.class);

        Set<Player> playerSet = new HashSet<Player>();

        playerSet.add(p1);
        playerSet.add(p2);
        playerSet.add(p3);

        when(CuT.getPlayerSet()).thenReturn(playerSet);
        when(CuT.getPlayer(any(String.class))).thenCallRealMethod();

        Player testPlayerOne = CuT.getPlayer(p1.getName());
        Player testPlayerTwo = CuT.getPlayer(p2.getName());
        Player testPlayerThree = CuT.getPlayer(p3.getName());

        assertTrue(testPlayerOne.equals(p1));
        assertTrue(testPlayerTwo.equals(p2));
        assertTrue(testPlayerThree.equals(p3));
    }

    /**
     * Tests getting the number of players.
     * Asserts that the number returned by getNumPlayers() is correct.
     */
    @Test
    public void getNumPlayers() {
        CuT = mock(PlayerLobby.class);

        Set<Player> playerSet = new HashSet<Player>();

        playerSet.add(p1);
        playerSet.add(p2);
        playerSet.add(p3);

        when(CuT.getPlayerSet()).thenReturn(playerSet);
        when(CuT.getNumPlayers()).thenCallRealMethod();

        int numPlayers = playerSet.size();
        int numPlayersTest = CuT.getNumPlayers();

        assertTrue(numPlayers == numPlayersTest);
    }
}