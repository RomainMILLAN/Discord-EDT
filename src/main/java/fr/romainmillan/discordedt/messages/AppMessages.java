package fr.romainmillan.discordedt.messages;

public enum AppMessages {
    ACTIVITY_PLAYING_BOT("Discord-EDT UM"),
    JDA_BOT_INITIALIZING("Initialization de Discord EDT"),
    JDA_BOT_CONNECTED("Discord EDT est connecté"),
    JDA_BOT_READY("Discord EDT est prêt");

    private String message;

    AppMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
