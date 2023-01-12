package com.chillycheesy.modulo.config;

import com.chillycheesy.modulo.modules.Module;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileConfigurationFactory implements ConfigurationFactory {

    protected Module module;
    protected File configFile;
    protected InputStream sourceInputStream;

    public FileConfigurationFactory(Module module, File configFile, InputStream sourceInputStream) {
        this.module = module;
        this.configFile = configFile;
        this.sourceInputStream = sourceInputStream;
    }

    public FileConfigurationFactory(Module module, String configPath, InputStream sourceInputStream) {
        this(module, new File(String.format("configs/%s/%s", module.getName(), configPath)), sourceInputStream);
    }

    public FileConfigurationFactory(Module module, String configPath, String internalSourcePath) {
        this(module, configPath, FileConfigurationFactory.getInputStreamFromModuleAndPath(module, internalSourcePath));
    }

    public FileConfigurationFactory(Module module, File configFile, String internalSourcePath) {
        this(module, configFile, FileConfigurationFactory.getInputStreamFromModuleAndPath(module, internalSourcePath));
    }

    public FileConfigurationFactory(Module module, String configPath) {
        this(module, configPath, configPath);
    }

    @Override
    public Configuration createConfiguration(ConfigurationLoaderStrategy loaderStrategy) {
        try {
            if (configFile.exists() || deployDefaultConfigFile(configFile))
                return applyLoaderStrategy(configFile, loaderStrategy);
        } catch (IOException e) {
            module.error("Failed to load configuration file: " + configFile.getPath());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveConfiguration(Configuration configuration, ConfigurationLoaderStrategy loaderStrategy) {
        if (configuration.isModified()) {
            try (
                final FileOutputStream fileOutputStream = new FileOutputStream(configFile);
                final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)
            ) {
                loaderStrategy.saveConfiguration(configuration, bufferedOutputStream);
            } catch (IOException e) {
                module.error("Error saving configuration file: " + configFile.getPath());
                e.printStackTrace();
            }
        }
    }

    private boolean deployDefaultConfigFile(File file) throws IOException {
        final File parentFile = file.getAbsoluteFile().getParentFile();
        if (file.exists() && (parentFile.exists() || parentFile.mkdirs()) && sourceInputStream != null)
            return file.createNewFile() && copySourceFile(file);
        return true;
    }

    private boolean copySourceFile(File file) throws IOException {
        try (
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(sourceInputStream);
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)
        ) {
            IOUtils.copy(bufferedInputStream, bufferedOutputStream);
            bufferedOutputStream.flush();
            return true;
        }
    }

    private Configuration applyLoaderStrategy(File file, ConfigurationLoaderStrategy loaderStrategy) {
        if (file.exists()) {
            try (
                final FileInputStream fileInputStream = new FileInputStream(file);
                final BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            ) {
                return loaderStrategy.loadConfiguration(bufferedInputStream);
            } catch (IOException e) {
                module.error("Failed to load configuration file: " + file.getPath());
                e.printStackTrace();
            }
        }
        return null;
    }

    private static InputStream getInputStreamFromModuleAndPath(Module module, String path) {
        try {
            final JarFile jarFile = module.getJarFile();
            final JarEntry entry = Objects.nonNull(jarFile) ? jarFile.getJarEntry(path) : null;
            return Objects.nonNull(entry) && !entry.isDirectory() ? jarFile.getInputStream(entry) : null;
        } catch (IOException e) {
            module.error("Failed to load configuration file: " + path);
            e.printStackTrace();
        }
        return null;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public File getConfigFile() {
        return configFile;
    }

    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    public InputStream getSourceInputStream() {
        return sourceInputStream;
    }

    public void setSourceInputStream(InputStream sourceInputStream) {
        this.sourceInputStream = sourceInputStream;
    }
}
