package com.walid.XMLParser;

import java.util.Map;

public class Node {
    private String tag;
    private Map<String, String> attrs; //NOTE: attributes must be string
    private String innerText;
    private Node parent;

    public Node() {}
    
    public Node(Node parent, String tag, String innerText) {
	this.parent = parent;
	this.tag = tag;
	this.innerText = innerText;
    }
	
    public String getTag() {
	return tag;
    }
    public void setTag(String tag) {
	this.tag = tag;
    }
    public String getInnerText() {
	return innerText;
    }
    public void setInnerText(String innerText) {
	this.innerText = innerText;
    }
    public Node getParent() {
	return parent;
    }
    public void setParent(Node parent) {
	this.parent = parent;
    }

    public Map<String, String> getAttrs() {
	return attrs;
    }

    public void setAttrs(Map<String, String> attrs) {
	this.attrs = attrs;
    }
	
    
}
