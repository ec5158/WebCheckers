package com.webcheckers.util;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.webcheckers.model.Player;

/**
 * A class used to help handle validating the username of a player.
 */
public final class UsernameValidator {

    public static final UsernameValidity NOT_ALPHANUMERIC = null;
	public static final UsernameValidity NAME_TAKEN = null;
	public static final UsernameValidity VALID = null;
	private static Pattern validUsernamePattern = Pattern.compile("[a-zA-Z1-9 ]*[a-zA-Z1-9][a-zA-Z1-9 ]*");

    /**
     * An enum to represent the statuses of a Username: Not Alphanumeric, Already taken, or Valid
     */
    public enum UsernameValidity {
        NOT_ALPHANUMERIC,
        NAME_TAKEN,
        VALID
    }

    /**
     * Validate if a player's username is valid as per the requirements for the username and currently unused.
     * The username must be made of alphanumeric characters and spaces, with at least one alphanumeric character
     * @param playerSet The set of currently logged in players
     * @param player Player whose username will be checked
     * @return a {@link UsernameValidator} representing the status of the valididty check
     */
    public static UsernameValidity isValidUsername(Set<Player> playerSet, Player player) {
        UsernameValidity validity = UsernameValidity.VALID;

        if (playerSet.contains(player)) {
            validity = UsernameValidity.NAME_TAKEN;
        }

        Matcher validMatcher = validUsernamePattern.matcher(player.getName());

        if (!validMatcher.matches()) {
            validity = UsernameValidity.NOT_ALPHANUMERIC;
        }

        return validity;
    }
}