package com.webcheckers.ui.turn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webcheckers.application.Matchmaking;
import com.webcheckers.model.Board;
import com.webcheckers.model.Match;
import com.webcheckers.model.Player;
import com.webcheckers.model.moves.Move;
import com.webcheckers.util.Message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import spark.Request;
import spark.Response;
import spark.Session;

/**
 * Test PostSubmitTurnRoute.java
 */
@Tag("UI-tier")
public class PostCheckTurnRouteTest {
    private Matchmaking matchmaking;
    private Player userPlayer, otherPlayer;
    private PostCheckTurnRoute CuT;
    private Match match;
    private Move move;
    private Request request;
    private Response response;

    Gson gson;
    Message.Type correctType = Message.Type.INFO;
    
    @BeforeEach
    public void setup() {
        gson = new Gson();
        matchmaking = mock(Matchmaking.class);
        userPlayer = mock(Player.class);
        otherPlayer = mock(Player.class);
        request = mock(Request.class);
        response = mock(Response.class);

        CuT = spy(new PostCheckTurnRoute(matchmaking));

        Mockito.doReturn(userPlayer).when(CuT).getPlayer(request);
    }

    /**
     * Test that correct message is returned when it's not the user's turn
     */
    @Test
    public void notMyTurnTest() {
        when(matchmaking.getMatchActivePlayer(any(Player.class))).thenReturn(otherPlayer);
        Mockito.doReturn(false).when(CuT).samePlayer(any(Player.class), any(Player.class));

        String correctText = Boolean.toString(false);

        String result = (String)CuT.handle(request, response);

        assertNotNull(result);
        
        Message message = (Message) gson.fromJson(result, new TypeToken<Message>() {}.getType());

        Message.Type messageType = message.getType();
        String messageText = message.getText();

        assertEquals(messageType, correctType);
        assertEquals(messageText, correctText);
    }

    /**
     * Test that correct message is returned when it's the user's turn
     */
    @Test
    public void isMyTurnTest() {
        when(matchmaking.getMatchActivePlayer(any(Player.class))).thenReturn(userPlayer);
        Mockito.doReturn(true).when(CuT).samePlayer(any(Player.class), any(Player.class));

        String correctText = Boolean.toString(true);

        String result = (String)CuT.handle(request, response);

        assertNotNull(result);
        
        Message message = (Message) gson.fromJson(result, new TypeToken<Message>() {}.getType());

        Message.Type messageType = message.getType();
        String messageText = message.getText();

        assertEquals(messageType, correctType);
        assertEquals(messageText, correctText);
    }
    
}