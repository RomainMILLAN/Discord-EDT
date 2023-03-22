package fr.romainmillan.discordedt.states;

public enum PluginName {
    EDT("EDT");

    private String message;

    PluginName(String message) {
        this.message = message;
    }

    /**
     * Retourne le nom du plugin
     * @return
     */
    public String getMessage() {
        return message;
    }
}
