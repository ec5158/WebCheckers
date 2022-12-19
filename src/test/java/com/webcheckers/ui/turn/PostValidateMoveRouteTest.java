package com.webcheckers.ui.turn;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.Matchmaking;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Match;
import com.webcheckers.model.Player;
import com.webcheckers.model.moves.Move;
import com.webcheckers.model.moves.MoveValidator;
import com.webcheckers.model.moves.Position;
import com.webcheckers.model.moves.SimpleMove;
import com.webcheckers.ui.NavbarUtils;
import com.webcheckers.ui.TemplateEngineTester;
import com.webcheckers.ui.turn.PostValidateMoveRoute;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class PostValidateMoveRouteTest {
    Request request;
    Session session;
    PostValidateMoveRoute CuT;
    Matchmaking matchmaking;
    Response response;
    Player player;
    MoveParser parser;
    Board board;
    Gson gson;
    Match match;

    static final String PLAYER_KEY = "Player";

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        matchmaking = mock(Matchmaking.class);
        player = mock(Player.class);
        parser = mock(MoveParser.class);
        board = mock(Board.class);
        match = mock(Match.class);

        gson = new Gson();
        CuT = spy(new PostValidateMoveRoute(matchmaking));
    }

    @Test
    public void validMoveTest() {
        test(MoveValidator.ValidationStatus.VALID);
    }
    @Test
    public void alreadyOccupiedEndTest() {
        test(MoveValidator.ValidationStatus.END_ALREADY_OCCUPIED);
    }    @Test
    public void invalidStartTest() {
        test(MoveValidator.ValidationStatus.INVALID_START);
    }
    @Test
    public void invalidEndTest() {
        test(MoveValidator.ValidationStatus.INVALID_END);
    }
    
    public void test(MoveValidator.ValidationStatus status)  {
        when(session.attribute(PLAYER_KEY)).thenReturn(player);
        when(request.session()).thenReturn(session);
//        MoveParser parser = spy(CuT).getMoveParser();
        doReturn(parser).when(CuT).getMoveParser();
        when(CuT.getMoveParser()).thenReturn(parser);

        String actionData = "actionDataExample";
        when(request.queryParams("actionData")).thenReturn(actionData);

        when(matchmaking.getBoard(any(Player.class))).thenReturn(board);
        doReturn(null).when(parser).parseMove(any(String.class),any(Board.class));
        when(parser.validateMove(actionData, board)).thenReturn(status);
        when(parser.parseMove(any(String.class), any(Board.class))).thenReturn(null);
        when(matchmaking.getMatch(player)).thenReturn(match);
        doNothing().when(match).setCurrentMove(any(Move.class));
        Object reply = CuT.handle(request, response);
        String jsonText = (String) reply;
        Message message = gson.fromJson(jsonText, Message.class);
        Message correctMessage = CuT.getMessage(status);

        assertEquals(message, correctMessage);
        verify(parser).validateMove(actionData,board);
    }
}
