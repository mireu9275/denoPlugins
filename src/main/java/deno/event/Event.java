package deno.event;


import deno.config.*;
import deno.mysql.*;
import deno.plugin.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class Event implements Listener {

    public static Plugin plugin;
    private MySQL mysql;

    public static void setPlugin(Plugin plugin){
        Event.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        mysql = new MySQL(plugin,MySQL.url,MySQL.user,MySQL.password);
        Player p = e.getPlayer();
        UUID pUUID = p.getUniqueId();
        String pUUID_str = pUUID.toString();
        String pNAME = p.getDisplayName();

        if(!p.hasPlayedBefore()){
          plugin.getLogger().info("test");
          plugin.getLogger().info(pUUID_str);
          plugin.getLogger().info(pNAME);
          mysql.pFirstJoin(pUUID_str,pNAME);
        }else{ plugin.getLogger().info("test2"); }
    }
}
