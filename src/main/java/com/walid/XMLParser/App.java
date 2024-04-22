/**   
	  the basic api:
	  - getElementByName(name);
	  - getElementByAttribute(name, value);
	  - getChildren(element);
*/
package com.walid.XMLParser;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;



public class App 
{

    public static void main( String[] args ) throws IOException, ParsingException
    {

		if(args.length == 0) {
			System.out.println("please choose a file");
			System.exit(0);
		}
		File file = new File(args[0]);
		FileInputStream fileInputStream = new FileInputStream(file);
		XMLParser xmlParser = new XMLParser(fileInputStream);
		Node version = xmlParser.getElementsByName("project").get(0);
		System.out.println(version.getAttrs().get("xmlns:xsi"));
	
	
    }
}
