package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.Matchmaking;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test GetGameRoute.java
 * @author Shane Burke
 */
@Tag("UI-tier")
public class GetGameRouteTest {

    /**
     * The component-under-test (CuT).
     */
    private GetGameRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private Matchmaking matchmaking;
    private TemplateEngineTester testHelper;
    private Player mockPlayer;

    private final String PLAYER_NAME = "John";
    private final String CHALLENGEE_NAME = "Jane";
    private final String PARAM_CHALLENGEE_NAME = "name";

    private final String VM_TITLE_ATTR = "title";
    private final String VM_CURRENT_USER = "currentUser";
    private final String VM_BOARD = "board";
    private final String VM_TITLE = "Web Checkers";

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        playerLobby = mock(PlayerLobby.class);
        when(request.session()).thenReturn(session);
        mockPlayer = mock(Player.class);
        when(mockPlayer.getName()).thenReturn(PLAYER_NAME);
        when(playerLobby.getPlayer(PLAYER_NAME)).thenReturn(mockPlayer);
        when(session.attribute(NavbarUtils.PLAYER_KEY)).thenReturn(mockPlayer);

        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        gameCenter = new GameCenter();
        matchmaking = mock(Matchmaking.class);
        // create a unique CuT for each test
        CuT = new GetGameRoute(gameCenter, playerLobby, matchmaking, engine);
    
        testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
    }

    /**
     * Test challenging a player who is already in game.
     */
    @Test
    public void challengePlayerInGame() {
        when(request.queryParams(PARAM_CHALLENGEE_NAME)).thenReturn(CHALLENGEE_NAME);

        when(playerLobby.getPlayerInGame(CHALLENGEE_NAME)).thenReturn(true);

        // Returns null if challenged player is in game
        assertNull(CuT.handle(request, response));

        verify(response).redirect(WebServer.HOME_URL + "?" + WebServer.ERROR_INGAME + "=true");
    }

    /**
     * Test challenging a player who is not in game.
     */
    @Test
    public void challengePlayerNotInGame() {
        when(request.queryParams(PARAM_CHALLENGEE_NAME)).thenReturn(CHALLENGEE_NAME);
        when(playerLobby.getPlayerInGame(CHALLENGEE_NAME)).thenReturn(false);
        when(matchmaking.getBoard(any(Player.class))).thenReturn(mock(Board.class));

        CuT.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(VM_TITLE_ATTR, VM_TITLE);
        testHelper.assertViewModelAttribute(VM_CURRENT_USER, mockPlayer);
    }

    /**
     * Test retrieving the game view when challenged to a game.
     */
    @Test
    public void getGameView() {
        when(request.queryParams(PARAM_CHALLENGEE_NAME)).thenReturn(null);
        when(matchmaking.getBoard(any(Player.class))).thenReturn(mock(Board.class));

        CuT.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(VM_TITLE_ATTR, VM_TITLE);
        testHelper.assertViewModelAttribute(VM_CURRENT_USER, mockPlayer);
    }
}
