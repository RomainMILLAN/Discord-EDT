package fr.romainmillan.discordedt.messages;

public enum LoggerMessages {
    PLUGIN_CONFIGURATION_DESACTIVE("Plugin désactivé"),
    USER_NO_EXIST("Utilisateur inexistant"),
    USER_NO_PERMISSION("Permission manquante"),
    COMMAND_NOT_EXIST("Commande incorrecte");

    private String message;

    LoggerMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
