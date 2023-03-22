package fr.romainmillan.discordedt.manager;

import fr.romainmillan.discordedt.states.FileName;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
    private static WriteFile instance = null;

    /**
     * Constructeur vide de singleton
     */
    public WriteFile() {}

    /**
     * Retourne l'instance et la crée si elle n'existe pas
     * @return
     */
    public static WriteFile getInstance() {
        if(instance == null){
            instance = new WriteFile();
        }
        return instance;
    }

    public void writeOnFile(FileName fileName, String toWrite) {
        File file = new File(fileName.getFileName());

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileWriter fw = null;
        try {
            fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(toWrite);
            bw.close();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
