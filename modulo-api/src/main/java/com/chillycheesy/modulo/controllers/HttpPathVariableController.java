package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;
import com.chillycheesy.modulo.utils.exception.InvalidPathVariableException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller that check if the pathVariable match with the target path.
 * If the path match then return the next controller result.
 * The path variable include some expression.
 * <ul>
 *     <li>
 *         <code>{&lt;key&gt;}</code>: The path variable.
 *         The key is the name of the path variable.
 *         If the path match with the request path, the apply method register the key inside a "path-variable" section with the equivalent value inside the configuration.
 *         <br>
 *         Exemple: <code>/{value}</code> match with <code>/test</code> and register the key <code>path-variable.value</code> with the value <code>test</code>.
 *     </li>
 *     <li>
 *         <code>*</code>: match any path but don't register it inside the configuration.
 *         <br>
 *         Exemple: <code>/my/*</code> match with <code>/my/test</code>.
 *     </li>
 *     <li>
 *         <code>/**</code>: match any additional path but don't register it inside the configuration. <strong>it should be place at the end of the path</strong>
 *         <br>
 *         Exemple: <code>/my/**</code> match with <code>/my/test</code> and <code>/my/test/test2</code> and <code>/my/test/test2/test3</code>.
 *         Register the additional path inside the configuration at the key "additional-path".
 *     </li>
 * </ul>
 *
 * @author ChillyCheesy
 */
public class HttpPathVariableController implements Controller {

    public static final String PATH_VARIABLE_SECTION = "path-variable";

    /**
     * Target path.
     */
    private final String pathVariable;

    /**
     * The next controller.
     */
    private Controller controller;

    /**
     * Constructor.
     *
     * @param pathVariable The target path.
     */
    public HttpPathVariableController(String pathVariable) throws InvalidPathVariableException {
        if (pathVariable == null || pathVariable.isEmpty())
            throw new InvalidPathVariableException("The path variable can't be null or empty");
        final Pattern pattern = Pattern.compile("(?<=/)\\*\\*(?=/)");
        final Matcher matcher = pattern.matcher(pathVariable);
        if (matcher.find() || pathVariable.startsWith("**/"))
            throw new InvalidPathVariableException("The ** path can only be placed at the end.");
        this.pathVariable = pathVariable;
    }

    /**
     * Apply the controller.
     * If the path match with the target pathVariable then Register all find variable inside the configuration and return the next controller result.
     * @param request the http request.
     * @param response the http response.
     * @param configuration the configuration.
     * @return The response.
     */
    @Override
    public Object apply(HttpServletRequest request, HttpServletResponse response, Configuration configuration) throws Exception {
        final String requestPath = request.getRequestURI();
        final List<String> requestPathList = List.of(requestPath.split("/"));
        final List<String> pathVariableSplit = List.of(pathVariable.split("/"));
        if (match(requestPathList, pathVariableSplit, configuration)) {
            return controller.apply(request, response, configuration);
        }
        return null;
    }

    /**
     * Check if the requestPath match with the path variable.
     * @param requestPathList The requestPath.
     * @param pathVariableSplit The pathVariable.
     * @param configuration The configuration.
     * @return True if the two path match.
     */
    private boolean match(List<String> requestPathList, List<String> pathVariableSplit, Configuration configuration) {
        final Iterator<String> requestPathIterator = requestPathList.iterator();
        final Iterator<String> pathVariableIterator = pathVariableSplit.iterator();
        while (requestPathIterator.hasNext() && pathVariableIterator.hasNext()) {
            final String requestPath = requestPathIterator.next();
            final String pathVariable = pathVariableIterator.next();
            if (argMatch(requestPath, pathVariable, configuration)) {
                if (pathVariable.equals("**")) {
                    final StringBuilder additionalPath = new StringBuilder(requestPath);
                    requestPathIterator.forEachRemaining(value -> additionalPath.append("/").append(value));
                    configuration.set("additional-path", additionalPath.toString());
                    return true;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the requestPath match with the path variable.
     * @param requestPath The requestPath.
     * @param pathVariable The pathVariable.
     * @param configuration The configuration.
     * @return True if the two path match.
     */
    private boolean argMatch(String requestPath, String pathVariable, Configuration configuration) {
        if (pathVariable.startsWith("{") && pathVariable.endsWith("}")) {
            final String key = pathVariable.replaceAll("^\\{|}$", "");
            configuration.set(String.format("%s.%s", PATH_VARIABLE_SECTION, key), requestPath);
            return true;
        } else return pathVariable.equals("*") || pathVariable.equals("**") || pathVariable.equals(requestPath);
    }

    /**
     * Set the next controller.
     * @param controller the next controller.
     */
    @Override
    public void setNextStep(Controller controller) {
        this.controller = controller;
    }

}
