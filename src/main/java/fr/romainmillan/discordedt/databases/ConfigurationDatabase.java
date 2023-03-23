package fr.romainmillan.discordedt.databases;

import fr.romainmillan.discordedt.object.Configuration;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationDatabase {

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

        System.out.println(res);

        return res;
    }

    public static void setupGroupe(Configuration configuration){
        final String sql = "INSERT INTO CONF(`groupe`, `nickgroupe`) VALUES('"+configuration.getGroupe()+"', '"+configuration.getNickgroupe()+"');";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    public static void updateConfiguration(Configuration conf){
        final String sql = "UPDATE CONF SET nickgroupe='"+conf.getNickgroupe()+"', url='"+conf.getUrl()+"', roleid='"+conf.getRoleid()+"', rolenotifid='"+conf.getRolenotifid()+"', channelid='"+conf.getChannelId()+"'";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
