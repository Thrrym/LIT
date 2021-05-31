package de.tuberlin.tkn.lit.util;

public class UriUtilities {
    // TODO: must be made configurable...
    private static final String LOCALE_ADDRESS = "http://localhost:8080/";

    public static boolean isLocaleServer(String uri) {
        return uri.startsWith(LOCALE_ADDRESS);
    }

    public static String getActor(String uri) {
        String[] strings = uri.split("/");
        return strings[3];
    }
}
