package fr.romainmillan.discordedt.messages;

public enum ConsoleCommanderMessages {

    CC_INFO_STOP("!stop - Arreter le bot"),
    CC_INFO_RELOAD("!reload - Relancer le bot"),
    CC_STOP("Arrêt du bot en cour"),
    CC_RELOAD("Reload du bot en cour"),
    CC_ERROR_COMMAND("Cette commande n'existe pas");

    private String message;

    ConsoleCommanderMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
