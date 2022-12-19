package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.PostSigninRoute.PLAYER_KEY;


/**
 * The UI Controller to control the sign out process.
 *
 */
public class PostSignOutRoute implements Route {
  private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());

  //static final String PLAYER_ID = "id";

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;
  private final GameCenter gameCenter;
  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /signout} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public PostSignOutRoute(final GameCenter gameCenter, TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = Objects.requireNonNull(gameCenter.getPlayerLobby(), "playerLobby is required");
    this.gameCenter = gameCenter;
    //
    LOG.config("PostSignOutRoute is initialized.");
  }

  /**
   * Process the player sign-out
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("PostSignOutRoute is invoked.");

    Map<String, Object> vm = NavbarUtils.renderNavBar(request);
    final Session httpSession = request.session();

    playerLobby.removePlayer(httpSession.attribute(PLAYER_KEY));

    httpSession.attribute(PLAYER_KEY, null);

    response.redirect(WebServer.HOME_URL);
    //return null;
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
