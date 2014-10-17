/**
 * 
 */
package org.gradle.core;

import java.util.Arrays;
import java.util.List;

import opennlp.tools.util.Span;

import org.gradle.core.flexi.Base;
import org.gradle.core.flexi.Paraphernalia;

/**
 * @author stefano
 *
 */
public class TermHandler extends Handler {

	private static TermHandler instance = null;

	public static TermHandler get() {
		if (null == instance)
			instance = new TermHandler();
		return instance;
	}

	private TermHandler() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gradle.Handler#execute(int, java.lang.String[],
	 * org.gradle.core.Paraphernalia, java.util.List)
	 */
	@Override
	protected void execute(int id, String[] tokens, Base base, Paraphernalia paraphernalia, List<String> errors) {
		if (id < 0)
			throw new IllegalArgumentException("Illegal 'id' argument in TermHandler.execute(int, String[], Base, Paraphernalia, List<String>): " + id);
		if (null == tokens || tokens.length < 3)
			throw new IllegalArgumentException("Illegal 'tokens' argument in TermHandler.execute(int, String[], Base, Paraphernalia, List<String>): " + tokens);
		if (null == base)
			throw new IllegalArgumentException("Illegal 'base' argument in TermHandler.execute(int, String[], Base, Paraphernalia, List<String>): " + base);
		if (null == paraphernalia)
			throw new IllegalArgumentException("Illegal 'paraphernalia' argument in TermHandler.execute(int, String[], Base, Paraphernalia, List<String>): "
					+ paraphernalia);
		if (null == errors)
			throw new IllegalArgumentException("Illegal 'errors' argument in TermHandler.execute(int, String[], Base, Paraphernalia, List<String>): " + errors);
		tokens = Arrays.copyOfRange(tokens, 2, tokens.length - 1);
		try {
			String[] ttags = paraphernalia.tag(tokens);
			Span[] spans = paraphernalia.span(tokens, ttags);
			String[] chunks = Span.spansToStrings(spans, tokens);
			String[] ctags = new String[spans.length];
			for (int i = 0; i < spans.length; i++)
				ctags[i] = spans[i].getType();
			if (1 == chunks.length && ctags[0].equals("NP")) {
				// String[] tags = paraphernalia.chunk(tokens, ttags);
				base.addTerm(Handler.makeTerm(spans[0], tokens, ttags, paraphernalia));
				// for (Span span : spans)
				// System.out.println(" >> " + span.getStart() + " - " +
				// span.getEnd());
				// for (int i = 0; i < tokens.length; i++)
				// System.out.println("* [" + tags[i] + "] " + tokens[i] + " ("
				// + ttags[i] + ")");
				// for (int i = 0; i < chunks.length; i++)
				// System.out.println("- [  " + ctags[i] + "] " + chunks[i]);
				// System.out.println(" >> " + sentence);
			} else
				errors.add("Sentence #" + id + ": proper terms only contains one noun phrases");
		} catch (NullPointerException e) {
			// errors should already contain a message inherited from
			// paraphernalia telling that NLP is missing
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gradle.Handler#match(java.lang.String[])
	 */
	@Override
	protected boolean match(String[] tokens) {
		return null != tokens && tokens.length > 3 && tokens[0].equalsIgnoreCase("Term") && tokens[1].equals(":") && tokens[tokens.length - 1].equals(".");
	}

}
