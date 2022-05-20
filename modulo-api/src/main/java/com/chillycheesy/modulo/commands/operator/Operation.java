package com.chillycheesy.modulo.commands.operator;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.exception.CommandException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An operation wad created when {@link OperatorFinder} finds a valid operator.
 * <p>
 *     The operation is responsible for parsing the operator and determining the
 *     next command flow.
 * </p>
 */
public class Operation {

    /**
     * The operation.
     */
    private OperatorListener listener;
    /**
     * The command flow that the operation is associated with.
     */
    private CommandFlow left, center, right;

    public Operation() { }

    public Operation(CommandFlow left, CommandFlow center, CommandFlow right) {
        this(left, center, right, null);
    }

    public Operation(CommandFlow left, CommandFlow center, CommandFlow right, OperatorListener listener) {
        this.left = left;
        this.center = center;
        this.right = right;
        this.listener = listener;
    }

    /**
     * Executes the operation.
     * @param caller The module that is executing the operation.
     * @return The result command flow.
     * @throws CommandException If the operation fails.
     */
    public CommandFlow apply(Module caller) throws CommandException {
        return listener.onOperate(caller, left, center, right);
    }

    /**
     * Set the command flow in the center of the operation.
     * @param center The command flow in the center of the operation.
     */
    public void setCenter(CommandFlow center) {
        this.center = center;
    }

    /**
     * Set the command flow in the left of the operation.
     * @param left The command flow in the left of the operation.
     */
    public void setLeft(CommandFlow left) {
        this.left = left;
    }

    /**
     * Set the command flow in the right of the operation.
     * @param right The command flow in the right of the operation.
     */
    public void setRight(CommandFlow right) {
        this.right = right;
    }

    /**
     * Set the operation listener.
     * @param listener The operation listener.
     */
    public void setListener(OperatorListener listener) {
        this.listener = listener;
    }

    /**
     * Get the command flow in the center of the operation.
     * @return The command flow in the center of the operation.
     */
    public CommandFlow getCenter() {
        return center;
    }

    /**
     * Get the command flow in the left of the operation.
     * @return The command flow in the left of the operation.
     */
    public CommandFlow getLeft() {
        return left;
    }

    /**
     * Get the command flow in the right of the operation.
     * @return The command flow in the right of the operation.
     */
    public CommandFlow getRight() {
        return right;
    }

    /**
     * Get the operation listener.
     * @return The operation listener.
     */
    public OperatorListener getListener() {
        return listener;
    }

    /**
     * Create an Operation from a CommandFlow. The operator is represented by a regex.
     * When the method find a match it will create an Operation.
     *
     * The left part is before the regex match, the center part is the match, and the right part is after the match.
     *
     * The regex is modified by the method for escaping skip characters.
     * The operator is escape when:
     * <ul>
     *     <li>We found  a '\' before the operator.</li>
     *     <li>The operator is between parentheses.</li>
     *     <li>The operator is between brackets.</li>
     *     <li>The operator is between curly braces.</li>
     * </ul>
     *
     * @param flux The CommandFlow to parse.
     * @param regex The regex to match.
     * @param listener The operation listener.
     * @return The Operation.
     */
    public static Operation buildFormRegex(CommandFlow flux, String regex, OperatorListener listener) {
        final Pattern pattern = Pattern.compile("(?!([(\\[{].*))((?<!\\\\)" + regex + ")(?!(.*[)\\]}]))");
        final Matcher matcher = pattern.matcher(flux.getContent());
        if (matcher.find()) {
            final String content = flux.getContent();
            final int startContent = 0, start = matcher.start(), end = matcher.end(), endContent = content.length();
            final CommandFlow left = new CommandFlow(content.substring(startContent, start).trim(), flux.getAliasManager());
            final CommandFlow center = new CommandFlow(content.substring(start, end).trim(), flux.getAliasManager());
            final CommandFlow right = new CommandFlow(content.substring(end, endContent).trim(), flux.getAliasManager());
            return new Operation(left, center, right, listener);
        }
        return null;
    }
}
