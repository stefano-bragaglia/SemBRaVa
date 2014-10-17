/**
 *
 */
package org.gradle.core.flexi;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import opennlp.tools.cmdline.PerformanceMonitor;

import org.gradle.core.ErrorHandler;
import org.gradle.core.FactTypeHandler;
import org.gradle.core.Handler;
import org.gradle.core.RuleHandler;
import org.gradle.core.TermHandler;

/**
 * @author stefano
 *
 */
public class Builder {

	private Base base;

	private List<String> errors;

	private Handler handler;

	private Paraphernalia paraphernalia;

	/**
	 * Default constructor.
	 * 
	 * @param locale
	 */
	protected Builder(Paraphernalia paraphernalia) {
		if (null == paraphernalia)
			throw new IllegalArgumentException("Illegal 'locale' argument in Builder(Paraphernalia): " + paraphernalia);
		this.base = new Base();
		this.errors = new ArrayList<>(paraphernalia.getErrors());
		this.handler = ErrorHandler.get();
		this.handler = RuleHandler.get().setNext(handler);
		this.handler = FactTypeHandler.get().setNext(handler);
		this.handler = TermHandler.get().setNext(handler);
		this.paraphernalia = paraphernalia;
		assert invariant() : "Illegal state in Builder(Paraphernalia)";
	}

	protected Base getBase() {
		assert invariant() : "Illegal state in Builder.getBase()";
		return base;
	}

	/**
	 * @return
	 */
	public Collection<String> getErrors() {
		assert invariant() : "Illegal state in Paraphernalia.getErrors()";
		return errors;
	}

	/**
	 * @return
	 */
	public boolean hasErrors() {
		boolean result = !errors.isEmpty();
		assert invariant() : "Illegal state in Paraphernalia.hasErrors()";
		return result;
	}

	/**
	 * Invariant check against the internal state.
	 * 
	 * @return <code>true</code> if this instance's state is consistent,
	 *         <code>false</code> otherwise
	 */
	private boolean invariant() {
		return (null != base && null != errors && null != handler && null != paraphernalia);
	}

	private static final int LIMIT = 15;

	private volatile int size = 0;

	/**
	 * @return
	 */
	public int size() {
		assert invariant() : "Illegal state in Builder.size()";
		return size;
	}

	/**
	 * @param text
	 * @return
	 */
	public void load(String text) {
		if (null == text)
			throw new IllegalArgumentException("Illegal 'text' argument in Builder.load(String): " + text);
		if (errors.size() < LIMIT) {
			PerformanceMonitor monitor = new PerformanceMonitor(System.err, "sentence");
			monitor.startAndPrintThroughput();
			try {
				String[] sentences = paraphernalia.detect(text);
				for (String sentence : sentences) {
					if (!sentence.endsWith("."))
						sentence += ".";
					handler.handle(++size, sentence, base, paraphernalia, errors);
					monitor.incrementCounter();
					if (errors.size() >= LIMIT) {
						errors.add("Parsing interrupted because of too many errors");
						break;
					}
				}
			} catch (NullPointerException e) {
				// errors should already contain a message inherited from
				// paraphernalia telling that NLP is missing
			}
			monitor.stopAndPrintFinalResult();
		}
		assert invariant() : "Illegal state in Builder.load(String)";
	}

	public void load(String filename, Charset encoding) {
		Path path;
		if (null == filename || (filename = filename.trim()).isEmpty() || Files.notExists(path = Paths.get(filename)))
			throw new IllegalArgumentException("Illegal 'filename' argument in Builder.load(String, Charset): " + filename);
		if (null == encoding)
			throw new IllegalArgumentException("Illegal 'encoding' argument in Builder.load(String, Charset): " + encoding);
		try {
			load(new String(Files.readAllBytes(path), encoding));
		} catch (IOException e) {
			errors.add("Impossible to access file '" + filename + "'");
		}
	}

}
