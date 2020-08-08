package it.core;

import java.util.Arrays;

public enum Commands {
    LOGIN, LOGOUT, MSG, JOIN, JOINU, LEAVE, MSGT;


    public static boolean isCommand(String candidate){
        return Arrays.asList(values()).stream().map(Commands::toString).anyMatch(command -> command.equals(candidate.toUpperCase()));
    }
}
