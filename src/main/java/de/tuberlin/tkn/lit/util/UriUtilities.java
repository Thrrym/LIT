package de.tuberlin.tkn.lit.util;

import de.tuberlin.tkn.lit.constants.UriConstants;

import java.util.UUID;
import java.util.logging.Logger;

public class UriUtilities {
    private static final Logger logger = Logger.getLogger(UriUtilities.class.getName());

    public static boolean isLocaleServer(String uri, int port) {
        return uri.startsWith(UriConstants.HOST + port);
    }

    public static String getActor(String uri) {
        String actor = "";
        String[] strings = uri.split("/");
        try {
            actor = strings[3];
        } catch (Exception ex) {
            logger.warning("Actor URL was in wrong format: " + uri);
        }
        return actor;
    }

    public static String generateId(String[] idParameter, int port, boolean uuidShouldBeAppended) {
        String id = UriConstants.HOST + port + "/" + String.join("/", idParameter) + "/";
        if (uuidShouldBeAppended) {
            id += UUID.randomUUID();
        }
        return id;
    }

    public static String generateId(String[] idParameter, int port, UUID id) {
        return UriConstants.HOST + port + "/" + String.join("/", idParameter) + "/" + id;
    }
}
