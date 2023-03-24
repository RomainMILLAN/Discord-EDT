package fr.romainmillan.discordedt.embedCrafter;

import fr.romainmillan.discordedt.object.Cour;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;

public class EDTCrafter extends EmbedCrafter{
    private static Color colorEDT = Color.MAGENTA;

    /**
     * Retourne un embed d'un cour.
     * <pre/>
     * 
     * @param cour Cour de l'embed
     * @return <code>MessageEmbed</code>
     */
    public static MessageEmbed craftEDTCourEmbed(Cour cour){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDCDA **Cour** - " + cour.getId());
        embed.addField("Heure début: ", cour.getHeureDebut(), true);
        embed.addField("Heure fin: ", cour.getHeureFin(), true);
        embed.addField("Date: ", cour.getDate(), true);
        embed.addField("Professeur: ", cour.getProfesseur(), true);
        embed.addField("Groupe: ", cour.getGroupe(), true);
        embed.addField("Information: ", cour.getInformation(), true);
        embed.setColor(colorEDT);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

    /**
     * Retourne un embed du prochain cour
     * <pre/>
     * 
     * @param cour Prochain cour
     * @return <code>MessageEmbed</code>
     */
    public static MessageEmbed craftNextCourEmbed(Cour cour){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDCDA Prochain **Cour**");
        embed.addField("Heure début: ", cour.getHeureDebut(), true);
        embed.addField("Heure fin: ", cour.getHeureFin(), true);
        embed.addField("Date: ", cour.getDate(), true);
        embed.addField("Professeur: ", cour.getProfesseur(), true);
        embed.addField("Groupe: ", cour.getGroupeFr(), true);
        embed.addField("Identifiant: ", String.valueOf(cour.getId()), true);

        if(!cour.getInformation().equals("") && !cour.getInformation().equals(" ") && cour.getInformation() != null && cour.getInformation().length() > 1)
            embed.addField("Information: ", cour.getInformation(), true);
        
        embed.setColor(colorEDT);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

    /**
     * Retourne un embed de la liste des cours
     * <pre/>
     * 
     * @param cours Liste des cours
     * @return <code>MessageEmbed</code>
     */
    public static MessageEmbed craftEDTListCour(ArrayList<Cour> cours){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDCDA **Cours** - [" + cours.get(0).getGroupeFr() + "] (*" + cours.get(0).getDate() + "*)");
        
        String description = "Liste des cours: \n";
        for(Cour cour : cours){
            description += " ■ [*" + cour.getId() + "*] *"+cour.getProfesseur()+"* **" + cour.getName() + "** - `" + cour.getHeureDebut() + "`/`" + cour.getHeureFin() + "`\n";
        }
        
        embed.setDescription(description);
        embed.setColor(colorEDT);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

    /**
     * Retourne l'embed des notification des cours
     * <pre/>
     * 
     * @param cour Cour de la notification
     * @return <code>MessageEmbed</code>
     */
    public static MessageEmbed craftNotification(Cour cour){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDCDA **Notification** - Cours");
        embed.setDescription("Début du cour de `" + cour.getName() + "`");
        embed.addField("Heure début: ", cour.getHeureDebut(), true);
        embed.addField("Heure fin: ", cour.getHeureFin(), true);
        embed.addField("Groupe: ", cour.getGroupe(), true);
        embed.addField("Professeur: ", cour.getProfesseur(), true);

        String information = cour.getInformation();
        if(!cour.getInformation().equals("") && !cour.getInformation().equals(" ") && cour.getInformation() != null && cour.getInformation().length() > 1)
            embed.addField("Information: ", information, true);

        embed.setColor(colorEDT);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }
}
