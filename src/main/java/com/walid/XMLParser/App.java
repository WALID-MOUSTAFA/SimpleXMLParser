/* the basic api:
   - getElementByName(name);
   - getElementByAttribute(name, value);
   - getChildren(element);
*/

package com.walid.XMLParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class App 
{
    @SuppressWarnings("unused")
    public static void main( String[] args ) throws IOException, ParsingException
    {
	InputStream inputStream = App.class.getClassLoader().getResourceAsStream("index.xml") ;
	XMLParser xmlParser = new XMLParser(inputStream);
	Node project = xmlParser.getElementsByName("project").get(0);
	var developedBy = project.getAttrs().get("developedBy");
	var mavenDeps = xmlParser.getElementsByName("dependencies").get(0);
	var deps = xmlParser.getChildren(mavenDeps);
      var groupId = xmlParser.getElementsByName("groupId");
	
    }
}
