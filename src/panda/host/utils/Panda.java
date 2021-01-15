package panda.host.utils;


import java.util.ArrayList;
import java.util.HashMap;
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

    // Marshalling and Unmarshalling patterns
    public enum PandaOperation {
        PANDAOP_REQUEST_GET_POSTS, PANDAOP_RESPONSE_GET_POSTS,
        PANDAOP_REQUEST_GET_CONNECTION, PANDAOP_RESPONSE_GET_CONNECTION
    }
    public static final HashMap<PandaOperation, Pattern> PANDA_DECODING_PATTERNS = new HashMap<>(){{
        put(PandaOperation.PANDAOP_REQUEST_GET_CONNECTION,
                Pattern.compile("panda@connect\\?guest='(.*?)'&user='(.*?)'&password='(.*?)'"));
        put(PandaOperation.PANDAOP_REQUEST_GET_POSTS,
                Pattern.compile("panda@getpost\\?all='(.*?)'&filetype='(.*?)'&class='(.*?)'"));
        put(PandaOperation.PANDAOP_RESPONSE_GET_CONNECTION,
                Pattern.compile("panda@connect\\?rescode='(.*?)'&user='(.*?)'&grant='(.*?)'"));
        put(PandaOperation.PANDAOP_RESPONSE_GET_POSTS,
                Pattern.compile("panda@getpost\\?rescode='(.*?)'&data='(.*?)'"));
    }};
    public static final HashMap<PandaOperation, String> PANDA_ENCODING_PATTERNS = new HashMap<>(){{
        put(PandaOperation.PANDAOP_REQUEST_GET_CONNECTION,
                "panda@connect?guest='%s'&user='%s'&password='%s'");
        put(PandaOperation.PANDAOP_REQUEST_GET_POSTS,
                "panda@getpost?all='%s'&filetype='%s'&class='%s'");
        put(PandaOperation.PANDAOP_RESPONSE_GET_CONNECTION,
                "panda@connect?rescode='%s'&user='%s'&grant='%s'");
        put(PandaOperation.PANDAOP_RESPONSE_GET_POSTS,
                "panda@getpost?rescode='%s'&data='%s'");
    }};

}
