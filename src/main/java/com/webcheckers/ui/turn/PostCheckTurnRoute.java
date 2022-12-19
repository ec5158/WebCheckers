package com.webcheckers.ui.turn;

import com.google.gson.Gson;
import com.webcheckers.application.Matchmaking;
import com.webcheckers.model.Player;
import com.webcheckers.ui.NavbarUtils;
import com.webcheckers.util.Message;

import spark.Route;
import spark.Request;
import spark.Response;

import static com.webcheckers.ui.PostSigninRoute.PLAYER_KEY;

/**
 * Returns whether this player is currently playing their turn.
 */
public class PostCheckTurnRoute implements Route {
    private Gson gson;
    private Matchmaking matchmaking;
    
    public PostCheckTurnRoute(Matchmaking matchmaking) {
        this.matchmaking = matchmaking;
        this.gson = new Gson();
    }
    
    @Override
    public Object handle(Request request, Response response) {
        Player player = getPlayer(request);
        Player activePlayer = matchmaking.getMatchActivePlayer(player);

        Message message;

        // Are the current player and this user the same?
        boolean samePlayer = samePlayer(player, activePlayer);
        message = Message.info(Boolean.toString(samePlayer));

        return gson.toJson(message);
    }

    protected Player getPlayer(Request request) {
        return NavbarUtils.getPlayer(request);
    }

    protected boolean samePlayer(Player p1, Player p2) {
        return p1.equals(p2);
    }
}
