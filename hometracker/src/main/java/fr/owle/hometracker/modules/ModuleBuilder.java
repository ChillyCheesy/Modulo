package fr.owle.hometracker.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.utils.exception.InvalidModuleConfigurationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class used to build a {@link fr.owle.hometracker.modules.HTModule} from a {@link java.io.File}<br>
 * The file you want to build should be a jar file with a module.yml at the root of the jar.
 * <pre>{@code
 * name: <name of your module>
 * version: <version of your module>
 * authors: [<the persons that works on your module>]
 * dependencies: [<modules you need to load your module>]
 * soft-dependencies: [<modules you can work with without needing it>]
 * main: <the path to you module class> for exemple: (com.dev.module.MyAwesomeModule)
 * }</pre>
 *
 * @author Geoffrey Vaniscotte
 */
public class ModuleBuilder {

    private final File file;
    private final URLClassLoader urlClassLoader;

    private ModuleBuilder(File file, URLClassLoader urlClassLoader) {
        this.urlClassLoader = urlClassLoader;
        this.file = file;
    }

    /**
     * Method you need to call to build a {@link fr.owle.hometracker.modules.HTModule}
     *
     * @param file file you want to build
     * @param urlClassLoader the {@link URLClassLoader} you want to use to load the jar modules
     * @return the built module
     */
    public static HTModule build(File file, URLClassLoader urlClassLoader) {
        final ModuleBuilder builder = new ModuleBuilder(file, urlClassLoader);
        try {
            return builder.build();
        } catch (ClassNotFoundException | IOException | NoSuchMethodException |
                IllegalAccessException | InvocationTargetException | InstantiationException |
                InvalidModuleConfigurationException e) {
            HTAPI.getLogger().error(HTAPI.getHTAPI(), e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method you need to call to build a {@link fr.owle.hometracker.modules.HTModule}.
     * It will use the default {@link URLClassLoader} to load the jar modules, which is the FactoryURLClassLoader
     * @param file file you want to build
     * @return the built module
     */
    public static HTModule build(File file) {
        try {
            return build(file, URLClassLoader.newInstance(new URL[]{file.toURI().toURL()}));
        } catch (MalformedURLException e) {
            HTAPI.getLogger().error(HTAPI.getHTAPI(), e.getMessage());
            return  null;
        }
    }

    /**
     * Reads the yml file in the root of the jar passed in parameter
     * @param file the jar file of your {@link HTModule}
     * @return a {@link HTModuleConfig}
     * @throws IOException if the file can't be access or read
     * @throws InvalidModuleConfigurationException if the configuration doesn't match the rules described on top of the class
     */
    public static HTModuleConfig readYml(File file) throws IOException, InvalidModuleConfigurationException {
        final FileInputStream fileInputStream = new FileInputStream(file);
        return readYml(file, fileInputStream);
    }

    /**
     * Reads the yml file in the root of the jar passed in parameter
     * @param file the jar file of your {@link HTModule}
     * @param inputStream the {@link InputStream} you especially want to use
     * @return a {@link HTModuleConfig}
     * @throws IOException
     * @throws InvalidModuleConfigurationException
     */
    public static HTModuleConfig readYml(File file, InputStream inputStream) throws IOException, InvalidModuleConfigurationException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        final HTModuleConfig config = mapper.readValue(inputStream, HTModuleConfig.class);
        checkField(file, config);
        return config;
    }

    /**
     * Check the field of the yml file.
     * It will return the field that are missing in your yml file.
     *
     * The only fields that must be in the configuration are the following:
     * <ul>
     *     <li>Main class</li>
     *     <li>Authors</li>
     *     <li>Name of your {@link HTModule}</li>
     * </ul>
     *
     * @param file
     * @param config
     * @throws InvalidModuleConfigurationException
     */
    private static void checkField(File file, HTModuleConfig config) throws InvalidModuleConfigurationException {
        String missingField = "";
        if (config.getMain() == null) missingField += "main ";
        if (config.getAuthors() == null) missingField += "authors ";
        if (config.getName() == null) missingField += "name ";
        if (!missingField.equals("")) throw new InvalidModuleConfigurationException(file, missingField.split(" "));
    }


    private HTModule build() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException, InvalidModuleConfigurationException {
        final JarFile jar = new JarFile(file);
        final JarEntry jarEntry = jar.getJarEntry("module.yml");
        final InputStream is = jar.getInputStream(jarEntry);
        final HTModuleConfig config = readYml(file, is);
        final Class<?> moduleClass = Class.forName(config.getMain(), true, urlClassLoader);
        final Object object = moduleClass.getConstructor().newInstance();
        final HTModule module = (HTModule) object;
        module.setConfig(config);
        module.setJarFile(jar);
        return module;
    }

}
