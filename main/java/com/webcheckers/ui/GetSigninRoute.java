package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.util.Message;
import com.webcheckers.application.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the sign in page.
 *
 */
public class GetSigninRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

  private final TemplateEngine templateEngine;
  private GameCenter gameCenter;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /signin} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetSigninRoute(final GameCenter gameCenter, TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gameCenter = gameCenter;

    LOG.config("GetSigninRoute is initialized.");
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
    LOG.finer("GetSigninRoute is invoked.");
    //
    Map<String, Object> vm = NavbarUtils.renderNavBar(request);

    // render the View
    return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
  }
}
