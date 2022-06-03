package me.bl.Utils;

import java.awt.*;
import java.io.IOException;

public class WebhookHandler {
    public static void Webhook(String Link, String Player) throws IOException {
        DiscordWebhook webhook = new DiscordWebhook(Link);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setColor(Color.RED).addField("Name", Player, true)
                .addField("Status", "Using VPN", true)
                .setThumbnail("https://mc-heads.net/avatar/identifier/100/" + Player));
        webhook.execute();
    }
}
