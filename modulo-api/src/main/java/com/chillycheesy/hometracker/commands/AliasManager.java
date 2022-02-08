package com.chillycheesy.hometracker.commands;

import java.util.*;

/**
 * This class is used to manage aliases.
 * An alias is a string that can be used to refer to another string.
 * For example, if you have an alias called "home" whit value "Endor". If you type "home"
 * in the command line, it will be replaced with the value "Endor".
 *
 * An alias can be used in the command line in two ways:
 * 1. As a command. For example, "home" will be replaced with "Endor" and the command "home" will be executed.
 * 2. As a parameter. For example, "home" will be replaced with "Endor" and the command "go home" will be executed.
 *
 * The AliasManager class is can have a children.
 * The children are used to manage aliases that are used in the parent.
 * When you create an alias in the parent, it will be accessible in the children.
 * But if you create an alias in the children, it will not be accessible in the parent.
 */
public class AliasManager {

    /**
     * The parent of this AliasManager.
     */
    private AliasManager parent;
    /**
     * The aliases list.
     */
    private final Map<String, String> aliases;

    public AliasManager() {
        this(new HashMap<>());
    }
    public AliasManager(Map<String, String> aliases) {
        this.aliases = aliases;
    }

    /**
     * Register an alias.
     * @param alias The alias name.
     * @param value The value of the alias.
     */
    public void registerAlias(String alias, String value) {
        aliases.put(alias, value);
    }
    /**
     * Register multiple alias.
     * @param alias The alias name.
     * @param values The values of the alias.
     */
    public void registerAlias(String alias, String...values) {
        Arrays.stream(values).forEach(value -> registerAlias(alias, value));
    }

    /**
     * Get the value of an alias.
     * @param alias The alias name.
     * @return The value of the alias.
     */
    public String getValue(String alias) {
        final String blockAlias = aliases.get(alias);
        if (blockAlias == null && parent != null) {
            return parent.getValue(alias);
        }
        return blockAlias;
    }

    /**
     * Check if an alias exists.
     * @param alias The alias name.
     * @return True if the alias exists.
     */
    public boolean isAlias(String alias) {
        return aliases.containsKey(alias);
    }

    /**
     * Get the aliases names list.
     * @return The aliases name list.
     */
    public List<String> getAliases() {
        final List<String> parent = new ArrayList<>(this.parent != null ? this.parent.getAliases() : Collections.emptyList());
        parent.addAll(aliases.keySet());
        return parent;
    }

    /**
     * Create a Child AliasManager with this as the parent.
     * @return The new AliasManager.
     */
    public AliasManager createChild() {
        AliasManager child = new AliasManager();
        child.parent = this;
        return child;
    }

    /**
     * Get the parent of this AliasManager.
     * @return The parent of this AliasManager.
     */
    public AliasManager getParent() {
        return parent;
    }

    /**
     * Clear all aliases.
     */
    public void clear() {
        aliases.clear();
    }
}
