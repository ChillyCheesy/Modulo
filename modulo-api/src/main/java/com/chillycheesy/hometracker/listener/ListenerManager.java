package com.chillycheesy.hometracker.listener;

import com.chillycheesy.hometracker.utils.Manager;

import java.lang.reflect.Method;
import java.util.*;

public class ListenerManager extends Manager<Listener> {

    protected Map<Method, Integer> sortByDescendingValue(Map<Method, Integer> map) {
        final List<Map.Entry<Method, Integer>> sortedEntries = new ArrayList<>(map.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        final Map<Method, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<Method, Integer> entry : sortedEntries)
            result.put(entry.getKey(), entry.getValue());
        return result;
    }

}
