package de.tuberlin.tkn.lit.util;

import de.tuberlin.tkn.lit.constants.UriConstants;

import java.util.UUID;

public class UriUtilities {
    public static boolean isLocaleServer(String uri) {
        return uri.startsWith(UriConstants.HOST);
    }

    public static String getActor(String uri) {
        String[] strings = uri.split("/");
        return strings[3];
    }

    public static String generateId(String[] idParameter) {
        return UriConstants.HOST + String.join("/", idParameter) + "/" + UUID.randomUUID();
    }

    public static String generateId(String[] idParameter, UUID id) {
        return UriConstants.HOST + String.join("/", idParameter) + "/" + id;
    }
}
