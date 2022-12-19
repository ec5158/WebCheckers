package com.webcheckers.ui;

import com.webcheckers.application.*;
import com.webcheckers.model.*;
import com.webcheckers.util.*;
import spark.*;

import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Game page.
 *
 */
public class GetGameRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

  //static  final String

  private static final String PARAM_CHALLENGEE_NAME = "name";

  private final TemplateEngine templateEngine;
  private GameCenter gameCenter;
  private PlayerLobby lobby;
  private Matchmaking matchmaking;
  public enum viewMode {PLAY}



  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /game} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetGameRoute(final GameCenter gameCenter, PlayerLobby lobby, Matchmaking matchmaking, TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gameCenter = gameCenter;
    this.lobby = lobby;
    this.matchmaking = matchmaking;
    //
    LOG.config("GetGameRoute is initialized.");
  }

  /**
   * Render the WebCheckers Game page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Game page
   */
  @Override
  public Object handle(Request request, Response response) {
    Map<String, Object> vm;
    final Session httpSession = request.session();

    // Get current user
    Player player = httpSession.attribute(PostSigninRoute.PLAYER_KEY);

    // This query string parameter exists if a player has attempted to challenge someone
    if (request.queryParams(PARAM_CHALLENGEE_NAME) != null) {
      // Value of the parameter is the challenged user's username
      String challengedPlayerName = request.queryParams(PARAM_CHALLENGEE_NAME);

      // Is the challenged player in game?
      boolean playerInGame = lobby.getPlayerInGame(challengedPlayerName);

      // Challenged is in game, reload home view with error
      if (playerInGame) {
        response.redirect(WebServer.HOME_URL + "?" + WebServer.ERROR_INGAME + "=true");
        return null;
      }

      vm = NavbarUtils.renderNavBar(request);

      LOG.finer("GetGameRoute is invoked.");
      // Get challenged Player object
      Player white = lobby.getPlayer(challengedPlayerName);

      matchmaking.registerMatch(player, white, vm);
      
    } else {
      // This GET is for a game already in progress.
      // Get existing view-model for this match
      vm = matchmaking.getViewModel(player);
    }

    // Amend view-model with current user and current board
    vm.put("title", "Web Checkers");
    vm.put("currentUser", player);
    vm.put("board", new BoardView(player, matchmaking.getBoard(player)));
    

    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }
}
