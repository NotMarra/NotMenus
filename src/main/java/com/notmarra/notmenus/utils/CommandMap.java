package com.notmarra.notmenus.utils;

import java.util.Arrays;

public class CommandMap {
    public static void registerCommands(CCommand... commands) {
        Arrays.stream(commands).forEach(command -> scm.register("NotMenus", command));
    }

    public void setupSimpleCommandMap() {

    }
}
