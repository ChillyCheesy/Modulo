package com.chillycheesy.modulo.pages.response;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.PageManager;
import com.chillycheesy.modulo.pages.ResourcePage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceResponse implements ResponseHandler {

    private static final int BUFFER_SIZE = 4096;

    @Override
    public boolean response(Page page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (page instanceof final ResourcePage resourcePage) {
            final PageManager pageManager =  ModuloAPI.getPage().getPageManager();
            final Module module = pageManager.getModuleByItem(page);
            if (Objects.nonNull(module)) {
                final String requestPath = request.getServletPath();
                final String resourcePath = resourcePage.getResourcePath(requestPath);
                final BufferedInputStream resource = getResourceFile(module, resourcePath);
                if (Objects.nonNull(resource)) {
                    final BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
                    final String contentType = getContentType(resourcePath, resource);
                    response.setStatus(200);
                    response.setContentType(contentType);
                    copy(resource, outputStream);
                    return true;
                }
            }
        }
        return false;
    }

    private String getContentType(String fileName, InputStream stream) throws IOException {
        final String contentType = URLConnection.guessContentTypeFromStream(stream);
        if (Objects.nonNull(contentType)) {
            return contentType;
        } else {
            return URLConnection.guessContentTypeFromName(fileName);
        }
    }

    private void copy(InputStream in, OutputStream out) throws IOException {
        final byte[] buffer = new byte[BUFFER_SIZE];
        int read;
        while ((read = in.read(buffer)) != -1)
            out.write(buffer, 0, read);
        out.flush();
        out.close();
        in.close();
    }

    private BufferedInputStream getResourceFile(Module module, String path) throws IOException {
        final BufferedInputStream bufferedInputStream = findResource(module, path);
        if (Objects.isNull(bufferedInputStream)) {
            final String newPath = reformatPath(path);
            return !newPath.equals("")
                ? getResourceFile(module, newPath)
                : null;
        }
        return bufferedInputStream;
    }

    private BufferedInputStream findResource(Module module, String path) throws IOException {
        final JarFile jarFile = module.getJarFile();
        final JarEntry entry = jarFile.getJarEntry(path);
        if (Objects.nonNull(entry) && !entry.isDirectory()) {
            final InputStream inputStream = jarFile.getInputStream(entry);
            return new BufferedInputStream(inputStream);
        }
        return null;
    }

    private String reformatPath(String path) {
        if (path.endsWith("index.html"))
            return path.replaceAll("([-a-zA-Z0-9@:%._+~#=]+/index\\.html)$", "");
        if (path.endsWith("/"))
            return path.replaceAll("/$", "");

        final Pattern pattern = Pattern.compile("[-a-zA-Z0-9@:%_+~#=]+\\.[-a-zA-Z0-9@:%_+~#=]+$");
        final Matcher matcher = pattern.matcher(path);
        if (matcher.find()) return "";
        return path + "/index.html";
    }

}
