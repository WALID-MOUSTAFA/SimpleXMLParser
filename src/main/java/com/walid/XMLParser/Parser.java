//TODO: do comments;

package com.walid.XMLParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Parser {
    private List<Token> tokens;
    private int index;
    private List<Node> nodes;

    
    public Parser(List<Token> tokens) throws ParsingException {
		this.tokens = tokens;
		this.nodes = new ArrayList<Node>();
		this.parse();
    }
    
    private void parse() throws ParsingException {
		Stack<Node> tags = new Stack<>();
		while (!this.isEOF()) {
			if (tagStart()) {
				String tagName = getCurrentTagName();
				//String innerText = getInnerText();
				Node parent = tags.isEmpty() ? null : tags.peek();
				Node node = new Node(parent, tagName, null);
				index++;
				parseAttrs(node);
				if(current().getType() != TokenType.SLASH){
					node.setInnerText(parseInnerText());
				}
				tags.push(node);
			} else if (tagEnd()) {
				if(!tags.isEmpty()) {
					this.nodes.add(tags.pop());
				}
			} else if (isComment()) {
				passComment();
			}else {
				index++;
			}
		}
    }


    private void parseAttrs(Node node) throws ParsingException {
	
		Map<String, String> attrs = new HashMap<>();
		passWhiteSpace();
		while (current().getType() != TokenType.CLOSINGANGLEBRACKET
			   && current().getType() != TokenType.SLASH ) {
			String attrName;
			String attrValue;
			passWhiteSpace();
			expectCurrent(TokenType.ALPHANUMURIC, "error in attrs");
			attrName = this.current().getValue();
			index++;
			passWhiteSpace();
			expectCurrent(TokenType.EQIAL, "attr");
			index++;
			passWhiteSpace();
			expectCurrent(TokenType.DOUBLE_QUOTE, "error in attrs");
			index++;
			int stringBegins = index;
			while (current().getType() != TokenType.DOUBLE_QUOTE) {
				index++;
			}
			attrValue = String.join("", tokens.subList(stringBegins, index).stream().map(e-> e.getValue()).toList());
			passWhiteSpace();
			expectCurrent(TokenType.DOUBLE_QUOTE, "error in attrs");
			index++;
			attrs.put(attrName, attrValue);
			passWhiteSpace();
		}
		node.setAttrs(attrs);
		//	index++;
    }

    public void expectCurrent(TokenType type, String msg) throws ParsingException {
		if (current().getType() != type) {
			throwParsingException(msg);
		}
    }
	
    public void throwParsingException(String msg) throws ParsingException {
		throw new ParsingException(msg);
    }

    public String parseInnerText() {
		int cur = index;
		String innerText;
		while (current().getType() != TokenType.OPENINGANGLEBRACKET) {
			index++;
		}
		innerText = String.join("", tokens.subList(cur, index).stream().map(e-> e.getValue()).toList());
		return innerText;
    }
	
    private String getCurrentTagName() throws ParsingException {
		
		Token tagName = this.tokens.get(index + 1);
		if (tagName.getType() != TokenType.ALPHANUMURIC) {
			throw new ParsingException
				("expected alphanumeric value that represents tag name");
		} else {
			index++;
			return tagName.getValue();
		}

    }

    private boolean tagStart() {
		return current().getType() == TokenType.OPENINGANGLEBRACKET
			&& this.peek().getType() != TokenType.SLASH
			&& this.peek().getType() != TokenType.EXP_MARK //evade comment
			&& this.peek().getType() != TokenType.QuestionMark; //evade <?xml?> tag
    }

	

    private boolean tagEnd() {
		boolean end = (current().getType() == TokenType.OPENINGANGLEBRACKET
					   && this.peek().getType() == TokenType.SLASH)
			|| (current().getType() == TokenType.SLASH
				&& this.peek().getType() == TokenType.CLOSINGANGLEBRACKET);

		if(end) {
			index+=2;
		}
		return end;
    }

	private boolean isComment() {
		if(this.current().getType() == TokenType.OPENINGANGLEBRACKET
				&& this.peek().getType() == TokenType.EXP_MARK) {
			return true;
		}
		return false;
	}

	private void passComment() {
		while (this.current().getValue() != "-"
			   && this.peek().getValue() != "-" && this.peek(2).getValue() != ">") {
			this.index++;
		}
	}
	
	
    private Token peek() {
		return this.tokens.get(this.index+1);
    }

    private Token peek(int offset) {
		return this.tokens.get(this.index+offset);
    }
    
    
    private boolean isEOF() {
		return this.index >= this.tokens.size()-1;
    }
    
    private Token current() {
		return this.tokens.get(this.index);
    }

    public void passWhiteSpace() {
		if (current().getType() == TokenType.WHITESPACE) {
			index++;
		}
    }

    public List<Node> getNodes() {
		return this.nodes;
    }
}
