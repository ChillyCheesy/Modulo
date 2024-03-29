package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;
import org.apache.tika.Tika;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller for serving internal files.
 * It takes the request path and tries to find the file in a target module jar.
 * The target jar location is defined by the result of the next controller.
 * for a full compatibility with the additional path inside the server folder, you should to add the additional-path key inside the configuration.
 * for more information about the additional-path key, see the {@link HttpPathVariableController}.
 *
 * @author ChillyCheesy
 */
public class InternalFileResourceController implements Controller {

    /**
     * The next controller in the chain.
     * It should return the target resource server path in the jarFile.
     */
    private Controller nextController;

    /**
     * The Jar File where the resource server is register.
     */
    private final JarFile jarFile;

    /**
     * Constructor for the InternalFileResourceController.
     * @param jarFile the jar file where the resource server is register.
     */
    public InternalFileResourceController(JarFile jarFile) {
        this.jarFile = jarFile;
    }

    /**
     * Apply the controller.
     * @param request the http request.
     * @param response the http response.
     * @param configuration the configuration.
     * @return The content of the resource file or null if it was undefinable.
     * @throws Exception If an error occurs.
     */
    @Override
    public Object apply(HttpServletRequest request, HttpServletResponse response, Configuration configuration) throws Exception {
        final String additionalPath = configuration.getString("additional-path");
        if (Objects.nonNull(nextController)) {
            final Object result = nextController.apply(request, response, configuration);
            if (Objects.nonNull(result)) {
                final String resultPath = result.toString().replaceAll("/$", "");
                final JarEntry entryContent = getInternalFileContent(resultPath, additionalPath);
                if (Objects.nonNull(entryContent)) {
                    final String contentType = getContentType(entryContent.getName(), jarFile.getInputStream(entryContent));
                    final String content = readEntry(entryContent);
                    if (Objects.nonNull(contentType))
                        response.setContentType(contentType);
                    configuration.set("base64", true);
                    return content;
                }
            }
        }
        return null;
    }

    /**
     * Guess the mime type of the returned file.
     * @param fileName the file name.
     * @param stream the file stream.
     * @return the mime type.
     * @throws IOException if an error occurs.
     */
    private String getContentType(String fileName, InputStream stream) throws IOException {
        final Tika tika = new Tika();
        return tika.detect(stream, fileName);
    }

    /**
     * Read the content of the entry.
     * @param resourceFolder the resource folder.
     * @param additionalPath the additional path.
     * @return the entry content.
     */
    private JarEntry getInternalFileContent(String resourceFolder, String additionalPath) {
        final String internalFilePath = String.format("%s/%s", resourceFolder, additionalPath.replaceAll("^/", ""));
        final JarEntry jarEntry = jarFile.getJarEntry(internalFilePath);
        if (Objects.nonNull(jarEntry) && !jarEntry.isDirectory())
            return jarEntry;
        final String newPath = reformatPath(additionalPath);
        return !newPath.equals("") ? getInternalFileContent(resourceFolder, newPath) : null;
    }

    /**
     * If the path is not valid, it will try to reformat it.
     * @param path the reformat path.
     * @return the new path.
     */
    private String reformatPath(String path) {
        if (path.endsWith("index.html"))
            return path.replaceAll("([-a-zA-Z0-9@:%._+~#=]+/index\\.html)$", "index.html");
        if (path.endsWith("/"))
            return path + "index.html";
        final Pattern pattern = Pattern.compile("[-a-zA-Z0-9@:%_+~#=]+\\.[-a-zA-Z0-9@:%_+~#=]+$");
        final Matcher matcher = pattern.matcher(path);
        if (matcher.find()) return "";
        return path + "/";
    }

    /**
     * Read the content of the entry.
     * @param jarEntry the jar entry.
     * @return the content of the entry.
     * @throws IOException if an error occurs.
     */
    private String readEntry(JarEntry jarEntry) throws IOException {
        try (
            final InputStream inputStream = jarFile.getInputStream(jarEntry);
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bufferedInputStream.readAllBytes());
        ) {
            final byte[] bytesContent = byteArrayInputStream.readAllBytes();
            final Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(bytesContent);
        }
    }

    /**
     * Set the next controller in the chain.
     * @param controller The next controller.
     */
    @Override
    public void setNextStep(Controller controller) {
        this.nextController = controller;
    }

}
