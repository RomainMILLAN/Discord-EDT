package fr.romainmillan.discordedt.messages;

public enum AppMessages {
    ACTIVITY_PLAYING_BOT("Développement de Doxer in progress ..."),
    JDA_BOT_INITIALIZING("Initialization de Doxer"),
    JDA_BOT_CONNECTED("Doxer est connecté"),
    JDA_BOT_READY("Doxer est prêt");

    private String message;

    AppMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
