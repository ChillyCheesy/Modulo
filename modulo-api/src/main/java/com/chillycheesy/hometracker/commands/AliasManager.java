package com.chillycheesy.hometracker.commands;

import java.util.*;

public class AliasManager {

    private AliasManager parent;
    private final Map<String, String> aliases;

    public AliasManager() {
        this(new HashMap<>());
    }
    public AliasManager(Map<String, String> aliases) {
        this.aliases = aliases;
    }

    public void registerAlias(String alias, String command) {
        aliases.put(alias, command);
    }

    public void registerAlias(String alias, String...command) {
        Arrays.stream(command).forEach(c -> registerAlias(alias, c));
    }

    public String getValue(String alias) {
        final String blockAlias = aliases.get(alias);
        if (blockAlias == null && parent != null) {
            return parent.getValue(alias);
        }
        return blockAlias;
    }

    public boolean isAlias(String alias) {
        return aliases.containsKey(alias);
    }

    public List<String> getAliases() {
        final List<String> parent = new ArrayList<>(this.parent != null ? this.parent.getAliases() : Collections.emptyList());
        parent.addAll(aliases.keySet());
        return parent;
    }

    public AliasManager createChild() {
        AliasManager child = new AliasManager();
        child.parent = this;
        return child;
    }

    public AliasManager getParent() {
        return parent;
    }

    public void clear() {
        aliases.clear();
    }
}
