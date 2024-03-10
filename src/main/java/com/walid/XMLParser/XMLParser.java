package com.walid.XMLParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public class XMLParser {
    private String document;
    private List<Node> nodes;
    
    public XMLParser(InputStream inputStream) throws ParsingException, IOException{
	String line;
	StringBuilder stringBuilder = new StringBuilder();
	BufferedReader bufferReader = new BufferedReader
	    (new InputStreamReader(inputStream));
	
	while ((line = bufferReader.readLine() ) != null) {
	    stringBuilder.append(line);
	}
	this.document = stringBuilder.toString();
	List<Token> tokens = new Tokenizer(this.document).getTokens();
	Parser parser = new Parser(tokens);
	this.nodes =  parser.getNodes();
	System.out.println("finished parsing");
    }



    public List<Node> getElementsByName(String name) {
	List<Node> result = this.nodes.stream().filter(e-> {
		return e.getTag().equals(name);
	}).toList();
	return result;
    }

    public List<Node> getElementsByAttribute(String attr, String value) {
	List<Node> result = this.nodes.stream()
	    .filter(e ->  e.getAttrs().keySet().contains(attr))
	    .toList()
	    .stream()
	    .filter(e -> e.getAttrs().values().contains(value))
	    .toList();
	return result;
    }

    public List<Node> getChildren(Node node) {
	List<Node> result = nodes
	    .stream()
	    .filter(e -> e.getParent() == node).toList();
	return result;
    }
    
    public List<Node> getNodes() {
	return this.nodes;
    }

    

    public String getDocument() {
	return this.document;
    }
	
}
