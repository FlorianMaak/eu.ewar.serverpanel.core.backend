package eu.ewar.serverpanel.core.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;


public class ServerConfig {

    private Config config;


    public ServerConfig() {
        config = ConfigFactory.load("Server.conf");
    }
    
    
    public String getKeystoreURL() {
        return config.getString("keystore.URL");
    }
    
    public String getKeystorPass() {
        return config.getString("keystore.pass");
    }
}
