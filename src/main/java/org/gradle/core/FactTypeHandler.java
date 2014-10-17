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
public class FactTypeHandler extends Handler {

	private static FactTypeHandler instance = null;

	public static FactTypeHandler get() {
		if (null == instance)
			instance = new FactTypeHandler();
		return instance;
	}

	private FactTypeHandler() {
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
			throw new IllegalArgumentException("Illegal 'id' argument in FactTypeHandler.execute(int, String[], Base, Paraphernalia, List<String>): " + id);
		if (null == tokens || tokens.length < 3)
			throw new IllegalArgumentException("Illegal 'tokens' argument in FactTypeHandler.execute(int, String[], Base, Paraphernalia, List<String>): "
					+ tokens);
		if (null == base)
			throw new IllegalArgumentException("Illegal 'base' argument in FactTypeHandler.execute(int, String[], Base, Paraphernalia, List<String>): " + base);
		if (null == paraphernalia)
			throw new IllegalArgumentException(
					"Illegal 'paraphernalia' argument in FactTypeHandler.execute(int, String[], Base, Paraphernalia, List<String>): " + paraphernalia);
		if (null == errors)
			throw new IllegalArgumentException("Illegal 'errors' argument in FactTypeHandler.execute(int, String[], Base, Paraphernalia, List<String>): "
					+ errors);
		tokens = Arrays.copyOfRange(tokens, 3, tokens.length - 1);
		try {
			String[] ttags = paraphernalia.tag(tokens);
			Span[] spans = paraphernalia.span(tokens, ttags);
			String[] chunks = Span.spansToStrings(spans, tokens);
			String[] ctags = new String[spans.length];
			for (int i = 0; i < spans.length; i++)
				ctags[i] = spans[i].getType();
			if ((2 == chunks.length || 3 == chunks.length && ctags[2].equals("NP")) && ctags[0].equals("NP") && ctags[1].equals("VP")) {
				if (2 == chunks.length)
					base.addFactType(Handler.makeTerm(spans[0], tokens, ttags, paraphernalia), Handler.makeVerb(spans[1], tokens, ttags, paraphernalia));
				else
					base.addFactType(Handler.makeTerm(spans[0], tokens, ttags, paraphernalia), Handler.makeVerb(spans[1], tokens, ttags, paraphernalia),
							Handler.makeTerm(spans[2], tokens, ttags, paraphernalia));

				// String[] tags = paraphernalia.chunk(tokens, ttags);
				// for (Span span : spans) {
				// for (int i = span.getStart(); i < span.getEnd(); i++)
				// System.out.println("* [" + tags[i] + "] " + tokens[i] + " ("
				// + ttags[i] + ")");
				// System.out.println(" >> " + span);

				// for (int i = 0; i < chunks.length; i++)
				// System.out.println("- [  " + ctags[i] + "] " +
				// chunks[i]);
				// System.out.println(" >> " + sentence);
				// }
			} else
				errors.add("Sentence #" + id
						+ ": proper fact types only contains one noun phrase and a verb phrase or a noun phrase, a verb phrase and another noun phrase");
		} catch (NullPointerException e) {
			// errors should already contain a message inherited from
			// paraphernalia telling that NLP is missing
		}

		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gradle.Handler#match(java.lang.String[])
	 */
	@Override
	protected boolean match(String[] tokens) {
		return null != tokens && tokens.length > 4 && tokens[0].equalsIgnoreCase("Fact") && tokens[1].equalsIgnoreCase("type") && tokens[2].equals(":")
				&& tokens[tokens.length - 1].equals(".");
	}

}
