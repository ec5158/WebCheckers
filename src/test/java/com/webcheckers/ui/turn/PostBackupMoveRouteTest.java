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
import org.mockito.Spy;

import spark.Request;
import spark.Response;
import spark.Session;

/**
 * Test PostSubmitTurnRoute.java
 */
@Tag("UI-tier")
public class PostBackupMoveRouteTest {
    private Matchmaking matchmaking;
    private Player player;
    private PostSubmitTurnRoute CuT;
    private Match match;
    private Board board;
    private Move move;
    private Request request;
    private Response response;

    Gson gson;
    
    @BeforeEach
    public void setup() {
        gson = new Gson();
        matchmaking = mock(Matchmaking.class);
        player = mock(Player.class);
        match = mock(Match.class);
        board = mock(Board.class);
        request = mock(Request.class);
        response = mock(Response.class);

        CuT = spy(new PostSubmitTurnRoute(matchmaking));

        Mockito.doReturn(player).when(CuT).getPlayer(request);
        when(matchmaking.getMatch(any(Player.class))).thenReturn(match);
        when(match.getBoard()).thenReturn(board);
    }

    /**
     * Test that no current move results in correct error message.
     */
    @Test
    public void nullCurrentMoveTest() {
        when(match.getCurrentMove()).thenReturn(null);

        Message.Type correctType = Message.Type.ERROR;
        String correctText = PostSubmitTurnRoute.ERR_NO_MOVE;

        String result = (String)CuT.handle(request, response);

        assertNotNull(result);
        
        Message message = (Message) gson.fromJson(result, new TypeToken<Message>() {}.getType());

        Message.Type messageType = message.getType();
        String messageText = message.getText();

        assertEquals(messageType, correctType);
        assertEquals(messageText, correctText);

    }

    @Test
    public void notNullCurrentMoveTest() {
        move = mock(Move.class);
        when(match.getCurrentMove()).thenReturn(move);

        Message.Type correctType = Message.Type.INFO;
        String correctText = PostSubmitTurnRoute.INFO_TURN_COMPLETE;

        String result = (String)CuT.handle(request, response);

        assertNotNull(result);
        
        Message message = (Message) gson.fromJson(result, new TypeToken<Message>() {}.getType());

        Message.Type messageType = message.getType();
        String messageText = message.getText();

        assertEquals(messageType, correctType);
        assertEquals(messageText, correctText);
    }
    
}