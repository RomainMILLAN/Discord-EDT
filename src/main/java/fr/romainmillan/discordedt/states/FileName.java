package fr.romainmillan.discordedt.states;

public enum FileName {
    LOGGER_FILENAME("DiscordEDTLogs.txt");
    private String fileName;
    
    FileName(String fileName) {this.fileName = fileName;}

    /**
     * Retourne le nom du fichier
     * @return
     */
    public String getFileName() {return fileName;}
}
