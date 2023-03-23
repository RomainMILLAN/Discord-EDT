package fr.romainmillan.discordedt.object;

import fr.romainmillan.discordedt.databases.ConfigurationDatabase;

public class Cour {
    private int id;
    private String groupe;
    private String name;
    private String salle;
    private String professeur;
    private String heureDebut;
    private String heureFin;
    private String date;
    private String information;

    public Cour(int id, String groupe, String name, String salle, String professeur, String heureDebut, String heureFin, String date, String information) {
        this.id = id;
        this.groupe = groupe;
        this.name = name;
        this.salle = salle;
        this.professeur = professeur;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.date = date;
        this.information = information;
    }

    public int getId() {
        return id;
    }

    public String getGroupe() {
        return groupe;
    }

    public String getGroupeFr(){
        Configuration conf = ConfigurationDatabase.getConfigurationByGroupe(groupe);

        if(conf != null){
            return conf.getNickgroupe();
        }else {
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public String getSalle() {
        return salle;
    }

    public String getProfesseur() {
        return professeur;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public String getInformation() {
        return information;
    }

    public String getDate() {
        return date;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
