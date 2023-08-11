package fr.romainmillan.discordedt.states.messages.command;

import fr.romainmillan.discordedt.states.command.SettingsCommandStates;

public enum SettingsCommandMessage {

    COMMAND_DESCRIPTION("Permet de configurer le bot"),

    COMMAND_SETTING("/"+SettingsCommandStates.COMMAND_SETTING_PREFIX.getState()+" <add/edit/remove> <roleid>"),

    OPTION_SELECTOR_DESCRIPTION("action à effectuer"),
    OPTION_SELECTOR_CHOICE_ADD("Ajouter un groupe"),
    OPTION_SELECTOR_CHOICE_EDIT("Editer un groupe"),
    OPTION_SELECTOR_CHOICE_REMOVE("Supprimerget un groupe"),

    TEXT_INPUT_ROLEID_LABEL("Identifiant du groupe"),
    TEXT_INPUT_EDT_URL_LABEL("URL du calendrier"),
    MODAL_ADD_LABEL("Ajouter un groupe"),
    
    OPTION_ROLEID_DESCRIPTION("Identifiant du role groupe"),

    MESSAGE("MESSAGE");

    private final String message;

    SettingsCommandMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
