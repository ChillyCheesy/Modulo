package com.chillycheesy.modulo.pages.comparator;

import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.TemplatePage;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TemplatePathRequestMatcher implements RequestMatcher {

    @Override
    public boolean compare(HttpServletRequest request, Page page) {
        if (page instanceof final TemplatePage templatePage) {
            final String requestPath = request.getRequestURI();
            final String pagePath = templatePage.getPath();
            final List<String> keys = this.findKeys(pagePath);
            final Map<String, String> templateArgs = this.findValues(pagePath, requestPath, keys);
            if (this.pageMatch(pagePath, requestPath)) {
                templatePage.saveTemplateVariable(templateArgs);
                return true;
            }
        }
        return false;
    }

    private List<String> findKeys(String path) {
        final Pattern pattern = Pattern.compile("(?<=/\\{)[-a-zA-Z0-9@:%._+~#=]+(?=})");
        final Matcher matcher = pattern.matcher(path);
        return matcher.results().map(MatchResult::group).collect(Collectors.toList());
    }

    private Map<String, String> findValues(String pagePath, String requestPath, List<String> keys) {
        final Map<String, String> templateArgs = new HashMap<>();
        final String pathMatch = pagePath
            .replaceAll("\\{\\w+}", "([-a-zA-Z0-9@:%._+~#=]+)")
            .replaceAll("(?<=/)\\*(?!\\*)", "[-a-zA-Z0-9@:%._+~#=]+")
            .replaceAll("(?<=/)\\*\\*", "[-a-zA-Z0-9@:%._+~#=]+(?:/[-a-zA-Z0-9@:%._+~#=]+)*")
            .replaceAll("(/\\?\\*\\*)$", "[-a-zA-Z0-9@:%._+~#=/]*");
        final Pattern pattern = Pattern.compile(pathMatch);
        final Matcher matcher = pattern.matcher(requestPath);
        if (matcher.matches()) {
            for (int i = 0; i < keys.size(); i++) {
                final String key = keys.get(i);
                templateArgs.put(key, matcher.group(i + 1));
            }
        }
        return templateArgs;
    }
    private boolean pageMatch(String path, String request) {
        final String patternFormat = path
            .replaceAll("(?<=/)((\\{[-a-zA-Z0-9@:%._+~#=]+})|\\*(?!\\*))", "[-a-zA-Z0-9@:%._+~#=]+")
            .replaceAll("(?<=/)\\*\\*", "[-a-zA-Z0-9@:%._+~#=]+(?:\\\\/[-a-zA-Z0-9@:%._+~#=]+)*")
            .replaceAll("(/\\?\\*\\*)$", "[-a-zA-Z0-9@:%._+~#=/]*");
        final Pattern pattern = Pattern.compile(patternFormat);
        final Matcher matcher = pattern.matcher(request);
        return matcher.matches();
    }

}
