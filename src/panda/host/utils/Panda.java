package panda.host.utils;


import com.sun.media.jfxmediaimpl.platform.Platform;
import panda.guest.config.Configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Panda.
 */
public class Panda {
    // Default remote object URL
    public static final String DEFAULT_REMOTE_URL = "rmi://localhost/panda";

    // Default app port
    public static final int DEFAULT_PORT = 77;

    // Default file path
    public static final String PATH_TO_CONFIG_FILE = "src/panda/guest/config/configs.json";

    // Default values
    public static final String DEFAULT_SPLIT_CHAR = ";";
    public static final String DEFAULT_DATE_FORMAT = "E, dd MMM yyyy";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm";


    // Marshalling and Unmarshalling patterns
    public enum PandaOperation {
        PANDAOP_REQUEST_GET_POSTS, PANDAOP_RESPONSE_GET_POSTS,
        PANDAOP_REQUEST_GET_CONNECTION, PANDAOP_RESPONSE_GET_CONNECTION
    }
    public static final HashMap<PandaOperation, String> PANDA_ENCODING_PATTERNS = new HashMap<>(){{
        put(PandaOperation.PANDAOP_REQUEST_GET_CONNECTION,
                "panda@connect?user='%s'&password='%s'");
        put(PandaOperation.PANDAOP_REQUEST_GET_POSTS,
                "panda@getpost?all='%s'&filetype='%s'&class='%s'");
        put(PandaOperation.PANDAOP_RESPONSE_GET_CONNECTION,
                "panda@connect?rescode='%s'&user='%s'&grant='%s'");
        put(PandaOperation.PANDAOP_RESPONSE_GET_POSTS,
                "panda@getpost?rescode='%s'&data='%s'");
    }};

    public static void exit(){
        Configs.saveAuth(Current.auth);
        System.exit(0);
    }

}
