package com.walid.XMLParser;

import java.util.List;

import java.util.ArrayList;

public class Tokenizer {

    private String string;
	private int position;
	private List<Token> tokens;
		
	public Tokenizer(String  string) {
		this.string = string;
		this.tokens = new ArrayList<Token>();
		this.position = 0;
		this.doTokenization();
	}


   private void doTokenization() {
		while(!this.isEOF()){
			matchLeftBracket();
			matchRightBracket();
			matchAlphaNumeric();
			matchWhiteSpace();
			matchEqualSign();
			matchDoubleQuote();
			matchSingleQuote();
			matchSlash();
			this.position++;
		}
    }


	private boolean isEOF() {
		return position >= this.string.length() - 1; 
	}

	private char current() {
		return this.string.charAt(this.position);
	}


	private void matchLeftBracket() {
		if(this.current() == '<') {
			this.tokens.add(new Token(TokenType.OPENINGANGLEBRACKET, "<"));
		} 

	}

	private void matchRightBracket() {
		if(this.current() == '>') {
			this.tokens.add(new Token(TokenType.CLOSINGANGLEBRACKET, ">"));
		} 
	}

	private void matchAlphaNumeric() {
		if(isAlphaNumeric(current())) {
			int cur = this.position;
			while(isAlphaNumeric(peek())) {
				this.position++;
			}
			this.tokens
				.add(new Token(TokenType.ALPHANUMURIC,
							   this.string.substring(cur,this.position+1)));
		} 
	}

	private void matchWhiteSpace() {
		if(isWhiteSpace(current())) {
			int cur = this.position;
			while(isWhiteSpace(peek())) {
				this.position++;
			}
			this.tokens
				.add(new Token(TokenType.WHITESPACE,
							   this.string.substring(cur, this.position+1)));
		}

	}


	private void matchEqualSign() {
		if(current() == '=') {
			this.tokens.add(new Token(TokenType.EQIAL, "="));
		}
	}
	
	private void matchDoubleQuote() {
		if(this.current() == '"') {
			this.tokens
				.add(new Token(TokenType.DOUBLE_QUOTE, "\""));
							   
		}
	}

	private void matchSingleQuote() {
		if(this.current() == '\'') {
			this.tokens
				.add(new Token(TokenType.SINGLE_QUOTE, "'"));
							   
		}
	}


	
	
	private void matchSlash() {
		if(this.current() == '/') {
			this.tokens.add(new Token(TokenType.SLASH, "/"));
		}
	}

	private boolean isWhiteSpace(char ch) {
		return Character.isWhitespace(ch)
			|| ch == '\n'
			|| ch == '\t'
			|| (ch == '\r' && ch == '\n');
	}


	private boolean isAlphaNumeric(char ch) {
	    return Character.isAlphabetic(ch)
		|| Character.isDigit(ch)
		|| ch == '{'
		|| ch == '}'
		|| ch == '('
		|| ch == ')'
		|| ch == ':'
		|| ch == '-'
		|| ch == '.';
	}

	private char peek() {
		return this.string.charAt(this.position+1);
	}

	private char peek(int offset) {
		return this.string.charAt(this.position+offset);
	}

	public List<Token> getTokens() {
		return this.tokens;
	}
	    

	

}
