	package com.cleancoder.args;
	import java.util.*; 

	public class ArgsMain {
		public static void main(String[] args) {
			
			try {
				Args arg = new Args("f,s*,n#,a##,p[*], g& ", args);
				executeApplication(arg);
			}	catch (ArgsException e) {
				System.out.printf("Argument error: %s\n", e.errorMessage());
			}
		}




		private static void executeApplication(Args arg) {
			boolean logging = arg.getBoolean('f');
			int port = arg.getInt('n');
			String directory = arg.getString('s');
			String []details = arg.getStringArray('p'); 
			Map<String,String> map=new HashMap<String,String>();
			map=arg.getMap('g'); 
			System.out.printf("logging is %s, port:%d, directory:%s\n",logging, port, directory);
			if(details.length==0)System.out.printf("");
			else
			{
				System.out.printf("Details :  "); 
				for(int i=0;i<details.length;i++)
					System.out.printf("%s ",details[i]);
			}

			System.out.printf("Map :- ");
			for(Map.Entry m:map.entrySet()){  
				System.out.println(m.getKey()+" : "+m.getValue());  
			}  

		}



	}