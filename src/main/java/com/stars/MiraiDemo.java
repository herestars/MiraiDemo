package com.stars;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;

public final class MiraiDemo extends JavaPlugin {
    public static final MiraiDemo INSTANCE = new MiraiDemo();

    private MiraiDemo() {
        super(new JvmPluginDescriptionBuilder("com.stars.mirai", "0.1.0")
                .name("MiraiDemo")
                .author("HanZiXin")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("Plugin loaded!");
    }
}