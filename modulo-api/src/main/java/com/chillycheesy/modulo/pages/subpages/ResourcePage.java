package com.chillycheesy.modulo.pages.subpages;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.PageResponse;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourcePage extends Page {

    public ResourcePage(HttpRequest requestType, String path, PageResponse content) {
        super(requestType, path, content);
    }

    public ResourcePage(HttpRequest requestType, String path, String content) {
        super(requestType, path, content);
    }

    public ResourcePage(String path, String content) {
        super(path, content);
    }

    public ResourcePage(String path, PageResponse content) {
        super(path, content);
    }

    public ResourcePage(HttpRequest requestType, String path) {
        super(requestType, path);
    }

    public ResourcePage(String path) {
        super(path);
    }

    @Override
    public String getContent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String uri = request.getRequestURI().substring(super.getFullPath().length() + 1);
        final String resourcePath = super.getContent(request, response, false) + uri;
        System.out.println("Resource path: " + resourcePath + " uri: " + uri + " path: " + super.getFullPath());
        try {
            final JarFile jarJarFile = ModuloAPI.getPage().getPageManager().getModuleByItem(this).getJarFile();
            final JarEntry jarJarEntry = jarJarFile.getJarEntry(resourcePath);
            return this.getResourceAsString(jarJarFile, jarJarEntry, response);
        } catch (IOException e) {
            ModuloAPI.getLogger().error(null, e.getMessage());
        }
        return "";
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
             final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            IOUtils.copy(bufferedInputStream, response.getOutputStream());
            return "";
        }
    }
}