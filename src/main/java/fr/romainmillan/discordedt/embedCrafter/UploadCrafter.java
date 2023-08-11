package fr.romainmillan.discordedt.embedCrafter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.SelfUser;

import java.awt.*;

import fr.romainmillan.discordedt.states.messages.IconMessages;
import fr.romainmillan.discordedt.states.messages.application.UploadMessages;

public class UploadCrafter extends EmbedCrafterBase {

    private static EmbedBuilder craftUploadEmbedBase() {
        EmbedBuilder embed = craftEmbedBase();

        embed
                .setTitle(IconMessages.WRENCH.getIcon() + " **Connexion**")
                .setColor(Color.orange);

        return embed;
    }

    public static EmbedBuilder craftConnectEmbed(SelfUser user) {
        EmbedBuilder embed = craftUploadEmbedBase();

        embed
                .setColor(Color.GREEN)
                .setDescription(
                    String.format(
                            UploadMessages.DISCORD_CONNECTED.getMessage(),
                            user.getAsMention()
                    )
                );

        return embed;
    }

    public static EmbedBuilder craftDisconnectEmbed(SelfUser user) {
        EmbedBuilder embed = craftUploadEmbedBase();

        embed
                .setColor(Color.RED)
                .setDescription(
                        String.format(
                                UploadMessages.DISCORD_DISCONNECTED.getMessage(),
                                user.getAsMention()
                        )
                );

        return embed;
    }

}
