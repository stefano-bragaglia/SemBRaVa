/**
 * 
 */
package org.gradle.core;

import java.util.List;

import opennlp.tools.util.Span;

import org.gradle.core.flexi.Base;
import org.gradle.core.flexi.Paraphernalia;

/**
 * @author stefano
 *
 */
public abstract class Handler {

	public static String makeTerm(Span span, String[] tokens, String[] tags, Paraphernalia paraphernalia) {
		if (null == span)
			throw new IllegalArgumentException("Illegal 'span' argument in Handler.makeTerm(Span, String[], String[], Paraphernalia): " + span);
		if (null == tokens || tokens.length < span.getEnd())
			throw new IllegalArgumentException("Illegal 'tokens' argument in Handler.makeTerm(Span, String[], String[], Paraphernalia): " + tokens);
		if (null == tags || tags.length < span.getEnd())
			throw new IllegalArgumentException("Illegal 'tags' argument in Handler.makeTerm(Span, String[], String[], Paraphernalia): " + tags);
		if (null == paraphernalia)
			throw new IllegalArgumentException("Illegal 'paraphernalia' argument in Handler.makeTerm(Span, String[], String[], Paraphernalia): "
					+ paraphernalia);
		String result = "";
		for (int i = span.getStart(); i < span.getEnd(); i++)
			if (!tags[i].equals("DT") && !tokens[i].isEmpty()) {
				String word = tokens[i];
				if (tags[i].equals("NNS"))
					word = paraphernalia.lemmatize(word, tags[i]);
				result += word.substring(0, 1).toUpperCase() + word.substring(1);
			}
		if (result.isEmpty())
			throw new IllegalArgumentException("Illegal 'tokens || tokens.length>=span.getEnd()' argument in Handler.makeTerm(Span, String[], String[]): "
					+ tokens);
		return result;
	}

	public static String makeVerb(Span span, String[] tokens, String[] tags, Paraphernalia paraphernalia) {
		if (null == span)
			throw new IllegalArgumentException("Illegal 'span' argument in Handler.makeVerb(Span, String[], String[], Paraphernalia): " + span);
		if (null == tokens || tokens.length < span.getEnd())
			throw new IllegalArgumentException("Illegal 'tokens' argument in Handler.makeVerb(Span, String[], String[], Paraphernalia): " + tokens);
		if (null == tags || tags.length < span.getEnd())
			throw new IllegalArgumentException("Illegal 'tags' argument in Handler.makeVerb(Span, String[], String[], Paraphernalia): " + tags);
		if (null == paraphernalia)
			throw new IllegalArgumentException("Illegal 'paraphernalia' argument in Handler.makeVerb(Span, String[], String[], Paraphernalia): "
					+ paraphernalia);
		String result = "";
		for (int i = span.getStart(); i < span.getEnd(); i++)
			if (!tags[i].equals("RB") && !tokens[i].isEmpty()) {
				String word = tokens[i];
				// if (tags[i].equals("NNS"))
				// word = paraphernalia.lemmatize(word, tags[i]);
				if (result.isEmpty())
					result += word.substring(0, 1).toLowerCase() + word.substring(1);
				else
					result += word.substring(0, 1).toUpperCase() + word.substring(1);
			}
		if (result.isEmpty())
			throw new IllegalArgumentException("Illegal 'tokens || tokens.length>=span.getEnd()' argument in Handler.makeVerb(Span, String[], String[]): "
					+ tokens);
		return result;
	}

	protected boolean serve = false;

	public void serveFirst() {
		serve = false;
	}

	public void serveAll() {
		serve = true;
	}

	protected Handler next;

	abstract protected void execute(int id, String[] tokens, Base base, Paraphernalia paraphernalia, List<String> errors);

	public void handle(int id, String sentence, Base base, Paraphernalia paraphernalia, List<String> errors) {
		if (null == sentence)
			throw new IllegalArgumentException("Illegal 'sentence' argument in Handler.handle(int, String, Base, Paraphernalia, List<String>): " + sentence);
		if (null == base)
			throw new IllegalArgumentException("Illegal 'base' argument in Handler.handle(int, String, Base, Paraphernalia, List<String>): " + base);
		if (null == paraphernalia)
			throw new IllegalArgumentException("Illegal 'paraphernalia' argument in Handler.handle(int, String, Base, Paraphernalia, List<String>): "
					+ paraphernalia);
		if (null == errors)
			throw new IllegalArgumentException("Illegal 'errors' argument in Handler.handle(int, String, Base, Paraphernalia, List<String>): " + errors);
		try {
			String[] tokens = paraphernalia.tokenize(sentence);
			boolean matched;
			if (matched = match(tokens))
				execute(id, tokens, base, paraphernalia, errors);
			if ((serve || !matched) && null != next)
				next.handle(id, sentence, base, paraphernalia, errors);
		} catch (NullPointerException e) {
			// errors should already contain a message inherited from
			// paraphernalia telling that NLP is missing
		}
	}

	abstract protected boolean match(String[] tokens);

	public Handler setNext(Handler next) {
		if (null == next)
			throw new IllegalArgumentException("Illegal 'next' argument in Handler.setNext(handler): " + next);
		this.next = next;
		return this;
	}

}
