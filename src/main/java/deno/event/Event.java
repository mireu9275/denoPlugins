package deno.event;

import deno.mysql.*;
import deno.plugin.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

import static deno.mysql.MySQL.*;

public class Event implements Listener {

    public static Plugin plugin;
    private MySQL mysql;

    public static void setPlugin(Plugin plugin){
        Event.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        mysql = new MySQL(plugin,url,user,password);
        Player p = e.getPlayer();
        UUID pUUID = p.getUniqueId();
        String pUUID_str = pUUID.toString();
        String pNAME = p.getDisplayName();

        if(!p.hasPlayedBefore()) {
            mysql.pFirstJoin(pUUID_str, pNAME);
        }
    }
}
