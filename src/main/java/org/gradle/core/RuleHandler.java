/**
 * 
 */
package org.gradle.core;

import java.beans.Statement;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.util.Span;

import org.gradle.core.flexi.Base;
import org.gradle.core.flexi.Paraphernalia;

/**
 * @author stefano
 *
 */
public class RuleHandler extends Handler {

	private static RuleHandler instance = null;

	public static RuleHandler get() {
		if (null == instance)
			instance = new RuleHandler();
		return instance;
	}

	private RuleHandler() {
	}

	private String parseTerm(Base base, int i, Span[] spans, String[] tokens, String[] ttags, Paraphernalia paraphernalia) {
		if (null == base)
			throw new IllegalArgumentException("Illegal 'base' argument in RuleHandler.parseTerm(Base, int, Span[], String[], String[], Paraphernalia): "
					+ base);
		if (i < 4 || i >= spans.length)
			throw new IllegalArgumentException("Illegal 'i' argument in RuleHandler.parseTerm(Base, int, Span[], String[], String[], Paraphernalia): " + i);
		if (null == spans || spans.length < 5)
			throw new IllegalArgumentException("Illegal 'spans' argument in RuleHandler.parseTerm(Base, int, Span[], String[], String[], Paraphernalia): "
					+ spans);
		if (null == ttags || ttags.length < 5)
			throw new IllegalArgumentException("Illegal 'ttags' argument in RuleHandler.parseTerm(Base, int, Span[], String[], String[], Paraphernalia): "
					+ ttags);
		if (null == tokens)
			throw new IllegalArgumentException("Illegal 'tokens' argument in RuleHandler.parseNP(Base, int, Span[], String[], String[], Paraphernalia): "
					+ tokens);
		if (null == paraphernalia)
			throw new IllegalArgumentException(
					"Illegal 'paraphernalia' argument in RuleHandler.parseTerm(Base, int, Span[], String[], String[], Paraphernalia): " + paraphernalia);
		String result;
		if (spans[i].getType().equals("NP")) {
			result = Handler.makeTerm(spans[i], tokens, ttags, paraphernalia);
			if (!base.contains(result))
				result = null;
		} else
			result = null;
		// assert invariant() :
		// "Illegal state in RuleHandler.parseTerm(Base, int, Span[], String[], String[], Paraphernalia)";
		return result;
	}

	private String parseVerb(Base base, String term, int i, Span[] spans, String[] tokens, String[] ttags, Paraphernalia paraphernalia) {
		if (null == base)
			throw new IllegalArgumentException(
					"Illegal 'base' argument in RuleHandler.parseVerb(Base, String, int, Span[], String[], String[], Paraphernalia): " + base);
		if (i < 4 || i >= spans.length)
			throw new IllegalArgumentException("Illegal 'i' argument in RuleHandler.parseVerb(Base, String, int, Span[], String[], String[], Paraphernalia): "
					+ i);
		if (null == spans || spans.length < 5)
			throw new IllegalArgumentException(
					"Illegal 'spans' argument in RuleHandler.parseVerb(Base, String, int, Span[], String[], String[], Paraphernalia): " + spans);
		if (null == ttags || ttags.length < 5)
			throw new IllegalArgumentException(
					"Illegal 'ttags' argument in RuleHandler.parseVerb(Base, String, int, Span[], String[], String[], Paraphernalia): " + ttags);
		if (null == tokens)
			throw new IllegalArgumentException(
					"Illegal 'tokens' argument in RuleHandler.parseVerb(Base, String, int, Span[], String[], String[], Paraphernalia): " + tokens);
		if (null == paraphernalia)
			throw new IllegalArgumentException(
					"Illegal 'paraphernalia' argument in RuleHandler.parseVerb(Base, String, int, Span[], String[], String[], Paraphernalia): " + paraphernalia);
		String result;
		if (spans[i].getType().equals("VP")) {
			result = Handler.makeVerb(spans[i], tokens, ttags, paraphernalia);
			if (!base.contains(term, result))
				result = null;
		} else
			result = null;
		// assert invariant() :
		// "Illegal state in RuleHandler.parseVerb(Base, String, int, Span[], String[], String[], Paraphernalia)";
		return result;
	}

	// Sentence = Name Verb [Name]
	// Name = Term [That Verb [Name] ]
	// not really... the subject will be missin
	private Statement parseStatement() {
		return null;
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
			throw new IllegalArgumentException("Illegal 'id' argument in RuleHandler.execute(int, String[], Base, Paraphernalia, List<String>): " + id);
		if (null == tokens || tokens.length < 3)
			throw new IllegalArgumentException("Illegal 'tokens' argument in RuleHandler.execute(int, String[], Base, Paraphernalia, List<String>): " + tokens);
		if (null == base)
			throw new IllegalArgumentException("Illegal 'base' argument in RuleHandler.execute(int, String[], Base, Paraphernalia, List<String>): " + base);
		if (null == paraphernalia)
			throw new IllegalArgumentException("Illegal 'paraphernalia' argument in RuleHandler.execute(int, String[], Base, Paraphernalia, List<String>): "
					+ paraphernalia);
		if (null == errors)
			throw new IllegalArgumentException("Illegal 'errors' argument in RuleHandler.execute(int, String[], Base, Paraphernalia, List<String>): " + errors);
		tokens = Arrays.copyOfRange(tokens, 2, tokens.length - 1);
		try {
			String[] ttags = paraphernalia.tag(tokens);
			Span[] spans = paraphernalia.span(tokens, ttags);
			String[] chunks = Span.spansToStrings(spans, tokens);
			String[] ctags = new String[spans.length];
			for (int i = 0; i < spans.length; i++)
				ctags[i] = spans[i].getType();
			if (chunks.length >= 5
					&& ctags[0].equals("NP")
					&& ttags[0].equals("PRP")
					&& tokens[0].equalsIgnoreCase("it")
					&& ctags[1].equals("VP")
					&& ttags[1].equals("VBZ")
					&& tokens[1].equalsIgnoreCase("is")
					&& ctags[2].equals("ADJP")
					&& ttags[2].equals("JJ")
					&& (tokens[2].equalsIgnoreCase("necessary") || tokens[2].equalsIgnoreCase("possible") || tokens[2].equalsIgnoreCase("obligatory") || tokens[2]
							.equalsIgnoreCase("permitted")) && ctags[3].equals("PP") && ttags[3].equals("IN") && tokens[3].equalsIgnoreCase("that")) {
				System.out.println("!!! " + tokens[2].toUpperCase());

				// Extensive use of Chain of Responsibility, because of all the
				// exceptions that you potentially have to deal, however, how
				// is it computationally-wise?

				// parseSentence() { Name VP [Name] }
				// parseName() { NP That Sentence }

				// parseNP() {}
				// parseVP() {}
				// parseThat() {}

				// NP - WDT

				String[] tags = paraphernalia.chunk(tokens, ttags);
				for (int j = 4; j < spans.length; j++) {
					System.out.println(">> " + chunks[j] + " (" + ctags[j] + ")");
					for (int i = spans[j].getStart(); i < spans[j].getEnd(); i++)
						System.out.println("* [" + tags[i] + "] " + tokens[i] + " (" + ttags[i] + ")");
				}
			} else
				errors.add("Sentence #" + id + ": proper rules only start with necessary, possible, obligatory or permitted model operators");
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
		return null != tokens && tokens.length > 3 && tokens[0].equalsIgnoreCase("Rule") && tokens[1].equals(":") && tokens[tokens.length - 1].equals(".");
	}

}
