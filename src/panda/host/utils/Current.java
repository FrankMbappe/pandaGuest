package panda.host.utils;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang3.RandomStringUtils;
import panda.host.models.Authentication;
import panda.host.models.Post;

public class Current {
    /**
     * The current authenticated user
     */
    public static Authentication auth;

    /**
     * A random ID that the app will us as its ID.
     * A new one is regenerated at each new execution of the program.
     */
    public static final String ID = RandomStringUtils.random(7, true, true);

    /**
     * If the PandaHost server is connected and running
     */
    public static SimpleBooleanProperty serverIsRunning = new SimpleBooleanProperty(false);

    /**
     * The list of posts retrieved from the PandaHost server
     */
    public static ObservableList<Post> postList = FXCollections.observableArrayList();
}
