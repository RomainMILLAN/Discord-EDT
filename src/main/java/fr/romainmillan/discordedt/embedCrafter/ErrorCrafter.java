package fr.romainmillan.discordedt.embedCrafter;

import fr.romainmillan.discordedt.messages.ErrorMessages;
import fr.romainmillan.discordedt.states.PluginName;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class ErrorCrafter extends EmbedCrafter{
    private static Color errorColor = Color.RED;

    /**
     * Retourne un EmbedBuilder de base pour les erreurs
     * @return
     */
    public static EmbedBuilder errorEmbedCrafter(){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setThumbnail(thumbnailURL);
        embed.setFooter(getFooterEmbed());
        embed.setColor(errorColor);

        return embed;
    }

    /**
     * Retourne un embed erreur avec la description passer en parametre
     * @param description
     * @return
     */
    public static MessageEmbed errorEmbedCrafterWithDescription(String description){
        EmbedBuilder embed = errorEmbedCrafter();
        embed.setDescription(description);

        return embed.build();
    }

    /**
     * Retourne un embed erreur pour une erreur de configuration
     * @param pluginName
     * @return
     */
    public static MessageEmbed errorConfigurationCrafter(PluginName pluginName){
        EmbedBuilder embed = errorEmbedCrafter();
        embed.setDescription(ErrorMessages.ERROR_CONFIGURATION_MESSAGE.getErrorMessage() + "(`"+ pluginName.getMessage()+"`)");

        return embed.build();
    }

    /**
     * Retourne un embed erreur quand un utiliser n'existe pas
     * @return
     */
    public static MessageEmbed errorUserNotExistCrafter(){
        EmbedBuilder embed = errorEmbedCrafter();
        embed.setDescription(ErrorMessages.ERROR_USER_NOT_EXIST.getErrorMessage());

        return embed.build();
    }

    /**
     * Retourne un embed erreur quand un utilisateur n'as pas une permission
     * @param member
     * @return
     */
    public static MessageEmbed errorNotPermissionToCommand(Member member){
        EmbedBuilder embed = errorEmbedCrafter();
        embed.setDescription(ErrorMessages.ERROR_USER_NOT_PERMIT.getErrorMessage());

        return embed.build();
    }

    /**
     * Retoure un embed erreur quand la commande effectué n'est pas correcte
     * @return
     */
    public static MessageEmbed errorCommand(){
        EmbedBuilder embed = errorEmbedCrafter();
        embed.setDescription(ErrorMessages.ERROR_COMMAND.getErrorMessage());

        return embed.build();
    }


}
