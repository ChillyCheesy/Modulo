package com.chillycheesy.modulo.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The CommandParameter handle for you the application of the option on your command.
 */
public class CommandParameter {

    /**
     * The first argument passed to the command
     */
    private String first;
    /**
     * The rest of the arguments passed to the command
     */
    private String[] args;
    /**
     * The method to apply to the option
     */
    private Map<String,Function<String,String>> optionMethods;

    public CommandParameter(String[] args){
        this.first = args[0];
        this.args = Arrays.copyOfRange(args,1,args.length);
        this.optionMethods = new HashMap<>();
    }

    /**
     * Apply the option method to the argument
     * @return the result of the command
     */
    public String applyParameter(){
        String res = "";
        if(!this.optionMethods.containsKey(this.first)) return this.first;
        else {
            if(this.args.length != 0){
                res = new CommandParameter(this.args).applyParameter();
            }
            res = this.optionMethods.get(this.first).apply(res);
        }
        return res;
    }

    /**
     * @param method is the method to apply to the command
     * @param option is the word that gonna trigger the method
     */
    public void addOptionMethod(Function<String,String> method,String option){
        this.optionMethods.put(option,method);
    }

    /**
     * @param method is the method to apply to the command
     * @param options is the list of word that gonna trigger the method
     */
    public void addOptionMethod(Function<String,String> method,String... options){
        for(String option: options) {
            this.addOptionMethod(method, option);
        }
    }

}
