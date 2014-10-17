/**
 * 
 */
package org.gradle.core.flexi;

import java.util.Iterator;
import java.util.NoSuchElementException;

import opennlp.tools.util.Span;

import org.apache.commons.lang3.StringUtils;

/**
 * @author stefano
 *
 */
public class Sentence implements Fragment, Iterable<Chunk> {

	private int col;

	private int index;

	private int length;

	// private Paraphernalia paraphernalia;

	private int offset;

	private int row;

	private String source;

	private Span[] spans;

	private String[] tags;

	private String text;

	private String[] tokens;
	
	public Sentence(String source, int offset, String text, int index, Paraphernalia paraphernalia) {
		if (null == source)
			throw new IllegalArgumentException("Illegal 'source' argument in Sentence(String, int, String, int, Paraphernalia): " + source);
		if (offset < 0)
			throw new IllegalArgumentException("Illegal 'offset' argument in Sentence(String, int, String, int, Paraphernalia): " + offset);
		if (null == text || (text = text.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'text' argument in Sentence(String, int, String, int, Paraphernalia): " + text);
		if (index < 0)
			throw new IllegalArgumentException("Illegal 'index' argument in Sentence(String, int, String, int, Paraphernalia): " + index);
		if (null == paraphernalia)
			throw new IllegalArgumentException("Illegal 'paraphernalia' argument in Sentence(String, int, String, int, Paraphernalia): " + paraphernalia);

		this.offset = source.indexOf(text, offset);

		int pivot = source.lastIndexOf("\n", this.offset);
		this.col = (pivot > -1 ? source.substring(pivot + 1) : source).indexOf(text, (pivot > -1 ? offset - pivot - 1 : offset));
		this.index = index;
		this.length = text.length();
		// this.paraphernalia = paraphernalia;
		this.row = StringUtils.countMatches(source.substring(0, this.offset), "\n");
		this.source = source;
		this.tokens = paraphernalia.tokenize(text);
		this.tags = paraphernalia.tag(tokens);
		this.spans = paraphernalia.span(tokens, tags);
		this.text = text;
		// String[] chunks = Span.spansToStrings(spans, tokens);
		// String[] ctags = new String[spans.length];
		// for (int i = 0; i < spans.length; i++)
		// ctags[i] = spans[i].getType();
	}

	public Sentence(String source, String text, int index, Paraphernalia paraphernalia) {
		this(source, 0, text, index, paraphernalia);
	}

	@Override
	public int col() {
		return col;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public int index() {
		return index;
	}

	@Override
	public Iterator<Chunk> iterator() {
		return new Iterator<Chunk>() {
			private int index = 0;
			private int offset = Sentence.this.offset; 

			@Override
			public boolean hasNext() {
				return index < spans.length;
			}

			@Override
			public Chunk next() {
				Chunk result = new Chunk(source, offset, spans[index], tokens, tags, index++);
				offset = result.offset() + result.length();
				return result;
			}
		};
	}

	@Override
	public int length() {
		return length;
	}

	@Override
	public int offset() {
		return offset;
	}

	@Override
	public int row() {
		return row;
	}

	public Iterator<Token> tokens() {
		return new Iterator<Token>() {
			private int index = 0;

			@Override
			public boolean hasNext() {
				return index < tokens.length;
			}

			@Override
			public Token next() {
				if (!hasNext())
					throw new NoSuchElementException();
				return new Token(source, offset, tokens[index], tags[index], index++);
			}
		};

	}

	@Override
	public String toString() {
		return "[" + row + ":" + col + "]\t" + (1 + index) + ". '" + text + "'";
	}
}
