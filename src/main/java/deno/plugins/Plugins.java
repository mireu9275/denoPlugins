package deno.plugins;

import mysql.mySQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public final class Plugins extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.AQUA + "[돈 플러그인 로딩 완료]");
        getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.AQUA + "[돈 플러그인 언로딩 완료]");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(label.equalsIgnoreCase("gm")){
            Player p = (Player) sender;
            if(p.isOp()){
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("0")){
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(ChatColor.YELLOW + "게임 모드를 서바이벌 모드로 변경하였습니다.");
                    }else if(args[0].equalsIgnoreCase("1")){
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(ChatColor.YELLOW + "게임 모드를 크리에이티브 모드로 변경하였습니다.");
                    }else if(args[0].equalsIgnoreCase("2")){
                        p.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(ChatColor.YELLOW + "게임 모드를 모험 모드로 변경하였습니다.");
                    }else if(args[0].equalsIgnoreCase("3")){
                        p.setGameMode(GameMode.SPECTATOR);
                        p.sendMessage(ChatColor.YELLOW + "게임 모드를 관전자 모드로 변경하였습니다.");
                    }
            }else if (args.length == 2){
                    Player p_online = Bukkit.getPlayerExact(args[1]);
                    if(p_online == null) {
                        p.sendMessage(ChatColor.RED + "해당 유저는 온라인이 아닙니다!");
                        return true;
                    }

                    Player target = Bukkit.getPlayer(args[1]);

                    if(args[0].equalsIgnoreCase("0")){
                        Objects.requireNonNull(target).setGameMode(GameMode.SURVIVAL);
                        target.sendMessage(ChatColor.YELLOW + "게임 모드가 서바이벌 모드로 변경되었습니다.");
                        p.sendMessage(ChatColor.YELLOW + args[1] + " 님의 게임모드를 서바이벌 모드로 변경하였습니다.");
                    }else if (args[0].equalsIgnoreCase("1")){
                        Objects.requireNonNull(target).setGameMode(GameMode.CREATIVE);
                        target.sendMessage(ChatColor.YELLOW + "게임 모드가 크리에이티브 모드로 변경되었습니다.");
                        p.sendMessage(ChatColor.YELLOW + args[1] + " 님의 게임모드를 크리에이티브 모드로 변경하였습니다.");
                    }else if (args[0].equalsIgnoreCase("2")){
                        Objects.requireNonNull(target).setGameMode(GameMode.ADVENTURE);
                        target.sendMessage(ChatColor.YELLOW + "게임 모드가 모험 모드로 변경되었습니다.");
                        p.sendMessage(ChatColor.YELLOW + args[1] + " 님의 게임모드를 모험 모드로 변경하였습니다.");
                    }else if (args[0].equalsIgnoreCase("3")){
                        Objects.requireNonNull(target).setGameMode(GameMode.SPECTATOR);
                        target.sendMessage(ChatColor.YELLOW + "게임 모드가 관전자 모드로 변경되었습니다.");
                        p.sendMessage(ChatColor.YELLOW + args[1] + " 님의 게임모드를 관전자 모드로 변경하였습니다.");
                    }
                }else {
                    p.sendMessage(ChatColor.YELLOW + "'/gm [게임모드] [닉네임]' : [닉네임]의 게임모드를 [게임모드]로 바꿈");
                    p.sendMessage(ChatColor.YELLOW + "서바이벌 0 | 크리에이티브 1 | 모험 2 | 관전자 3");
                }
            }else p.sendMessage(Objects.requireNonNull(this.getConfig().getString("메시지.error")));
        }
        return true;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws SQLException {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        String name = p.getDisplayName();

        mySQL.main("SELECT * FROM USER_MAST WHERE UUID = '" + uuid + "'");

        //if(!p.hasPlayedBefore()){ //처음 접속하였을 떄
           // mySQL.main("Insert into USER_MAST (UUID,NAME) VALUES ('" + uuid +"','" + name +"')");
        //}
    }
}
