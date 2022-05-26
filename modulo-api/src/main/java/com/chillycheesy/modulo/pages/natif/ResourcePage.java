package com.chillycheesy.modulo.pages.natif;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.PageManager;
import com.chillycheesy.modulo.pages.PageResponse;
import com.chillycheesy.modulo.utils.exception.No404SubPageException;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * The ResourcePage represents a page that is a resource of the application.
 * It can be a file or a directory, inside the jar resource.
 */
public class ResourcePage extends Page {

    public ResourcePage(HttpRequestType requestType, String path, PageResponse content) {
        super(requestType, path, content);
    }

    public ResourcePage(HttpRequestType requestType, String path, String content) {
        super(requestType, path, content);
    }

    public ResourcePage(String path, String content) {
        super(path, content);
    }

    public ResourcePage(String path, PageResponse content) {
        super(path, content);
    }

    public ResourcePage(HttpRequestType requestType, String path) {
        super(requestType, path);
    }

    public ResourcePage(String path) {
        super(path);
    }

    @Override
    public String applyRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final PageManager pageManager = ModuloAPI.getPage().getPageManager();
        final String uri = request.getRequestURI().substring(super.getFullPath().length() + 1);
        final String resourcePath = super.applyRequest(request, response, false).replaceAll("[^/]$", "$0/") + uri;
        try {
            final JarFile jarJarFile = ModuloAPI.getPage().getPageManager().getModuleByItem(this).getJarFile();
            final JarEntry jarJarEntry = jarJarFile.getJarEntry(resourcePath);
            return Objects.isNull(jarJarEntry)
                    ? pageManager.redirect(HttpRequestType.ANY, "*").applyRequest(request, response)
                    : this.getResourceAsString(jarJarFile, jarJarEntry, response);
        } catch (IOException | No404SubPageException e) {
            final Module module = ModuloAPI.getPage().getPageManager().getModuleByItem(this);
            ModuloAPI.getLogger().error(module, e.getMessage());
        }
        return null;
    }

    private String getResourceAsString(JarFile jarJarFile, JarEntry jarJarEntry, HttpServletResponse response) throws IOException {
        if (jarJarEntry.isDirectory()) {
            // The resource requested is a directory so we want to find an index.html in it.
            final String jarJarEntryName = jarJarEntry.getName();
            final String newJarJarEntryPath = jarJarEntryName.replaceAll("/$", "") + "/index.html";
            final JarEntry indexJarJarEntry = new JarEntry(newJarJarEntryPath);
            return getResourceAsString(jarJarFile, indexJarJarEntry, response);
        }
        try (final InputStream inputStream = jarJarFile.getInputStream(jarJarEntry);
             final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
             final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream())) {
            IOUtils.copy(bufferedInputStream, bufferedOutputStream);
            return jarJarEntry.getRealName();
        }
    }
}
