package com.notmarra.notmenus.utils;

import com.notmarra.notmenus.NotMenus;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Files {
    public static void createFile(String name) {
        File file = new File(NotMenus.getInstance().getDataFolder().getAbsolutePath(), name);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            NotMenus.getInstance().saveResource(name, false);
        }

        try {
            Reader reader = new InputStreamReader(NotMenus.getInstance().getResource(name));
            YamlConfiguration.loadConfiguration(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static YamlConfiguration loadFile(String name) {
        File file = new File(NotMenus.getInstance().getDataFolder().getAbsolutePath(), name);
        if (!file.exists()) {
            createFile(name);
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    public String[] getFileNamesFromDirectory(String directory) {
        File folder = new File(NotMenus.getInstance().getDataFolder(), directory);
        if (!folder.exists()) {
            createFile(directory);
        }

        String[] fileNames = folder.list();
        return fileNames != null ? fileNames : new String[0];
    }


}
