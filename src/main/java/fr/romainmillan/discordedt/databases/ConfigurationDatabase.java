package fr.romainmillan.discordedt.databases;

import fr.romainmillan.discordedt.object.Configuration;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationDatabase {

    /**
     * Crée une liste des groupes pour les commandes Slash
     * <pre/>
     * 
     * @return <code>List<Command.Choice></code> Liste des groupes
     */
    public static List<Command.Choice> getAllGroupeChoices(){
        final String sql = "SELECT groupe, nickgroupe FROM CONF";
        List<Command.Choice> res = new ArrayList<>();

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                res.add(new Command.Choice(ResultatSQL.getString("nickgroupe"), ResultatSQL.getString("groupe")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    /**
     * Initialize un nouveau groupe dans la BD.
     * <pre/>
     * 
     * @param configuration du nouveau groupe
     */
    public static void setupGroupe(Configuration configuration){
        final String sql = "INSERT INTO CONF(`groupe`, `nickgroupe`) VALUES('"+configuration.getGroupe()+"', '"+configuration.getNickgroupe()+"');";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Récupére une configuration d'un groupe par son nom de groupe
     * <pre/>
     * 
     * @param groupe nom du groupe à rechercher dans la Base de Données
     * @return <code>Configuration</code> du groupe récupérer dans la Base de Données
     */
    public static Configuration getConfigurationByGroupe(String groupe){
        final String sql = "SELECT * FROM CONF WHERE groupe='"+groupe+"' LIMIT 1";

        try {
            ResultSet resultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(resultatSQL.next()){
                return new Configuration(
                        resultatSQL.getString("groupe"),
                        resultatSQL.getString("nickgroupe"),
                        resultatSQL.getString("url"),
                        resultatSQL.getString("roleid"),
                        resultatSQL.getString("rolenotifid"),
                        resultatSQL.getString("channelid")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Mets à jour une configuration de groupe dans la Base de Données.
     * <pre/>
     * 
     * @param configuration à mettre à jour
     */
    public static void updateConfiguration(Configuration configuration){
        final String sql = "UPDATE CONF SET nickgroupe='"+configuration.getNickgroupe()+"', url='"+configuration.getUrl()+"', roleid='"+configuration.getRoleid()+"', rolenotifid='"+configuration.getRolenotifid()+"', channelid='"+configuration.getChannelId()+"' WHERE groupe='"+configuration.getGroupe()+"'";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Récupére la liste des noms des groupes.
     * <pre/>
     * 
     * @return <code>List<String></code>
     */
    public static List<String> getListGroupe(){
        final String sql = "SELECT groupe FROM CONF";
        List<String> res = new ArrayList<>();

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                res.add(ResultatSQL.getString("groupe"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }
}
