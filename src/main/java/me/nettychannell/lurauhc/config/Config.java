package me.nettychannell.lurauhc.config;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@Getter
public class Config {

    private File file;
    private FileConfiguration fileConfiguration;

    public Config(JavaPlugin javaPlugin, String path) {
        File configFile = new File(javaPlugin.getDataFolder(), path);
        file = configFile;

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            javaPlugin.saveResource(path, false);
        }

        FileConfiguration configuration = new YamlConfiguration();

        try {
            configuration.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        fileConfiguration = configuration;
    }

    public void reload() {
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
