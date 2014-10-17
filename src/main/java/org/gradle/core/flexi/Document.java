/**
 * 
 */
package org.gradle.core.flexi;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author stefano
 *
 */
public class Document implements Iterable<Sentence> {

	private Paraphernalia paraphernalia;

	private String text;

	private String[] sentences;

	/**
	 * @param paraphernalis
	 * @param text
	 */
	public Document(Paraphernalia paraphernalia, String text) {
		if (null == paraphernalia)
			throw new IllegalArgumentException("Illegal 'paraphernalia' argument in Document.Document(Paraphernalia, String): " + paraphernalia);
		if (null == text)
			throw new IllegalArgumentException("Illegal 'text' argument in Document.Document(Paraphernalia, String): " + text);
		this.paraphernalia = paraphernalia;
		this.sentences = paraphernalia.detect(text);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Sentence> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<Sentence>() {
			private int index = 0;
			private int offset = 0;

			@Override
			public boolean hasNext() {
				return index < sentences.length;
			}

			@Override
			public Sentence next() {
				if (!hasNext())
					throw new NoSuchElementException();
				Sentence result = new Sentence(text, offset, sentences[index], index++, paraphernalia);
				offset = result.offset() + result.length();
				return result;
			}
		};
	}

	public Sentence get(int offset) {
		Sentence result = null;
		Iterator<Sentence> iterator = iterator();
		while (null == result && iterator.hasNext()) {
			Sentence sentence = iterator.next();
			if (sentence.offset() <= offset && offset <= sentence.offset() + sentence.length())
				result = sentence;
		}
		return result;
	}

}
