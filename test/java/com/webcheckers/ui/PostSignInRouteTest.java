package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.UsernameValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.*;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class PostSignInRouteTest {

    private static final String USERNAME_NOT_ALPHANUM_MSG = "Usernames must contain at least "
            + "one alphanumeric character and optionally spaces. Please try another name.";
    private static final String USERNAME_TAKEN_MSG = "The username you entered is taken. Please try another name.";

    /**
     * The component-under-test (CuT).
     */
    private PostSigninRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private GameCenter gameCenter;
    private PlayerLobby lobby;
    private Player player;

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
        gameCenter = spy(new GameCenter());
        player = mock(Player.class);
        lobby = spy(gameCenter.getPlayerLobby());
        // create a unique CuT for each test
        CuT = new PostSigninRoute(gameCenter, lobby, engine);
    }

    @Test
    public void alreadySignedIn() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(session.attribute(NavbarUtils.PLAYER_KEY)).thenReturn(player);

        // Invoke the test
        try {
            CuT.handle(request, response);
            //fail("Redirects invoke halt exceptions.");
        } catch (HaltException e) {
            // expected
        }

        // Analyze the results:
        //   * redirect to the Game view
        verify(response).redirect(WebServer.HOME_URL);
    }

    @Test
    public void successfulSignIn(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(NavbarUtils.PLAYER_KEY)).thenReturn(null);
        when(request.queryParams("username")).thenReturn("testUsername");
        CuT.handle(request, response);
        verify(response).redirect(WebServer.HOME_URL);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("currentUser", new Player("testUsername"));

    }

    @Test
    public void usernameNotAlphaNum(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(NavbarUtils.PLAYER_KEY)).thenReturn(null);
        when(request.queryParams("username")).thenReturn(" ");
        CuT.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("message", Message.error(USERNAME_NOT_ALPHANUM_MSG));
    }

    @Test
    public void usernameAlreadyInUse(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(NavbarUtils.PLAYER_KEY)).thenReturn(null);
        when(request.queryParams("username")).thenReturn("in-use");
        doReturn(UsernameValidator.UsernameValidity.NAME_TAKEN).when(lobby).signin(any(Player.class));
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("message", Message.error(USERNAME_TAKEN_MSG));
    }
}
