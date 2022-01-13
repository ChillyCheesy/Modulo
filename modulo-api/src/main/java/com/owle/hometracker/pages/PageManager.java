package com.owle.hometracker.pages;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.owle.hometracker.modules.Module;
import com.owle.hometracker.utils.exception.PageMissingIndexAnnotationException;
import com.owle.hometracker.utils.exception.PageNotFoundException;
import com.owle.hometracker.utils.exception.ResourceNotExistingException;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class that manage the different {@link Page} of the different {@link Module}.
 * It allows you to add, remove, get any {@link Page} from any {@link Module}.
 *
 * @author Geoffrey Vaniscotte
 */
@Deprecated
public class PageManager {

    private final Map<Module, List<Page>> pages;

    @Deprecated
    public PageManager() {
        this.pages = new HashMap<>();
    }

    /**
     * Checks if the module contain any page.
     * @param module you want to check.
     * @return true if the module contain at least one {@link Page}.
     */
    @Deprecated
    public boolean doesModuleHavePages(Module module) {
        return pages.containsKey(module);
    }

    /**
     * Get the list of {@link Page} from a {@link Module}.
     * @param module The module you want to get the pages of.
     * @return A List of {@link Page}.
     */
    @Deprecated
    public List<Page> getModulePages(Module module) {
        return pages.get(module) == null ? new ArrayList<>() : pages.get(module);
    }

    /**
     * Get the {@link Page} object from a {@link Module} and the name ({@link Index}) of the page.
     * @param module The module you want to get the page of.
     * @param pageIndex The name ({@link Index}) of the page you want to get.
     * @return The {@link Page} object.
     * @throws PageMissingIndexAnnotationException
     * @throws PageNotFoundException
     */
    @Deprecated
    public Page getPage(Module module, String pageIndex) throws PageMissingIndexAnnotationException, PageNotFoundException {
       final List<Page> modulePages = getModulePages(module);
       for (Page p : modulePages) {
           final String currentPageIndex = getIndexByPage(p);
           if (pageIndex.equals(currentPageIndex)) {
               return p;
           }
       }
       throw new PageNotFoundException(module, pageIndex);
    }

    /**
     * Method to call to submit page(s) of any {@link Module}.
     * @param module The module that you want to add of update the page(s) of.
     * @param pages The page(s) you want to submit.
     * @throws PageMissingIndexAnnotationException
     */
    @Deprecated
    public void submitPages(Module module, Page...pages) throws PageMissingIndexAnnotationException, PageNotFoundException {
        for (Page page : pages)
            if (!pageExist(module, page)) {
                if (!doesModuleHavePages(module))
                    this.pages.put(module, new ArrayList<>());
                this.pages.get(module).add(page);
            } else
                overwrite(module, page);
    }

    /**
     * Remove the page from a {@link Module}.
     * @param module The module you want to delete your {@link Page} of.
     * @param page The {@link Page} you want to remove.
     */
    @Deprecated
    public void removePage(Module module, Page page) {
        pages.get(module).remove(page);
    }

    /**
     * Clears all the pages of a {@link Module}
     * @param module the module which you want to delete every {@link Page}
     */
    @Deprecated
    public void removeAllPages(Module module) {
        pages.remove(module);
    }

    /**
     * @param page the page your trying to get the index.
     * @return the index of the page.
     * @throws PageMissingIndexAnnotationException if the page is missing the annotation.
     */
    @Deprecated
    public String getIndexByPage(Page page) throws PageMissingIndexAnnotationException {
        final Index index = page.getClass().getDeclaredAnnotation(Index.class);
        if (index == null) throw new PageMissingIndexAnnotationException();
        return index.value();
    }

    /**
     * Check if a {@link Module} contain a specific {@link Page}
     * @param module the {@link Module} you want to check
     * @param page the page you're trying to find
     * @return true if the module has submitted the page passed in parameter
     */
    @Deprecated
    public boolean pageExist(Module module, Page page) {
        for (Page p : getModulePages(module))
            if (page.equals(p)) return true;
        return false;
    }

    /**
     * Get the html content of a {@link Page}
     * @param module the module of the page
     * @param page the page you want to get the content of
     * @param path the url path you're trying to get
     * @param param the parameters of the path
     * @param body the body request of the http request
     * @param annotation the type of request annotation you want to get
     * @return the content of the page
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws ResourceNotExistingException
     */
    @Deprecated
    public String getPagePathContent(Module module, Page page, String path, String param, String body, Class<? extends Annotation> annotation) throws IOException, InvocationTargetException, IllegalAccessException, ResourceNotExistingException {
        final Map<Method, String> methods = getSortedMethod(page.getClass().getMethods(), annotation);
        for (Method method : methods.keySet()) {
            final String methodPath = methods.get(method);
            if (pathMatch(path, methodPath)) {
                final Object content = callMethodAndReturnContent(page, methodPath, path, param, body, method);
                if (method.isAnnotationPresent(Resource.class)) {
                    final char lastChar = path.length() > 0 ? path.charAt(path.length() - 1) : '/';
                    final int length = path.length();
                    path = lastChar == '/' ? length > 1 ? path.substring(0, length - 1) : "" : path;
                    return readResourcesContent(module, path, content.toString());
                }
                final ObjectMapper mapper = new ObjectMapper(new JsonFactory());
                return Base64.getEncoder().encodeToString((isPrimitiveContent(content) ? content.toString() : mapper.writeValueAsString(content)).getBytes());
            }
        }
        throw new ResourceNotExistingException("index.html", path);
    }

