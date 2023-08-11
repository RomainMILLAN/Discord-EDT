package fr.romainmillan.discordedt.manager;

import java.util.HashMap;

import fr.romainmillan.discordedt.states.ConsoleState;
import fr.romainmillan.discordedt.states.messages.application.SystemMessages;
import io.github.cdimascio.dotenv.Dotenv;

public class Configuration {

    private HashMap<String, String> configuration;
    
    public Configuration(){
        this.configuration = new HashMap<>();

        this.loadConfiguration();
    }

    /**
     * Load the configuration of .env file and store it on configuration property class
     */
    private void loadConfiguration(){
        Dotenv configurationDotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

        configurationDotenv.entries().forEach(element -> {
            this.configuration.put(element.getKey(), element.getValue());
        });

        ConsoleManager.getInstance().toConsole(SystemMessages.CONFIGURATION_REGISTER.getMessage(), ConsoleState.INFO);
    }

    /**
     * @param configurationKey
     * @return <code>String</code> value of key passed in parameter in .env
     */
    public String getConfiguration(String configurationKey) {
        String res = "";

        res = this.configuration.get(configurationKey);

        return res;
    }
    
}
