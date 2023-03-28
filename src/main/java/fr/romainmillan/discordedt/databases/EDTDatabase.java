package fr.romainmillan.discordedt.databases;

import fr.romainmillan.discordedt.object.Cour;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EDTDatabase {
    /**
     * Return id max
     * @return
     */
    public static int getMaxId(){
        final String sql = "SELECT MAX(id) as id FROM DB_EDT";

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                return ResultatSQL.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return -1;
    }

    /**
     * Return curse by id
     * @return
     */
    public static Cour getCourById(int id){
        final String sql = "SELECT * FROM DB_EDT WHERE id='"+id+"'";

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                return new Cour(ResultatSQL.getInt("id"), ResultatSQL.getString("groupe"), ResultatSQL.getString("name"), ResultatSQL.getString("salle"), ResultatSQL.getString("professeur"), ResultatSQL.getString("heureDebut"), ResultatSQL.getString("heureFin"), ResultatSQL.getString("date"), ResultatSQL.getString("information"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Return a liste of course by date and group
     * @param date
     * @param groupe
     * @return
     */
    public static ArrayList<Cour> getListCourByDateAndGroupe(String date, String groupe){
        final String sql = "SELECT * FROM DB_EDT WHERE date='"+date+"' AND groupe='"+groupe+"' ORDER BY heureDebut ASC";
        ArrayList<Cour> resultat = new ArrayList<>();

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                resultat.add(new Cour(ResultatSQL.getInt("id"), ResultatSQL.getString("groupe"), ResultatSQL.getString("name"), ResultatSQL.getString("salle"), ResultatSQL.getString("professeur"), ResultatSQL.getString("heureDebut"), ResultatSQL.getString("heureFin"), ResultatSQL.getString("date"), ResultatSQL.getString("information")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultat;
    }

    public static Cour getNextCour(String date, String heure, String groupe){
        final String sql = "SELECT * FROM DB_EDT WHERE date='"+date+"' AND heureDebut > '"+heure+"' AND groupe='"+groupe+"' ORDER BY date,heureDebut ASC LIMIT 1";

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                return new Cour(ResultatSQL.getInt("id"), ResultatSQL.getString("groupe"), ResultatSQL.getString("name"), ResultatSQL.getString("salle"), ResultatSQL.getString("professeur"), ResultatSQL.getString("heureDebut"), ResultatSQL.getString("heureFin"), ResultatSQL.getString("date"), ResultatSQL.getString("information"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
    /*
    AJOUT
     */
    /**
     * Add course on DB
     * @param cour
     */
    public static void ajoutCour(Cour cour){
        final String sql = "INSERT INTO DB_EDT('id', 'groupe', 'name', 'salle', 'professeur', 'heureDebut', 'heureFin', 'date', 'information') VALUES('"+cour.getId()+"', '"+cour.getGroupe()+"', '"+cour.getName()+"', '"+cour.getSalle()+"', '"+cour.getProfesseur()+"', '"+cour.getHeureDebut()+"', '"+cour.getHeureFin()+"', '"+cour.getDate()+"', '"+cour.getInformation()+"');";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    REMOVE
     */

    /**
     * Delete course on DB by groupe
     * @param groupe
     */
    public static void deleteAllCourByGroupeId(String groupe){
        final String sql = "DELETE FROM DB_EDT WHERE groupe='"+groupe+"'";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete course on DB by date and groupe
     * @param groupe
     */
    public static void deleteAllCourAfterDateByGroupe(String date, String groupe){
        final String sql = "SELECT * FROM DB_EDT WHERE groupe='"+groupe+"'";

        String[] dateOfRefresh = date.split("/");
        int dayOfRefresh = Integer.parseInt(dateOfRefresh[0]);
        int monthOfRefresh = Integer.parseInt(dateOfRefresh[1]);
        int yearOfRefresh = Integer.parseInt(dateOfRefresh[2]);

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                String[] dateSplit = ResultatSQL.getString("date").split("/");
                int day = Integer.parseInt(dateSplit[0]);
                int month = Integer.parseInt(dateSplit[1]);
                int year = Integer.parseInt(dateSplit[2]);

                if((year > yearOfRefresh) || (year == yearOfRefresh && month == monthOfRefresh && day >= dayOfRefresh) || (year == yearOfRefresh && month > monthOfRefresh)){
                    final String sqlToDelete = "DELETE FROM DB_EDT WHERE id='"+ResultatSQL.getString("id")+"'";

                    DatabaseConnection.getInstance().getStatement().execute(sqlToDelete);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    UPDATE
     */
    public static void updateInformationOfCourse(Cour cour){
        final String sql = "UPDATE DB_EDT SET information='"+cour.getInformation()+"' WHERE id='"+cour.getId()+"';";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
