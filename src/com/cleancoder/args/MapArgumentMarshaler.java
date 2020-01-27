	package com.cleancoder.args;

	import java.util.HashMap;
	import java.util.Iterator;
	import java.util.Map;
	import java.util.NoSuchElementException;

	import static com.cleancoder.args.ArgsException.ErrorCode.*;

	public class MapArgumentMarshaler implements 
	ArgumentMarshaler {
		private Map<String, String> map;
		
		public MapArgumentMarshaler(){
		map = new HashMap<>();

	 }

	 
	private void SetMapValues(
		Iterator<String> currentArgument) 
		throws ArgsException{

		String[] mapEntries = currentArgument.next().split(",");
		for (String entry : mapEntries) {
			String[] entryComponents = entry.split(":");
			
			if (entryComponents.length != 2)
				throw new ArgsException(MALFORMED_MAP);

			if(entryComponents[0].equals(""))
				throw new ArgsException(MALFORMED_MAP);
			map.put(entryComponents[0], entryComponents[1]);

		}

	}



	public void set(
		Iterator<String> currentArgument) 
	throws ArgsException{
		try {
			SetMapValues( currentArgument );
		}
		catch (NoSuchElementException e) {
			throw new ArgsException(MISSING_MAP);
		}
		
	}  



	public boolean validate(ArgumentMarshaler am){

		return (  am instanceof MapArgumentMarshaler);

	}


	public static Map<String, String> getValue(ArgumentMarshaler am) {
		if ((new MapArgumentMarshaler()).validate(am))
			return ((MapArgumentMarshaler) am).map;
		else
			return new HashMap<>();
		}
	}  

