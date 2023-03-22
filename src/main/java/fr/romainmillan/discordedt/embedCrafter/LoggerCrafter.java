package fr.romainmillan.discordedt.embedCrafter;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;


public class LoggerCrafter extends EmbedCrafter {
    private static final String titleBuilder = "**DISCORD SENTRY/";
    private static final Color colorBuilder = Color.ORANGE;

    /**
     * Return un embed qui servira à log des activité par le bot
     * @param title
     * @param message
     * @return
     */
    public static MessageEmbed craftLogEmbedWithTitleAndMessage(String title, String message, Member member){
        net.dv8tion.jda.api.EmbedBuilder embed = new net.dv8tion.jda.api.EmbedBuilder();
        embed.setTitle("\uD83D\uDCDD " + titleBuilder + title + "**");
        embed.setDescription(title + " - " + member.getAsMention() + "\n > " + message);
        embed.setColor(colorBuilder);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }
}
