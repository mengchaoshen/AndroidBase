package com.smc.androidbase.args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/10/16
 * @description
 */

public class Args {

    public static void main(String[] args) {
        try {
            String[] as  = {"-s", "abc", "-l", "false"};
            Args arg = new Args("l,s*", as);
            String s = arg.getString('s');
            System.out.println(s);
            boolean l = arg.getBoolean('l');
            System.out.println(l);
        } catch (ArgsException e) {
            e.printStackTrace();
        }
    }


    private Map<Character, ArgumentMarshaler> marshalers;
    private Set<Character> argsFound;
    private ListIterator<String> currentArgument;

    public Args(String schema, String[] args) throws ArgsException {
        marshalers = new HashMap<>();
        argsFound = new HashSet();

        parseSchema(schema);
        parseArgumentStrings(Arrays.asList(args));
    }

    private void parseSchema(String schema) throws ArgsException {
        for (String element : schema.split(",")) {
            if (element.length() > 0) {
                parseSchemeElement(element.trim());
            }
        }
    }

    private void parseSchemeElement(String element) throws ArgsException {
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        validateSchemaElementId(elementId);
        if (elementTail.length() == 0) {
            marshalers.put(elementId, new BooleanArgumentMarshaler());
        } else if (elementTail.equals("*")) {
            marshalers.put(elementId, new StringArgumentMarshaler());
        } else {
            throw new ArgsException();
        }

    }

    private void validateSchemaElementId(char elementId) throws ArgsException {
        //elementId 必须是 字母
        if (!Character.isLetter(elementId)) {
            throw new ArgsException();
        }
    }

    private void parseArgumentStrings(List<String> argsList) throws ArgsException {
        for (currentArgument = argsList.listIterator(); currentArgument.hasNext(); ) {
            String argString = currentArgument.next();
            if (argString.startsWith("-")) {
                parseArgumentCharacters(argString.substring(1));
            } else {
                currentArgument.previous();
                break;
            }
        }
    }

    private void parseArgumentCharacters(String argChars) throws ArgsException {
        for (int i = 0; i < argChars.length(); i++) {
            parseArgumentCharacter(argChars.charAt(i));
        }
    }

    private void parseArgumentCharacter(char argChar) throws ArgsException {
        ArgumentMarshaler m = marshalers.get(argChar);
        if (m == null) {
            throw new ArgsException();
        } else {
            argsFound.add(argChar);
            m.set(currentArgument);
        }
    }

    public boolean getBoolean(char arg) {
        return BooleanArgumentMarshaler.getValue(marshalers.get(arg));
    }

    public String getString(char arg) {
        return StringArgumentMarshaler.getValue(marshalers.get(arg));
    }
}
