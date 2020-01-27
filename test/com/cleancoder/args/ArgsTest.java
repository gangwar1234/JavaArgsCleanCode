package com.cleancoder.args;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Map;

import static com.cleancoder.args.ArgsException.ErrorCode.*;
import static org.junit.Assert.*;



public class ArgsTest {

  public static void main(String[] args) {
      Result result = JUnitCore.runClasses(ArgsTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println(result.wasSuccessful());
  }
  

  @Test
  public void testCreateWithNoSchemaOrArguments() throws Exception {

    Args args = new Args("", new String[0]);
    assertEquals(0, args.nextArgument());
  }


  @Test
  public void testWithNoSchemaButWithOneArgument() throws Exception {
    try {

      new Args("", new String[]{"-x"});
      fail("Args constructor should have thrown exception"+
       " in testWithNoSchemaButWithOneArguments");
    } catch (ArgsException e) {
      assertEquals(UNEXPECTED_ARGUMENT, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }


  @Test
  public void testWithNoSchemaButWithMultipleArguments() throws Exception {
    try {
      new Args("", new String[]{"-x", "-y"});
      fail("Args constructor should have thrown exception"+
           " in testWithNoSchemaButWithMultipleArguments");
    } catch (ArgsException e) {
      assertEquals(UNEXPECTED_ARGUMENT, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }

  }


  @Test
  public void testNonLetterSchema() throws Exception {
    try {
      new Args("*", new String[]{});
      fail("Args constructor should have thrown exception"+
           " in testNonLetterSchema");
    } catch (ArgsException e) {
      assertEquals(INVALID_ARGUMENT_NAME, e.getErrorCode());
      assertEquals('*', e.getErrorArgumentId());
    }
  }



  @Test
  public void testInvalidArgumentFormat() throws Exception {
    try {
      new Args("f~", new String[]{});
      fail("Args constructor should have throws exception");
    } catch (ArgsException e) {
      assertEquals(INVALID_ARGUMENT_FORMAT, e.getErrorCode());
      assertEquals('f', e.getErrorArgumentId());
    }
  }


  @Test
  public void testSimpleBooleanPresent() throws Exception {
    Args args = new Args("x", new String[]{"-x"});
    assertEquals(true, args.getBoolean('x'));
    
  }



  @Test
  public void testSimpleStringPresent() throws Exception {
    Args args = new Args("x*", new String[]{"-x", "param"});
    assertEquals("param", args.getString('x'));
  }



  @Test
  public void testMissingStringArgument() throws Exception {
    try {
      new Args("x*", new String[]{"-x"});
      fail("Args constructor should have throws exception");
    } catch (ArgsException e) {
      assertEquals(MISSING_STRING, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }



 @Test
  public void testValidLabel() throws Exception {
    try{
     new Args("x, y#", new String[]{"-xy 5"});
    fail("Args constructor should have throws exception"+
         "in testValidLabel");
    }catch(ArgsException e){
      assertEquals(INVALID_ARGUMENT_FORMAT, e.getErrorCode());
    }
   
  }


  @Test
  public void testInvalidInput() throws Exception {
    try{
     new Args("x, y", new String[]{"-xy"});
    fail("Args constructor should have throws exception"+
         "testInvalidInput");
    }catch(ArgsException e){
      assertEquals(INVALID_ARGUMENT_FORMAT, e.getErrorCode());
    }
    
  }


  
  @Test
  public void testSimpleIntPresent() throws Exception {
    Args args = new Args("x#", new String[]{"-x", "42"});
    assertEquals(42, args.getInt('x'));
    
  }

  
  @Test
  public void testInvalidInteger() throws Exception {
    try {
      new Args("x#", new String[]{"-x", "Forty two"});
      fail("Args constructor should have throws exception");

    } catch (ArgsException e) {
      assertEquals(INVALID_INTEGER, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
      assertEquals("Forty two", e.getErrorParameter());
    }

  }


  @Test
  public void testMissingInteger() throws Exception {
    try {
      new Args("x#", new String[]{"-x"});
      fail("Args constructor should have throws exception");
    } catch (ArgsException e) {
      assertEquals(MISSING_INTEGER, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }


  @Test
  public void testSimpleDoublePresent() throws Exception {
    Args args = new Args("x##", new String[]{"-x", "42.3"});
    assertEquals(42.3, args.getDouble('x'), .001);
  }

  @Test
  public void testInvalidDouble() throws Exception {
    try {
      new Args("x##", new String[]{"-x", "Forty two"});
      fail("Args constructor should have throws exception");
    } catch (ArgsException e) {
      assertEquals(INVALID_DOUBLE, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
      assertEquals("Forty two", e.getErrorParameter());
    }
  }

  @Test
  public void testMissingDouble() throws Exception {
    try {
      new Args("x##", new String[]{"-x"});
      fail("Args constructor should have throws exception");

    } catch (ArgsException e) {
      assertEquals(MISSING_DOUBLE, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }


  @Test
  public void testStringArray() throws Exception {
    Args args = new Args("x[*]", new String[]{"-x", "alpha"});
    String[] result = args.getStringArray('x');
    assertEquals(1, result.length);
    assertEquals("alpha", result[0]);
  }


  @Test
  public void testMissingStringArrayElement() throws Exception {
    try {
      new Args("x[*]", new String[] {"-x"});
      fail("Args constructor should have throws exception");

    } catch (ArgsException e) {
      assertEquals(MISSING_STRING,e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }

  @Test
  public void manyStringArrayElements() throws Exception {
    Args args = new Args("x[*]", new String[]{"-x", "alpha", "-x", "beta"});
    String[] result = args.getStringArray('x');
    assertEquals(2, result.length);
    assertEquals("alpha", result[0]);
    assertEquals("beta", result[1]);
    
  }

  @Test
  public void MapArgument() throws Exception {
    Args args = new Args("f&", new String[] {"-f", "key1:val1,key2:val2"});
    assertTrue(args.has('f'));
    Map<String, String> map = args.getMap('f');
    assertEquals("val1", map.get("key1"));
    assertEquals("val2", map.get("key2"));
  }

  @Test
  
  public void malFormedMapArgument() throws Exception {

    try{
      new Args("f&", new String[] {"-f", "key1:val1,key2"});
      fail("Args constructor should have throws exception");
    } catch(ArgsException e){
      assertEquals(MALFORMED_MAP, e.getErrorCode());
      
    }

  }



   @Test
 
  public void malFormedMapArgumentCheckValue() throws Exception {

    try{
      new Args("f&", new String[] {"-f", ":val1"});
      fail("Args constructor should have throws exception");
    } catch(ArgsException e){
      assertEquals(MALFORMED_MAP, e.getErrorCode());
      
    }

  }


  @Test
  public void oneMapArgument() throws Exception {
    Args args = new Args("f&", new String[] {"-f", "key1:val1"});
    Map<String, String> map = args.getMap('f');
    assertEquals("val1", map.get("key1"));
  }



  @Test
  public void testExtraArguments() throws Exception {
    try{
      new Args("x,y*", new String[]{"-x", "-y", "alpha", "beta"});
      fail("Args constructor should have throws exception");
    } catch(ArgsException e){
      assertEquals(INVALID_ARGUMENT_FORMAT, e.getErrorCode());
      
    }
  }


  @Test
  public void testExtraArgumentsThatLookLikeFlags() throws Exception {
    
   try{
      new Args("x,y", new String[]{"-x", "-y", "alpha", "beta"});
      fail("Args constructor should have throws exception");
    } catch(ArgsException e){
      assertEquals(INVALID_ARGUMENT_FORMAT, e.getErrorCode());
      
    }
    
  }

}

