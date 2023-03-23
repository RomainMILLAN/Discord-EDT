package fr.romainmillan.discordedt.messages;

public enum ConfigurationMessages {
    DESCRIPTION_SETUP_COMMAND("Permet de mettre en place un nouveau groupe"),
    DESCRIPTION_ARG_GROUPE("Grouper à initializer"),
    DESCRIPTION_COMMAND("Permet de configurer un groupe"),
    DESCRIPTION_ARG_ACTION("Action à effectuer"),
    DESCRIPTION_ARG_NICKNAME("Nickname du groupe"),
    DESCRIPTION_ARG_URL("URL de l'emploi du temp du groupe"),
    DESCRIPTION_ARG_GRP("Groupe"),
    NO_GROUP_FIND("Aucun groupe n'est enregistré sous se nom");

    private String messages;

    ConfigurationMessages(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }
}
