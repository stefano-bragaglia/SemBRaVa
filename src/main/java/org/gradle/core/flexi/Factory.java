/**
 * 
 */
package org.gradle.core.flexi;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author stefano
 *
 */
public class Factory {

	/**
	 * 
	 */
	private static Map<Locale, Paraphernalia> paraphernalia = new HashMap<>();

	public static Paraphernalia get(Locale locale) {
		if (null == locale)
			throw new IllegalArgumentException("Illegal 'locale' argument in Factory.get(Locale): " + locale);
		Paraphernalia result = paraphernalia.get(locale);
		if (null == result) {
			result = new Paraphernalia(locale);
			paraphernalia.put(locale, result);
		}
		return result;
	}

	public static Document getDocument(Locale locale, String filename, Charset encoding) {
		Path path;
		if (null == locale)
			throw new IllegalArgumentException("Illegal 'locale' argument in Factory.getDocument(Locale, String, Charset): " + locale);
		if (null == filename || (filename = filename.trim()).isEmpty() || Files.notExists(path = Paths.get(filename)))
			throw new IllegalArgumentException("Illegal 'filename' argument in Factory.getDocument(Locale, String, Charset): " + filename);
		if (null == encoding)
			throw new IllegalArgumentException("Illegal 'encoding' argument in Factory.getDocument(Locale, String, Charset): " + encoding);
		try {
			Paraphernalia paraphernalis = paraphernalia.get(locale);
			if (null == paraphernalis) {
				paraphernalis = new Paraphernalia(locale);
				paraphernalia.put(locale, paraphernalis);
			}
			return new Document(paraphernalis, new String(Files.readAllBytes(path), encoding));
		} catch (IOException e) {
			throw new IllegalArgumentException("Illegal 'filename' argument in Factory.getDocument(Locale, String, Charset): " + filename);
		}
	}

	public static Document getDocument(Locale locale, String text) {
		if (null == locale)
			throw new IllegalArgumentException("Illegal 'locale' argument in Factory.getBuilder(Locale, String): " + locale);
		if (null == text)
			throw new IllegalArgumentException("Illegal 'text' argument in Factory.getBuilder(Locale, String): " + text);
		Paraphernalia paraphernalis = paraphernalia.get(locale);
		if (null == paraphernalis) {
			paraphernalis = new Paraphernalia(locale);
			paraphernalia.put(locale, paraphernalis);
		}
		return new Document(paraphernalis, text);
	}

	/**
	 * @param builder
	 * @return
	 */
	public static Base getBase(Builder builder) {
		if (null == builder)
			throw new IllegalArgumentException("Illegal 'builder' argument in Factory.getBase(Builder): " + builder);
		Base base = builder.getBase();
		return base;
	}

	/**
	 * @param locale
	 * @return
	 */
	public static Builder getBuilder(Locale locale) {
		if (null == locale)
			throw new IllegalArgumentException("Illegal 'locale' argument in Factory.getBuilder(Locale): " + locale);
		Paraphernalia paraphernalis = paraphernalia.get(locale);
		if (null == paraphernalis) {
			paraphernalis = new Paraphernalia(locale);
			paraphernalia.put(locale, paraphernalis);
		}
		return new Builder(paraphernalis);
	}

}
