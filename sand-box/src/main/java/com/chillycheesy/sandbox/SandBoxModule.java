package com.chillycheesy.sandbox;

import com.chillycheesy.modulo.config.Configuration;
import com.chillycheesy.modulo.config.ConfigurationFactory;
import com.chillycheesy.modulo.config.FileConfigurationFactory;
import com.chillycheesy.modulo.config.YamlConfigurationStrategy;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.sandbox.pages.SandBoxPageManager;

import java.io.IOException;
import java.util.stream.Collectors;

public class SandBoxModule extends Module {

    public static SandBoxModule instance;

    private SandBoxPageManager pageManager;

    @Override
    protected void onLoad() {
        instance = this;
        pageManager = new SandBoxPageManager(this);
    }

    @Override
    protected void onStart() throws IOException {
        info("hello, world test");
        pageManager.loadPages();
        final ConfigurationFactory configurationFactory = new FileConfigurationFactory(this, "config.yml");
        final Configuration configuration = configurationFactory.createConfiguration(new YamlConfigurationStrategy());
        info("all config: " + configuration.<String>getList("colors").stream().map(String::toUpperCase).collect(Collectors.joining(", ")));

        configurationFactory.saveConfiguration(configuration, new YamlConfigurationStrategy());
    }

    @Override
    protected void onStop() {

    }

}
