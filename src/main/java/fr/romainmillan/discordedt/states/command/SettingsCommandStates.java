package fr.romainmillan.discordedt.states.command;

public enum SettingsCommandStates {
    COMMAND_NAME("Settings"),

    COMMAND_SETTING_PREFIX("setting"),

    OPTION_SELECTOR_NAME("action"),
    OPTION_SELECTOR_CHOICE_ADD("add"),
    OPTION_SELECTOR_CHOICE_EDIT("edit"),
    OPTION_SELECTOR_CHOICE_REMOVE("remove"),

    TEXT_INPUT_ROLE_NAME("ti_roleid"),
    TEXT_INPUT_EDT_URL_NAME("ti_edturl"),
    MODAL_ADD("m_add_setting"),
    
    MODAL_EDIT("m_edit_setting"),
    
    OPTION_ROLEID_NAME("role");

    private final String state;

    SettingsCommandStates(String message) {
        this.state = message;
    }

    public String getState() {
        return state;
    }
}
