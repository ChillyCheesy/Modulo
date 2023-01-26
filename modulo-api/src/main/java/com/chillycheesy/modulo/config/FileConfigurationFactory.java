package com.chillycheesy.modulo.config;

import com.chillycheesy.modulo.modules.Module;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Create a {@link Configuration} from a Module and a source file.
 *
 * @author chillycheesy
 */
public class FileConfigurationFactory implements ConfigurationFactory {

    /**
     * Source module.
     */
    protected Module module;
    /**
     * The destination config file.
     */
    protected File configFile;

    /**
     * The source file input stream.
     */
    protected InputStream sourceInputStream;

    /**
     * Create a new FileConfigurationFactory.
     *
     * @param module The source module.
     * @param configFile The destination config file.
     * @param sourceInputStream The source file input stream.
     */
    public FileConfigurationFactory(Module module, File configFile, InputStream sourceInputStream) {
        this.module = module;
        this.configFile = configFile;
        this.sourceInputStream = sourceInputStream;
    }

    /**
     * Create a new FileConfigurationFactory.
     * @param module The source module.
     * @param configPath The destination config file path. The path is relative to "./configs/&lt;module-name&gt;/".
     * @param sourceInputStream The source file input stream.
     */
    public FileConfigurationFactory(Module module, String configPath, InputStream sourceInputStream) {
        this(module, new File(String.format("configs/%s/%s", module.getName(), configPath)), sourceInputStream);
    }

    /**
     * Create a new FileConfigurationFactory.
     * @param module The source module.
     * @param configPath The destination config file path. The path is relative to "./configs/&lt;module-name&gt;/".
     * @param internalSourcePath The source file path in the module jar file.
     */
    public FileConfigurationFactory(Module module, String configPath, String internalSourcePath) {
        this(module, configPath, FileConfigurationFactory.getInputStreamFromModuleAndPath(module, internalSourcePath));
    }

    /**
     * Create a new FileConfigurationFactory.
     * @param module The source module.
     * @param configFile The destination config file.
     * @param internalSourcePath The source file path in the module jar file.
     */
    public FileConfigurationFactory(Module module, File configFile, String internalSourcePath) {
        this(module, configFile, FileConfigurationFactory.getInputStreamFromModuleAndPath(module, internalSourcePath));
    }

    /**
     * Create a new FileConfigurationFactory.
     * The config file will be created in "./configs/&lt;module-name&gt;/".
     * The source file will be read from the module jar file at the same path as the configPath.
     * @param module The source module.
     * @param configPath The destination config file path. The path is relative to "./configs/&lt;module-name&gt;/".
     */
    public FileConfigurationFactory(Module module, String configPath) {
        this(module, configPath, configPath);
    }

    /**
     * Create a new Configuration.
     * @param loaderStrategy The loader strategy.
     * @return The new Configuration.
     */
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

    /**
     * Save a configuration as a file.
     * @param configuration The configuration to save.
     * @param loaderStrategy The loader strategy.
     */
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

    /**
     * Deploy the default config file from the module jar file.
     * If the target config is not present in the server folder, it will be copied from the module jar file.
     *
     * @param file The target config file.
     * @return True if the file was deployed, false otherwise.
     * @throws IOException If an error occurs while deploying the file.
     */
    private boolean deployDefaultConfigFile(File file) throws IOException {
        final File parentFile = file.getAbsoluteFile().getParentFile();
        if ((parentFile.exists() || parentFile.mkdirs()) && sourceInputStream != null)
            return file.createNewFile() && copySourceFile(file);
        return true;
    }

    /**
     * Copy the source file to the target file.
     * @param file The target file.
     * @return True if the file was copied, false otherwise.
     * @throws IOException If an error occurs while copying the file.
     */
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

    /**
     * Apply the loader strategy to the config file.
     * @param file The config file.
     * @param loaderStrategy The loader strategy.
     * @return The loaded configuration.
     */
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

    /**
     * Get an input stream from a module and a path.
     * @param module The module.
     * @param path The path.
     * @return The input stream.
     */
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

    /**
     * Get the config file.
     * @return The config file.
     */
    public Module getModule() {
        return module;
    }

    /**
     * Get the config file.
     * @param module The module.
     */
    public void setModule(Module module) {
        this.module = module;
    }

    /**
     * Get the config file.
     * @return The config file.
     */
    public File getConfigFile() {
        return configFile;
    }

    /**
     * Set the config file.
     * @param configFile The config file.
     */
    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    /**
     * Get the source input stream.
     * @return The source input stream.
     */
    public InputStream getSourceInputStream() {
        return sourceInputStream;
    }

    /**
     * Set the source input stream.
     * @param sourceInputStream The source input stream.
     */
    public void setSourceInputStream(InputStream sourceInputStream) {
        this.sourceInputStream = sourceInputStream;
    }
}
