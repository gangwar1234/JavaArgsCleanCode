package com.cleancoder.args;

import java.util.*;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

public class Args {
  private Map<Character, ArgumentMarshaler> marshalers;
  private Set<Character> argsFound;
  private ListIterator<String> currentArgument;

  
  public Args(String schema, String[] args) throws ArgsException {
    marshalers = new HashMap<>();
    argsFound = new HashSet<>();

    parseSchema(schema);
    parseArgumentStrings(Arrays.asList(args));
  }



  private void parseSchema(
                        String schema) throws ArgsException {
    for (String element : schema.split(","))
      if (element.length() > 0)
        parseSchemaElement(element.trim());
  }


  
  private void ParseCheck(
                      char elementId,
                      String elementTail)
                      throws ArgsException{

     if (elementTail.length() == 0){
      marshalers.put(elementId, new BooleanArgumentMarshaler());
    }

    else if (elementTail.equals("*")){
      marshalers.put(elementId, new StringArgumentMarshaler());
    }

    else if (elementTail.equals("#")){
      marshalers.put(elementId, new IntegerArgumentMarshaler());
    }

    else if (elementTail.equals("##")){
      marshalers.put(elementId, new DoubleArgumentMarshaler());
    }

    else if (elementTail.equals("[*]")){
      marshalers.put(elementId, new StringArrayArgumentMarshaler());
    }

    else if (elementTail.equals("&")){
      marshalers.put(elementId, new MapArgumentMarshaler());
    }

    else{
      throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, elementTail);
    }


  }



  private void parseSchemaElement(
            String element) 
            throws ArgsException {
    char elementId = element.charAt(0);
    String elementTail = element.substring(1);
    validateSchemaElementId(elementId);
    ParseCheck(elementId,elementTail); 
      }


  private void validateSchemaElementId(
    char elementId) throws ArgsException {
    if (!Character.isLetter(elementId))
      throw new ArgsException(INVALID_ARGUMENT_NAME, elementId);
  }



  private void parseArgumentStrings(
    List<String> argsList) throws ArgsException {
    for (currentArgument = argsList.listIterator(); currentArgument.hasNext();) {
      String argString = currentArgument.next();
      if (argString.startsWith("-")) {
        parseArgumentCharacters(argString.substring(1));

      } else {
        throw new ArgsException(INVALID_ARGUMENT_FORMAT,argString);
       }
    }
  }




  private void parseArgumentCharacters(
          String argChars) throws ArgsException {
      if(argChars.length()>1)
      throw new ArgsException(INVALID_ARGUMENT_FORMAT,argChars); 
      else
        parseArgumentCharacter(argChars.charAt(0));
  }

  

 

  private void parseArgumentCharacter(
                  char argChar) throws ArgsException {
    ArgumentMarshaler MarchalersObject = marshalers.get(argChar);
    Optional<ArgumentMarshaler> objectFromMap = 
                          Optional.ofNullable(MarchalersObject);
    if (!objectFromMap.isPresent()) {
      throw new ArgsException(UNEXPECTED_ARGUMENT, argChar);
    } else {
      argsFound.add(argChar);
      validateMapObject(MarchalersObject,argChar);
    }
  }




  private void validateMapObject(
    ArgumentMarshaler m, char argChar) throws ArgsException{

    try {
        m.set(currentArgument);
      } catch (ArgsException e) {
        e.setErrorArgumentId(argChar);
        throw e;
      }

  }
  

  
  public boolean has(char arg) {
    return argsFound.contains(arg);
  }

  public int nextArgument() {
    return currentArgument.nextIndex();
  }

  public boolean getBoolean(char arg) {
    return BooleanArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public String getString(char arg) {
    return StringArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public int getInt(char arg) {
    return IntegerArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public double getDouble(char arg) {
    return DoubleArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public String[] getStringArray(char arg) {
    return StringArrayArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public Map<String, String> getMap(char arg) {
    return MapArgumentMarshaler.getValue(marshalers.get(arg));
  }
}