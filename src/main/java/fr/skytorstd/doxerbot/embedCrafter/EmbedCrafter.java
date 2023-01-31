package fr.skytorstd.doxerbot.embedCrafter;

import fr.skytorstd.doxerbot.manager.DateHourFormatter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbedCrafter {
    protected static String thumbnailURL = "https://cdn.discordapp.com/avatars/1030431310499553400/faded94d8efd123df08a0ae35ce495f6.webp?size=128";

    /**
     * Return embed footer
     * => Le 12/12/2000 à 12:12
     * @return
     */
    protected static String getFooterEmbed(){
        return "Le " + DateHourFormatter.getInstance().getDateTimeFormat() + " à " + DateHourFormatter.getInstance().getHourTimeFormat();
    }

    /**
     * Craft an embed with only description
     * @param description
     * @return
     */
    public static MessageEmbed embedCraftWithDescriptionOnly(String description){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription(description);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

    /**
     * Craft an embed with description and color
     * @param description
     * @param color
     * @return
     */
    public static MessageEmbed embedCraftWithDescriptionAndColor(String description, Color color){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription(description);
        embed.setFooter(getFooterEmbed());
        embed.setColor(color);

        return embed.build();
    }
}
