/**
 * 
 */
package org.gradle.core.flexi;

import java.util.Iterator;

import opennlp.tools.util.Span;

import org.apache.commons.lang3.StringUtils;

/**
 * @author stefano
 *
 */
public class Chunk implements Fragment, Iterable<Token> {

	private int col;

	private int index;

	private int length;

	private int offset;

	private int row;

	private String source;

	private Span span;

	private STag tag;

	private String[] tags;

	private String text;

	private String[] tokens;

	public Chunk(String source, int offset, Span span, String[] tokens, String[] tags, int index) {
		if (null == source)
			throw new IllegalArgumentException("Illegal 'source' argument in Chunk(String, int, Span, String[], String[], int): " + source);
		if (offset < 0)
			throw new IllegalArgumentException("Illegal 'offset' argument in Chunk(String, int, Span, String[], String[], int): " + offset);
		if (null == span)
			throw new IllegalArgumentException("Illegal 'span' argument in Chunk(String, int, Span, String[], String[], int): " + span);
		if (null == tokens)
			throw new IllegalArgumentException("Illegal 'tokens' argument in Chunk(String, int, Span, String[], String[], int): " + tokens);
		if (null == tags)
			throw new IllegalArgumentException("Illegal 'tags' argument in Chunk(String, int, Span, String[], String[], int): " + tags);
		if (index < 0)
			throw new IllegalArgumentException("Illegal 'index' argument in Chunk(String, int, Span, String[], String[], int): " + index);
		String temp = tokens[span.getStart()];
		this.offset = source.indexOf(temp, offset);

		int pivot = source.lastIndexOf("\n", this.offset);
		this.col = (pivot > -1 ? source.substring(pivot + 1) : source).indexOf(temp, (pivot > -1 ? offset - pivot - 1 : offset));
		this.index = index;
		int tmpoffset = this.offset + temp.length();
		for (int i = 1 + span.getStart(); i < span.getEnd(); i++)
			tmpoffset = source.indexOf(tokens[i], tmpoffset) + tokens[i].length();
		this.text = source.substring(this.offset, tmpoffset);
		this.length = text.length();
		this.row = StringUtils.countMatches(source.substring(0, this.offset), "\n");
		this.source = source;
		this.span = span;
		this.tag = STag.decode(span.getType());
		this.tags = tags;
		this.tokens = tokens;
	}

	@Override
	public int col() {
		return col;
	}

	public STag getTag() {
		return tag;
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
	public Iterator<Token> iterator() {
		return new Iterator<Token>() {
			private int index = span.getStart();
			private int offset = Chunk.this.offset;

			@Override
			public boolean hasNext() {
				return index < span.getEnd();
			}

			@Override
			public Token next() {
				Token result = new Token(source, offset, tokens[index], tags[index], index++);
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

	@Override
	public String toString() {
		return "[" + row + ":" + col + "]\t" + (1 + index) + ". '" + text + "' (" + tag.getSymbol() + ")";
	}

}
