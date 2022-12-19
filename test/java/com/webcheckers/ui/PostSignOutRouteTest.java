package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
class PostSignOutRouteTest {

    /**
     * The component-under-test (CuT).
     */
    private PostSignOutRoute CuT;

    private final String HOME_VIEW_NAME = "home.ftl";

    private Request request;
    private Session session;
    private PlayerLobby playerLobby;
    private Response response;
    private TemplateEngine engine;
    private GameCenter gameCenter;
    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        Player mockPlayer = new Player("new_name");
        when(playerLobby.getPlayer("new_name")).thenReturn(mockPlayer);
        gameCenter = new GameCenter();
        // create a unique CuT for each test
        CuT = new PostSignOutRoute(gameCenter, engine);
    }

    /**
     * Tests that the player's username is removed from the page after
     * signing out.
     */
    @Test
    public void testPlayerUsernameRemovedFromPage(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttributeIsAbsent("currentUser");
    }

    /**
     * Tests that after signing out the web page redirects to the home page
     * and view model.
     */
    @Test
    public void testRedirectToHome(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(HOME_VIEW_NAME);
        verify(response).redirect(WebServer.HOME_URL);
    }

    /**
     * Tests that the player was removed from the player lobby after signing out
     */
    @Test
    public void testPlayerRemovedFromPlayerLobby() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        assertFalse(playerLobby.getPlayerInGame("username"));
    }
}