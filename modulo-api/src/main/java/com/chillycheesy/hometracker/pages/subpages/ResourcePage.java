package com.chillycheesy.hometracker.pages.subpages;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.pages.HttpRequest;
import com.chillycheesy.hometracker.pages.Page;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourcePage extends Page {

    public ResourcePage(HttpRequest requestType, String path, Supplier<String> content) {
        super(requestType, path, content);
    }

    public ResourcePage(String path, Supplier<String> content) {
        super(path, content);
    }

    public ResourcePage(HttpRequest requestType, String path) {
        super(requestType, path);
    }

    public ResourcePage(String path) {
        super(path);
    }

    @Override
    public String getContent() {
        final String resourcePath = super.getContent();
        try {
            final JarFile jarJarFile = ModuloAPI.getPage().getPageManager().getModuleByItem(this).getJarFile();
            final JarEntry jarJarEntry = jarJarFile.getJarEntry(resourcePath);
            return this.getResourceAsString(jarJarFile, jarJarEntry);
        } catch (IOException e) {
            ModuloAPI.getLogger().error(null, e.getMessage());
        }
        return null;
    }

    private String getResourceAsString(JarFile jarJarFile, JarEntry jarJarEntry) throws IOException {
        if (jarJarEntry.isDirectory()) {
            // The resource requested is a directory so we want to find an index.html in it.
            final String jarJarEntryName = jarJarEntry.getName();
            final String newJarJarEntryPath = jarJarEntryName.replaceAll("/$", "") + "/index.html";
            final JarEntry indexJarJarEntry = new JarEntry(newJarJarEntryPath);
            return getResourceAsString(jarJarFile, indexJarJarEntry);
        }
        try (final InputStream inputStream = jarJarFile.getInputStream(jarJarEntry)) {
            final byte[] bytes = IOUtils.toByteArray(inputStream);
            return Base64.getEncoder().encodeToString(bytes);
        }
    }
}
