/**
 * 
 */
package org.gradle.core.flexi;

import org.apache.commons.lang3.StringUtils;

/**
 * @author stefano
 *
 */
public class Token implements Fragment {

	private int col;

	private int index;

	private int length;

	private int offset;

	private int row;

	private POSTag tag;

	private String text;

	public Token(String source, int offset, String text, String type, int index) {
		if (null == source)
			throw new IllegalArgumentException("Illegal 'source' argument in Token.Token(String, int, String, String, int): " + source);
		if (offset < 0)
			throw new IllegalArgumentException("Illegal 'offset' argument in Token.Token(String, int, String, String, int): " + offset);
		if (null == text || (text = text.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'text' argument in Token.Token(String, int, String, String), int: " + text);
		if (null == type || (type = type.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'type' argument in Token.Token(String, int, String, String, int): " + type);
		if (index < 0)
			throw new IllegalArgumentException("Illegal 'index' argument in Token.Token(String, int, String, String, int): " + index);

		this.offset = source.indexOf(text, offset);

		int pivot = source.lastIndexOf("\n", this.offset);
		this.col = (pivot > -1 ? source.substring(pivot + 1) : source).indexOf(text, (pivot > -1 ? offset - pivot - 1: offset));
		this.index = index;
		this.length = text.length();
		this.row = StringUtils.countMatches(source.substring(0, this.offset), "\n");
		this.tag = POSTag.decode(type);
		this.text = text;
	}

	@Override
	public int col() {
		return col;
	}

	public POSTag getTag() {
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
