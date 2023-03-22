package fr.romainmillan.discordedt.messages;

public enum EDTMessages {
    DESCRIPTION_EDT_COMMAND("Permet d'utiliser le plugin d'emploi du temp"),
    DESCRIPTION_EDT_ACTION_ARGUMENT("Permet de définir l'action à effectué"),
    DESCRIPTION_EDT_GROUPE_ARGUMENT("Permet de définir le groupe à voir"),
    DESCRIPTION_EDT_DATE_ARGUMENT("Permet de définir la date du cour"),
    DESCRIPTION_EDT_INFO_ARGUMENT("Permet de définir l'information à modifier"),
    DESCRIPTION_EDT_ID_ARGUMENT("Permet de définir l'id du cour à regarder"),
    NO_GROUP_SELECT("Aucun groupe sélectionner et aucun groupe dans les rôles"),
    NONE_COUR_WITH_ID("Aucun cour avec cette identifiant"),
    EDT_DOING_REFRSH("Emploi du temps en cours de rafraichissement"),
    EDT_REFRESH("Emploi du temp refresh"),
    NO_COUR_TO_DATE("Aucun cour pour cette journée"),
    SHOW_COUR_LIST("Affichage liste des cours"),
    SHOW_COUR("Affichage de cour"),
    NO_INFORMATION_ARGUMENT("Aucune information rentré en parametre"),
    NO_ID_ARGUMENT("Aucun identifiant renseigner"),
    INFORMATION_UPDATE("Information du cour mise à jour"),
    DESCRIPTION_NOTIFICATION_COMMAND("Permet d'avoir les notification des cours");

    private String message;

    EDTMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
