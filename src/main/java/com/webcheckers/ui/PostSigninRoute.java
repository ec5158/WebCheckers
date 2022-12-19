package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.UsernameValidator;
import com.webcheckers.util.UsernameValidator.UsernameValidity;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the sign in page.
 *
 */
public class PostSigninRoute implements Route {
  private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());
  public static final String PLAYER_KEY = "Player";

  private final TemplateEngine templateEngine;
  private final GameCenter gameCenter;
  private PlayerLobby lobby;

  private static final String USERNAME_TAKEN_MSG = "The username you entered is taken. Please try another name.";
  private static final String USERNAME_NOT_ALPHANUM_MSG = "Usernames must contain at least "
    + "one alphanumeric character and optionally spaces. Please try another name.";

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /signin} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public PostSigninRoute(final GameCenter gameCenter, PlayerLobby lobby, TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gameCenter = gameCenter;
    this.lobby = lobby;

    LOG.config("PostSigninRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
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
    LOG.finer("PostSigninRoute is invoked.");
    //
    Map<String, Object> vm = NavbarUtils.renderNavBar(request);

    final Session httpSession = request.session();
    if(httpSession.attribute(PLAYER_KEY) == null) {
      // get the object that will provide client-specific services for this player
      String username = request.queryParams("username");
      Player player = new Player(username);

      UsernameValidity usernameValidity = lobby.signin(player);

      if (usernameValidity == UsernameValidity.VALID) {
        Player[] players = lobby.getOtherPlayers(player);
        vm.put("players", players);

        vm.put("currentUser", player);
        httpSession.attribute(PLAYER_KEY, player);
        response.redirect(WebServer.HOME_URL);
        return templateEngine.render(new ModelAndView(vm, "home.ftl"));
      } else if (usernameValidity == UsernameValidity.NAME_TAKEN) {
        vm.put("message", Message.error(USERNAME_TAKEN_MSG));
        return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
      } else if (usernameValidity == UsernameValidity.NOT_ALPHANUMERIC) {
        vm.put("message", Message.error(USERNAME_NOT_ALPHANUM_MSG));
        return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
      }
    }
    else {
      //already signed in
      response.redirect(WebServer.HOME_URL);

    }
    return null;
  }
}