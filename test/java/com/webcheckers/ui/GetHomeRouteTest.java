package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.Matchmaking;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Match;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static com.webcheckers.ui.PostSigninRoute.PLAYER_KEY;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class GetHomeRouteTest {

    private GetHomeRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private Player player;
    private PlayerLobby lobby;
    private Matchmaking matchmaking;
    private TemplateEngine engine;
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        gameCenter = mock(GameCenter.class);
        player = mock(Player.class);
        lobby = mock(PlayerLobby.class);
        matchmaking = mock(Matchmaking.class);
        engine = mock(TemplateEngine.class);
        when(gameCenter.getMatchmaking()).thenReturn(matchmaking);

        // create a unique CuT for each test
        CuT = new GetHomeRoute(gameCenter, lobby, engine);
    }
    @Test
    public void matchmakingTest(){
        Match match = mock(Match.class);
        when(session.attribute(PLAYER_KEY)).thenReturn(player);
        when(request.session()).thenReturn(session);
        when(matchmaking.getMatch(any(Player.class))).thenReturn(match);

        CuT.handle(request, response);
        verify(response).redirect(WebServer.GAME_URL);
    }

    @Test
    public void homeTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Match match = mock(Match.class);
        when(session.attribute(PLAYER_KEY)).thenReturn(null);
        when(request.session()).thenReturn(session);
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
    }

    @Test
    public void ingameErrorTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Match match = mock(Match.class);
        when(session.attribute(PLAYER_KEY)).thenReturn(null);
        when(request.session()).thenReturn(session);
        when(request.queryParams(WebServer.ERROR_INGAME)).thenReturn("true");
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("message", Message.error("That player is already in a game."));
    }

    @Test
    public void otherPlayersTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Match match = mock(Match.class);
        when(session.attribute(PLAYER_KEY)).thenReturn(null);
        when(request.session()).thenReturn(session);
        when(session.attribute(PLAYER_KEY)).thenReturn(player);
        when(matchmaking.getMatch(any(Player.class))).thenReturn(null);
        Player[] test = {new Player("test")};
        when(lobby.getOtherPlayers(any(Player.class))).thenReturn(test);
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("players",test);
    }
}
