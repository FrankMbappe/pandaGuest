package panda.guest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import panda.host.models.Authentication;
import panda.host.utils.Current;
import panda.host.utils.Panda;

import java.io.File;
import java.io.IOException;

public class Configs {
    static ObjectMapper mapper = new ObjectMapper();
    static File file = new File(Panda.PATH_TO_CONFIG_FILE);

    /**
     * Deserializes the configurations' file
     * @return The saved authentication
     */
    public static Authentication getSavedAuth(){
        try{
            //System.out.println("[Configs, get()] | Configurations' file content:\n" + config.toString());
            return mapper.readValue(file, Authentication.class);
        } catch (Exception e) {
//            e.printStackTrace();
            System.err.println("[Configs, getSavedAuth] | Failed to deserialize configs file.");
            return null;
        }
    }

    /**
     * Saves an authentication into the configurations' file
     * @param auth The Authentication object
     * @return A boolean: Operation succeeded
     */
    public static boolean saveAuth(Authentication auth){
        try {
            mapper.writeValue(file, auth);
            Current.auth = auth;
            System.out.println("[Configs, saveAuth()] | Configurations has been saved.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean fileIsValid(){
        return getSavedAuth() != null && getSavedAuth().isValid();
    }
}
