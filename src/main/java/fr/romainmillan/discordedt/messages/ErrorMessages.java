package fr.romainmillan.discordedt.messages;

public enum ErrorMessages {
    ERROR_CONFIGURATION_MESSAGE(":x: La configuration du plugin n'est pas activé "),
    ERROR_USER_NOT_EXIST(":x: L'utilisateur demandé n'existe pas."),
    ERROR_USER_NOT_PERMIT(":x: Vous n'avez pas la permission d'effectué ceci"),
    ERROR_COMMAND(":x: La commande effectué n'est pas correcte");

    private String errorMessage;
    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
