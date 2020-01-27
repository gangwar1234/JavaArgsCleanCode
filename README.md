
### Schema Used :


- char    - Boolean arg.
 - char*   - String arg.
 - char#   - Integer arg.
 - char##  - double arg.
 - char[*] - one element of a string array.
 - char &   - map
Example schema: (f,s*,n#,a##,p[*],g&)
Coresponding command line: "-f -s Bob -n 1 -a 3.2 -p e1 -p e2 -p e3 -g key:value




### Implemented Features :
      
      * Map Implementation in the Schema. 
      * String Array was also not implemented as schema.
      * Polymorphism is implemented during constructor overloading.
      * Datatype such as Integer, Boolean, Float, Double, String, String array
        along with Map.  
      * All properties of Unit test has been Satisfied. Properties are : - 
        F.I.R.S.T 
        F : Fast
        I : Independent
        R : Repeatable
        S : Self validating
        T : Timely
        
      * One assert per test is implemented.
      * Exception handling is done by implementing various new constructors.
      
     
### Install/Update Java

      * sudo add-apt-repository ppa:openjdk-r/ppa
      * sudo apt-get update -q 
      * sudo apt install -y openjdk-11-jdk 


### Execution of main File

      * Clone this repo 
      * install ant by running 'sudo apt-get install ant'
      * then go to the folder where you have cloned this repo
      * run 'ant compile'
      * run 'ant jar'
      * run 'java -cp build/jar/args.jar com.cleancoder.args.ArgsMain'

### Execution of Test
     
      * Run the command given below from the root folder of this repo
      * 'java -cp "lib/junit-4.13.jar:lib/hamcrest-core-1.3.jar:build/jar/args.jar" ./test/com/cleancoder/args/ArgsTest.java
        testCreateWithNoSchemaOrArguments'

          
      
### Characterstics of Clean Code : 

      * There is no redundency in the code.
      * Names of methods and data members are meaningful for methods it is 
       (verb + noun) for data members it is ( noun ).
      * Most functions contains less than 7 lines unless it is necessary to 
        add more.
      * Number of Arguments are less than three only it required otherwise
        tried to do parameter passing with one or two arguments.
      * Vertical alignment of arguments in function definition.
      * If try and catch is used in any function then try keyword is the 
        first word of the function.
      * There is nothing after catch/ finally block.
      * Null is avoided to pass as a parameter.
      * Comparison with NULL is done using optional class.
      * Proper Inden tation of functions.
      * Object Oriented Programming concepts such as data encapsulation and
        abstraction and interfaces.
      * Declared blank default constructor if parameterized is required.   
      * If else blocks are one liners.
      * Comments are required only if it is necessary.
      


### Bugs -
      
      * As per the above Schema, -sa as input should be invalid input but 
        givenfunction was not showing the same. It is first calculating the
        string for -s and a is also part of the schema, so it is also  
        expecting the corresponding value of it.
      
      * test case for if Map is empty was not implemented. 
         
    
          

    
