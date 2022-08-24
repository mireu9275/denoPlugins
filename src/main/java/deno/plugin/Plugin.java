package deno.plugin;

import deno.config.Config;
import deno.mysql.MySQL;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class Plugin extends JavaPlugin {

    private MySQL mysql;
    private Config money;
    private Config config;
    public String host,port,user,password,database; //MySQL 변수선언
    public String Money_user_msg1,Money_user_msg2,Money_op_msg1,Money_op_msg2,Money_op_msg3,Money_error_msg1_NF; //Money 플러그인변수 선언
    public String url;


    @Override
    public void onEnable() {
        getLogger().info(ChatColor.AQUA + "[플러그인 로딩 완료]");

        config = new Config(this,"Config.yml");
        money = new Config(this, "Money.yml");

        config.saveDefaultConfig();
        money.saveDefaultConfig();

        host = config.getConfig().getString("MySQL.host");
        port = config.getConfig().getString("MySQL.port");
        user = config.getConfig().getString("MySQL.user");
        password = config.getConfig().getString("MySQL.password");
        database = config.getConfig().getString("MySQL.database");
        url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";

        mysql = new MySQL(this, url, user, password);

        boolean result = this.mysql.conn_test("TESTUUID");
        if(!result){
            throw new RuntimeException("MySQL conn error");
        }

        Money_user_msg1 = money.getConfig().getString("Money.user_msg1");
        Money_user_msg2 = money.getConfig().getString("Money.user_msg2");
        Money_op_msg1 = money.getConfig().getString("Money.op_msg1");
        Money_op_msg2 = money.getConfig().getString("Money.op_msg2");
        Money_op_msg3 = money.getConfig().getString("Money.op_msg3");
        Money_error_msg1_NF = money.getConfig().getString("Money.error_msg1_NF");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.AQUA + "[플러그인 언로딩 완료]");
    }

    @Override
    public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args){
        if (label.equalsIgnoreCase("돈") || label.equalsIgnoreCase("money")){
            Player p = (Player) sender;
            UUID pUUID = p.getUniqueId();

            //args[4]까지 연동되게 만들기.
            if (args.length == 4){
                if(args[1].equalsIgnoreCase("보내기")) {

                }else if(p.isOp()){
                    if(args[1].equalsIgnoreCase("주기")){
                    }else if(args[1].equalsIgnoreCase("뺏기")){

                    }else if(args[1].equalsIgnoreCase("설정")){

                    }else{
                        p.sendMessage(Money_error_msg1_NF);
                    }
                }else {
                    p.sendMessage(Money_error_msg1_NF);
                }
            }else
            {
                p.sendMessage(Money_user_msg1);
                p.sendMessage(Money_user_msg2);
                if (p.isOp()){
                    p.sendMessage(Money_op_msg1);
                    p.sendMessage(Money_op_msg2);
                    p.sendMessage(Money_op_msg3);
                }
            }
        }
        return true;
    }

    private static Plugin instance;
    public Plugin(){instance = this;}
    public static Plugin getInstance(){return instance;}
    public MySQL mysql(){
        return this.mysql;
    }
    public Config config(){
        return this.config;
    }
}