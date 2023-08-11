package fr.romainmillan.discordedt.embedCrafter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

import fr.romainmillan.discordedt.messages.ErrorMessages;
import fr.romainmillan.discordedt.states.MemberPermissionStates;
import fr.romainmillan.discordedt.states.PluginName;
import fr.romainmillan.discordedt.states.messages.IconMessages;
import fr.romainmillan.discordedt.states.messages.application.SystemMessages;

public class ErrorCrafter extends EmbedCrafterBase {
    private static Color colorError = Color.RED;

    private static EmbedBuilder craftErrorEmbedBase(String pluginName) {
        EmbedBuilder embed = craftEmbedBase();

        embed.setColor(colorError)
                .setTitle(IconMessages.ERROR.getIcon() + " " + pluginName);

        return embed;
    }

    public static EmbedBuilder craftErrorDescriptionEmbed(String pluginName, String description) {
        EmbedBuilder embed = craftErrorEmbedBase(pluginName);

        embed.setDescription(description);

        return embed;
    }

    public static EmbedBuilder craftErrorCommand(String pluginName, String commandError) {
        EmbedBuilder embed = craftErrorEmbedBase(pluginName);

        embed.setDescription("> " + IconMessages.ERROR.getIcon() + " " + SystemMessages.INCORRECT_COMMAND.getMessage() + " (`" + commandError + "`)");

        return embed;
    }

    public static EmbedBuilder craftErrorPermission(String pluginName, Member member, String command, MemberPermissionStates memberPermission) {
        EmbedBuilder embed = craftErrorEmbedBase(pluginName);

        StringBuilder description = new StringBuilder();
        description
                .append("> ")
                .append(IconMessages.ERROR.getIcon())
                .append(" ")
                .append(
                        String.format(
                                SystemMessages.INCORRECT_PERMISSION_WITH_PERMISSION.getMessage(),
                                memberPermission.getMessage()
                        )
                );

        embed.setDescription(description)
                .addField("Commande", "`" + command + "`", false);

        return embed;
    }




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
