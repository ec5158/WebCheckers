package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;

import static com.webcheckers.ui.PostSigninRoute.PLAYER_KEY;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
  private static final Message ERROR_INGAME_MSG = Message.error("That player is already in a game.");

  private final TemplateEngine templateEngine;
  private final GameCenter gameCenter;
  private PlayerLobby lobby;
  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final GameCenter gameCenter, PlayerLobby lobby, TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gameCenter = gameCenter;
    this.lobby = lobby;
    //
    LOG.config("GetHomeRoute is initialized.");
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
    LOG.finer("GetHomeRoute is invoked.");
    Player player = request.session().attribute(PLAYER_KEY);
    Map<String, Object> vm = NavbarUtils.renderNavBar(request);
    String template = "home.ftl";

    if (gameCenter.getMatchmaking().getMatch(player) != null) {
      response.redirect(WebServer.GAME_URL);
    } else {
      // display a user message in the Home page
      if (request.queryParams(WebServer.ERROR_INGAME) != null) {
        vm.put("message", ERROR_INGAME_MSG);
      } else {
        vm.put("message", WELCOME_MSG);
      }
      vm.put("numPlayersMessage", lobby.getNumPlayersMessage());
      if(player != null){
        //we are logged in so show the other players
        Player[] players = lobby.getOtherPlayers(player);
        vm.put("players", players);
      }
    }
    return templateEngine.render(new ModelAndView(vm , template));
  }
}
