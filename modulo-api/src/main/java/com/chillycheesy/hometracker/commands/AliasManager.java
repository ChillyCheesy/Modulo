package com.chillycheesy.hometracker.commands;

import java.util.HashMap;
import java.util.Map;

public class AliasManager {

    private AliasManager parent;
    private Map<String, String> aliases;

    public void addAlias(String alias, String command) {
        aliases.put(alias, command);
    }

    public String getValue(String alias) {
        final String blockAlias = aliases.get(alias);
        if (blockAlias == null && parent != null) {
            return parent.getValue(alias);
        }
        return blockAlias;
    }

    public AliasManager createChild() {
        AliasManager child = new AliasManager();
        child.parent = this;
        child.aliases = new HashMap<>(aliases);
        return child;
    }

    public AliasManager getParent() {
        return parent;
    }
}
