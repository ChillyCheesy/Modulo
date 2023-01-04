package com.chillycheesy.modulo.config;

import com.chillycheesy.modulo.modules.Module;

import java.io.*;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileConfigurationFactory implements ConfigurationFactory {

    protected Module module;
    protected String filePath;

    public FileConfigurationFactory(Module module, String filePath) {
        this.module = module;
        this.filePath = filePath;
    }

    @Override
    public Configuration createConfiguration(ConfigurationLoaderStrategy loaderStrategy) {
        final File file = new File("configs/" + module.getName() + "/" + filePath);
        if (!file.exists() && file.getParentFile().mkdirs()) {

        }
        final JarFile jarFile = module.getJarFile();
        final JarEntry entry = jarFile.getJarEntry(filePath);
        if (Objects.nonNull(entry) && !entry.isDirectory())
            return applyLoaderStrategy(jarFile, entry, loaderStrategy);
        return null;
    }

    @Override
    public void saveConfiguration(Configuration configuration, ConfigurationLoaderStrategy loaderStrategy) throws IOException {
        loaderStrategy.saveConfiguration(configuration, new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        });
    }

    private void copyConfigFile() {
        final JarFile jarFile = module.getJarFile();
        final JarEntry entry = jarFile.getJarEntry(filePath);
    }

    private Configuration applyLoaderStrategy(JarFile jarFile, JarEntry entry, ConfigurationLoaderStrategy loaderStrategy) {
        try {
            final InputStream InputStream = jarFile.getInputStream(entry);
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(InputStream);
            final Configuration configuration = loaderStrategy.loadConfiguration(bufferedInputStream);
            bufferedInputStream.close();
            return configuration;
        } catch (IOException e) {
            module.error("Failed to load configuration file: " + filePath);
            e.printStackTrace();
        }
        return null;
    }

}
