package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.webcheckers.model.Player;
import com.webcheckers.model.moves.Move;
import com.webcheckers.model.moves.Position;
import spark.Request;
import spark.Session;

import java.util.HashMap;
import java.util.Map;

/**
 * Helps add various attributes to the navbar's view model to reduce duplicated code.
 */
public class NavbarUtils {
    static final String PLAYER_KEY = "Player";

    /**
     * Handles adding the current user and title to the navbar
     * @param request the Request
     * @return the view model
     */
    public static Map<String, Object> renderNavBar(Request request){
        Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();

        Player player = httpSession.attribute(PLAYER_KEY);
        if(player != null) {
            vm.put("currentUser", player);
        }
        vm.put("title", "Welcome!");
        return vm;
    }

    public static Player getPlayer(Request request){
        Player player = request.session().attribute(PLAYER_KEY);
        return player;
    }


}