    @Deprecated
    public Map<Module, List<Page>> getPagesCopy() {
        return new HashMap<>(pages);
    }

    private boolean isPrimitiveContent(Object content) {
        return content instanceof Integer ||
                content instanceof Double ||
                content instanceof Float ||
                content instanceof Long ||
                content instanceof Byte ||
                content instanceof Boolean ||
                content instanceof Character ||
                content instanceof String;
    }

    private Map<Method, String> getSortedMethod(Method[] methods, Class<? extends Annotation> annotation) {
        final Map<Method, String> methodList = new HashMap<>();
        for (final Method method : methods) {
            final Annotation methodAnnotation = method.getDeclaredAnnotation(annotation);
            if (methodAnnotation != null) {
                final String methodPath = getAnnotationPath(methodAnnotation);
                methodList.put(method, methodPath);
            }
        }
        return sortByAscendingValue(methodList);
    }

    private Map<Method, String> sortByAscendingValue(Map<Method, String> map) {
        final List<Map.Entry<Method, String>> sortedEntries = new ArrayList<>(map.entrySet());
        sortedEntries.sort(Comparator.comparing(e -> -1 * e.getValue().length()));
        final Map<Method, String> result = new LinkedHashMap<>();
        for (Map.Entry<Method, String> entry : sortedEntries) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private String readResourcesContent(Module module, String path, String content) throws ResourceNotExistingException, IOException {
        if (path.equals("/")) return readResourcesContent(module, "/index.html", content);
        final JarFile jar = module.getJarFile();
        final JarEntry jarEntry = jar.getJarEntry(content + path);
        if (jarEntry == null) {
            if (path.equals("/index.html"))
                throw new ResourceNotExistingException(content, path);
            final String[] allPaths = path.split("/");
            final String last = allPaths[allPaths.length - 2];
            final String newPath = path.substring(0, path.length() - last.length() - allPaths[allPaths.length - 1].length());
            return readResourcesContent(module, newPath, content);
        }
        return jarEntry.isDirectory() ? readResourcesContent(module, path + "/", content) : read(jar, jarEntry);

    }

    private Object callMethodAndReturnContent(Page page, String methodPath, String path, String param, String body, Method method) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {
        if (method.getParameterCount() > 0) {
            final Object[] response = new Object[method.getParameterCount()];
            int i = 0;
            for (Parameter parameter : method.getParameters()) {
                response[i++] = ParameterParser.parse(parameter, methodPath, path, param, body);
            }
            return method.invoke(page, response);
        }
        return method.invoke(page);
    }

    private String read(JarFile jarJarBin, JarEntry jarJarEntry) throws IOException {
        final InputStream inputStream = jarJarBin.getInputStream(jarJarEntry);
        final byte[] bytes = IOUtils.toByteArray(inputStream);
        return Base64.getEncoder().encodeToString(bytes);
    }

    private void overwrite(Module module, Page page) throws PageMissingIndexAnnotationException, PageNotFoundException {
        removePage(module, getPage(module, getIndexByPage(page)));
        submitPages(module, page);
    }

    private String getAnnotationPath(Annotation annotation) {
        if (annotation instanceof GetRequest) {
            return ((GetRequest) annotation).value();
        } else if (annotation instanceof PostRequest) {
            return ((PostRequest) annotation).value();
        } else if (annotation instanceof PutRequest) {
            return ((PutRequest) annotation).value();
        } else if (annotation instanceof DeleteRequest) {
            return ((DeleteRequest) annotation).value();
        } else {
            return "";
        }
    }

    private boolean pathMatch(String path, String methodPath) {
        final String[] pathParts = path.split("/");
        final String[] methodPathParts = methodPath.split("/");
        final int size = Math.max(pathParts.length, methodPathParts.length);
        boolean match = path.equals(methodPath);
        if (!match) {
            final StringBuilder pathBuilder = new StringBuilder(), methodPathBuilder = new StringBuilder();
            for (int i = 1; i < size; i++) {
                final String url = i < pathParts.length ? pathParts[i] : "";
                final String method = i < methodPathParts.length ?
                        !methodPathParts[i].matches("^\\{.+}$") ? methodPathParts[i] : url
                        : "";
                if (url.equals(method) && url.equals("")) return false;
                methodPathBuilder.append(url);
                pathBuilder.append(method);
            }
            match = pathBuilder.toString().equals(methodPathBuilder.toString());
        }
        if (!match && !path.endsWith("/")) {
            return pathMatch(path + "/", methodPath);
        } else if (!match && !path.equals("/")) {
            final String[] allPaths = path.split("/");
            final String last = allPaths[allPaths.length - 1];
            final String newPath = allPaths.length > 2 ? path.substring(0, path.length() - last.length() - 1) : "";
            return pathMatch(newPath, methodPath);
        }
        return match;
    }

}