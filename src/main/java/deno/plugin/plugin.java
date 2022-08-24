package deno.plugin;

import deno.config.Config;
import deno.mysql.MySQL;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Plugin extends JavaPlugin {

    private MySQL mysql;
    private Config config;

    public String host;
    public String port;
    public String user;
    public String password;
    public String database;

    public String url;


    @Override
    public void onEnable() {
        getLogger().info(ChatColor.AQUA + "[플러그인 로딩 완료]");

        config = new Config(this,"Config.yml");

        this.saveConfig(); //config 파일 저장
        File cfile = new File(getDataFolder(),"config.yml");

        if(cfile.length() == 0){ //config 파일에 아무것도 적혀있지 않다면 실행함.
            getConfig().options().copyDefaults(true); //현재 config 파일에 있는 내용을 불러옴.
            saveConfig(); // config 파일 저장.
        }

        host = config.getConfig().getString("MySQL.host");
        port = config.getConfig().getString("MySQL.port");
        user = config.getConfig().getString("MySQL.user");
        password = config.getConfig().getString("MySQL.password");
        database = config.getConfig().getString("MySQL.database");
        url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";

        mysql = new MySQL(this, url, user, password);

        boolean result = this.mysql.conn_test();
        if(!result){
            throw new RuntimeException("MySQL conn error");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.AQUA + "[플러그인 언로딩 완료]");
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