package org.faabtech.brainfuck.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.faabtech.brainfuck.BrainfuckEngine;

/**
 * The {@link YoEngine} is an implementation for the
 * <code>brainfuck<code> dialect
 * 	<code>Yo!</code>.
 * 
 * @author Christian P.
 */
public class YoEngine extends BrainfuckEngine {

	/**
	 * The {@link Token} class contains tokens in <code>Yo!</code>.
	 * 
	 * @author Fabian M.
	 */
	protected static class Token {

		public final static String NEXT = "yo";
		public final static String PREVIOUS = "YO";
		public final static String PLUS = "Yo!";
		public final static String MINUS = "Yo?";
		public final static String OUTPUT = "YO!";
		public final static String INPUT = "yo?";
		public final static String BRACKET_LEFT = "yo!";
		public final static String BRACKET_RIGHT = "YO?";
	}

	/**
	 * Constructs a new {@link YoEngine} instance.
	 * 
	 * @param cells
	 *            The amount of memory cells.
	 */
	public YoEngine(int cells) {
		this(cells, new PrintStream(System.out), System.in);
	}

	/**
	 * Constructs a new {@link YoEngine} instance.
	 * 
	 * @param cells
	 *            The amount of memory cells.
	 * @param out
	 *            The outputstream of this program.
	 */
	public YoEngine(int cells, OutputStream out) {
		this(cells, out, System.in);
	}

	/**
	 * Constructs a new {@link YoEngine} instance.
	 * 
	 * @param cells
	 *            The amount of memory cells.
	 * @param out
	 *            The printstream of this program.
	 * @param in
	 *            The outputstream of this program.
	 */
	public YoEngine(int cells, OutputStream out, InputStream in) {
		super(cells, out, in);
	}

	/**
	 * Interprets the given string.
	 * 
	 * @param str
	 *            The string to interpret.
	 * @throws Exception
	 */
	@Override
	public void interpret(String str) throws Exception {
		// List with tokens.defaultTokenLenght
		List<String> tokens = new ArrayList<String>();
		
		int c=0;
		while(c<str.length()) {
			int tokenStart = c;
			char y = str.charAt(c);
			
			if(Character.isWhitespace(y)) {
				++c;
				continue;
			}
			
			if(y!='y' && y!='Y')
				continue;
			++c;
			char o = str.charAt(c);
			if(o!='o' && o!='O')
				continue;
			++c;
			char _0 = str.charAt(c);
			final String token;
			if(_0=='!' || _0=='?')
				token = str.substring(tokenStart,++c);
			else
				token = str.substring(tokenStart,c);
			
			if(isValidToken(token))
				tokens.add(token);
		}
		
//		StringBuffer b = new StringBuffer();
//		for(String t : tokens)
//			b.append(t);
//		System.out.println(b.toString());
		
		// Loop through all tokens.
		for (int tokenPointer = 0; tokenPointer < tokens.size(); ) {
			String token = tokens.get(tokenPointer);
		
			if (token.equals(Token.NEXT)) {
				// increment the data pointer (to point to the next cell
				// to the
				// right).
				dataPointer = (dataPointer == data.length - 1 ? 0 : dataPointer + 1);
			} 
			if (token.equals(Token.PREVIOUS)) {
				// decrement the data pointer (to point to the next cell
				// to the
				// left).
				dataPointer = (dataPointer == 0 ? data.length - 1 : dataPointer - 1);
			} 
			if (token.equals(Token.PLUS)) {
				// increment (increase by one) the byte at the data
				// pointer.
				data[dataPointer]++;
			} 
			if (token.equals(Token.MINUS)) {
				// decrement (decrease by one) the byte at the data
				// pointer.
				data[dataPointer]--;
			}
			if (token.equals(Token.OUTPUT)) {
				// Output the byte at the current index in a character.
				outWriter.write((char) data[dataPointer]);
				// Flush the outputstream.
				outWriter.flush();
			} 
			if (token.equals(Token.INPUT)) {
				// accept one byte of input, storing its value in the
				// byte at the data pointer.
				data[dataPointer] = (byte) consoleReader.read();
			} 
			if (token.equals(Token.BRACKET_LEFT)) {
				if (data[dataPointer] == 0) {
					int level = 1;
					while (level > 0) {	
						tokenPointer++;
						
						if (tokens.get(tokenPointer).equals(Token.BRACKET_LEFT)) 
							level++;
						else if (tokens.get(tokenPointer).equals(Token.BRACKET_RIGHT))
							level--;
					}
				}
			}
			if (token.equals(Token.BRACKET_RIGHT)) {
				if (data[dataPointer] != 0) {
					int level = 1;
					while (level > 0) {
						tokenPointer--;
						
						if (tokens.get(tokenPointer).equals(Token.BRACKET_LEFT)) 
							level--;
						else if (tokens.get(tokenPointer).equals(Token.BRACKET_RIGHT))
							level++;
					}
				}
			}
			
			tokenPointer++;
		}
		// Clear all data.
		initate(data.length);
	}
	
	protected boolean isValidToken(String token) {
		if (token.equals(Token.NEXT)
				|| token.equals(Token.PREVIOUS) || token.equals(Token.PLUS)
				|| token.equals(Token.MINUS) || token.equals(Token.OUTPUT)
				|| token.equals(Token.INPUT)
				|| token.equals(Token.BRACKET_LEFT)
				|| token.equals(Token.BRACKET_RIGHT)) {
			return true;
		}
		return false;
	}

}
