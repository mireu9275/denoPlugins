package deno.money.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Config {
    public String fileName;
    private final JavaPlugin plugin;
    public File ConfigFile;
    private FileConfiguration Configuration;

    public Config(JavaPlugin plugin, String fileName) {
        if (plugin == null) {
            throw new IllegalArgumentException("plugin cannot be null"); //지정된 메시지가 있는 예외 처리
        }
        this.plugin = plugin;
        this.fileName = fileName;
        File dataFolder = plugin.getDataFolder();
        if (dataFolder == null) {
            throw new IllegalStateException(); //세부 메시지가 없는 예외 처리
        }
        this.ConfigFile = new File(dataFolder.toString() + File.separatorChar + this.fileName);
    }

    public void reloadConfig() {
        try {
            this.Configuration = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(this.ConfigFile), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStream defConfigStream = this.plugin.getResource(this.fileName);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            this.Configuration.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (this.Configuration == null) {
            reloadConfig();
        }
        return this.Configuration;
    }

    public void saveConfig() {
        if ((this.Configuration == null) || (this.ConfigFile == null)) {
            return;
        }
        try {
            getConfig().save(this.ConfigFile);
        } catch (IOException ex) {
            this.plugin.getLogger().info("Could not save config to " + this.ConfigFile);
        }
    }

    public void saveDefaultConfig() {
        if (!this.ConfigFile.exists()) {
            this.plugin.saveResource(this.fileName, false);
        }

    }
}
