/**
 * 
 */
package org.gradle.core.flexi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.lemmatizer.SimpleLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

/**
 * @author stefano
 *
 */
public class Paraphernalia {

	private static final String CHUNKER = "/%s-chunker.bin";

	private static final String LEMMATIZER = "/%s-lemmatizer.bin";

	private static final String SENTENCE = "/%s-sent.bin";

	private static final String TAG = "/%s-pos-maxent.bin";

	private static final String TOKEN = "/%s-token.bin";

	private ChunkerME chunker;

	/**
	 * The OpenNLP sentence detector.
	 */
	private SentenceDetectorME detector;

	private DictionaryLemmatizer lemmatizer;

	private List<String> errors;

	private Locale locale;

	private POSTaggerME tagger;

	private TokenizerME tokenizer;

	/**
	 * Default constructor.
	 * 
	 * @param locale
	 */
	protected Paraphernalia(Locale locale) {
		if (null == locale)
			throw new IllegalArgumentException("Illegal 'locale' argument in Paraphernalia(Locale): " + locale);
		this.locale = locale;
		this.errors = new ArrayList<>();
		this.chunker = getChunker();
		this.detector = getDetector();
		this.lemmatizer = getLemmatizer();
		this.tagger = getTagger();
		this.tokenizer = getTokenizer();
		assert invariant() : "Illegal state in Paraphernalia(Locale)";
	}

	public String lemmatize(String token, String tag) {
		if (null == token || (token = token.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'token' argument in Paraphernalia.lemmatize(String, String): " + token);
		if (null == tag || (tag = tag.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'tag' argument in Paraphernalia.lemmatize(String, String): " + tag);
		String result = lemmatizer.lemmatize(token, tag);
		assert invariant() : "Illegal state in Paraphernalia.lemmatize(String, String)";
		return result;
	}

	public String[] chunk(String[] tokens, String[] tags) {
		if (null == tokens)
			throw new IllegalArgumentException("Illegal 'tokens' argument in Paraphernalia.chunk(String[], String[]): " + tokens);
		if (null == tags)
			throw new IllegalArgumentException("Illegal 'tags' argument in Paraphernalia.chunk(String[], String[]): " + tags);
		String[] result = chunker.chunk(tokens, tags);
		assert invariant() : "Illegal state in Paraphernalia.chunk(String[], String[])";
		return result;
	}

	public Span[] span(String[] tokens, String[] tags) {
		if (null == tokens)
			throw new IllegalArgumentException("Illegal 'tokens' argument in Paraphernalia.span(String[], String[]): " + tokens);
		if (null == tags)
			throw new IllegalArgumentException("Illegal 'tags' argument in Paraphernalia.span(String[], String[]): " + tags);
		Span[] result = chunker.chunkAsSpans(tokens, tags);
		return result;
	}

	/**
	 * @param text
	 * @return
	 */
	public String[] detect(String text) {
		if (null == text)
			throw new IllegalArgumentException("Illegal 'text' argument in Paraphernalia.detect(String): " + text);
		String[] result = detector.sentDetect(text);
		assert invariant() : "Illegal state in Paraphernalia.detect(String)";
		return result;
	}

	private ChunkerME getChunker() {
		String filename = String.format(CHUNKER, locale.getLanguage());
		ChunkerME result = null;
		try {
			InputStream stream = Paraphernalia.class.getResourceAsStream(filename);
			ChunkerModel cModel = new ChunkerModel(stream);
			result = new ChunkerME(cModel);
			stream.close();
		} catch (FileNotFoundException e) {
			System.err.println(String.format("'%s': %s", filename,
					"Couldn't find the compressed archive with the trained model for chunks detection in the given language..."));
		} catch (InvalidFormatException e) {
			System.err.println(String.format("'%s': %s", filename,
					"The compressed archive with the trained model for chunks detection in the given language is corrupted..."));
		} catch (IOException e) {
			System.err.println(String.format("'%s': %s", filename,
					"Unexpected I/O error while accessing the compressed archive with the trained model for chunks detection in the given language..."));
		}
		return result;
	}

	private SentenceDetectorME getDetector() {
		String filename = String.format(SENTENCE, locale.getLanguage());
		SentenceDetectorME result = null;
		try {
			InputStream stream = Paraphernalia.class.getResourceAsStream(filename);
			SentenceModel sModel = new SentenceModel(stream);
			result = new SentenceDetectorME(sModel);
			stream.close();
		} catch (FileNotFoundException e) {
			errors.add(String.format("'%s': %s", filename,
					"Couldn't find the compressed archive with the trained model for sentence detection in the given language..."));
		} catch (InvalidFormatException e) {
			errors.add(String.format("'%s': %s", filename,
					"The compressed archive with the trained model for sentence detection in the given language is corrupted..."));
		} catch (IOException e) {
			errors.add(String.format("'%s': %s", filename,
					"Unexpected I/O error while accessing the compressed archive with the trained model for sentence detection in the given language..."));
		}
		assert invariant() : "Illegal state in Paraphernalia.getDetector()";
		return result;
	}

	private DictionaryLemmatizer getLemmatizer() {
		String filename = String.format(LEMMATIZER, locale.getLanguage());
		DictionaryLemmatizer result = null;
		try {
			InputStream stream = Paraphernalia.class.getResourceAsStream(filename);
			result = new SimpleLemmatizer(stream);
			stream.close();
		} catch (FileNotFoundException e) {
			errors.add(String.format("'%s': %s", filename,
					"Couldn't find the compressed archive with the trained model for sentence detection in the given language..."));
		} catch (InvalidFormatException e) {
			errors.add(String.format("'%s': %s", filename,
					"The compressed archive with the trained model for sentence detection in the given language is corrupted..."));
		} catch (IOException e) {
			errors.add(String.format("'%s': %s", filename,
					"Unexpected I/O error while accessing the compressed archive with the trained model for sentence detection in the given language..."));
		}
		assert invariant() : "Illegal state in Paraphernalia.getDetector()";
		return result;
	}

	/**
	 * @return
	 */
	public Collection<String> getErrors() {
		assert invariant() : "Illegal state in Paraphernalia.getErrors()";
		return errors;
	}

	private POSTaggerME getTagger() {
		String filename = String.format(TAG, locale.getLanguage());
		POSTaggerME result = null;
		try {
			InputStream stream = Paraphernalia.class.getResourceAsStream(filename);
			POSModel pModel = new POSModel(stream);
			result = new POSTaggerME(pModel);
			stream.close();
		} catch (FileNotFoundException e) {
			System.err.println(String.format("'%s': %s", filename,
					"Couldn't find the compressed archive with the trained model for POS detection in the given language..."));
		} catch (InvalidFormatException e) {
			System.err.println(String.format("'%s': %s", filename,
					"The compressed archive with the trained model for POS detection in the given language is corrupted..."));
		} catch (IOException e) {
			System.err.println(String.format("'%s': %s", filename,
					"Unexpected I/O error while accessing the compressed archive with the trained model for POS detection in the given language..."));
		}
		return result;
	}

	private TokenizerME getTokenizer() {
		String filename = String.format(TOKEN, locale.getLanguage());
		TokenizerME result = null;
		try {
			InputStream stream = Paraphernalia.class.getResourceAsStream(filename);
			TokenizerModel tModel = new TokenizerModel(stream);
			result = new TokenizerME(tModel);
			stream.close();
		} catch (FileNotFoundException e) {
			System.err.println(String.format("'%s': %s", filename,
					"Couldn't find the compressed archive with the trained model for token detection in the given language..."));
		} catch (InvalidFormatException e) {
			System.err.println(String.format("'%s': %s", filename,
					"The compressed archive with the trained model for token detection in the given language is corrupted..."));
		} catch (IOException e) {
			System.err.println(String.format("'%s': %s", filename,
					"Unexpected I/O error while accessing the compressed archive with the trained model for token detection in the given language..."));
		}
		return result;
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
		return (null != errors && null != locale);
	}

	public String[] tag(String[] tokens) {
		if (null == tokens)
			throw new IllegalArgumentException("Illegal 'tokens' argument in Oracle.tag(String[]): " + tokens);
		String[] result = tagger.tag(tokens);
		return result;
	}

	public String[] tokenize(String sentence) {
		if (null == sentence)
			throw new IllegalArgumentException("Illegal 'sentence' argument in Paraphernalia.tokinize(String): " + sentence);
		String[] result = tokenizer.tokenize(sentence);
		assert invariant() : "Illegal state in Paraphernalia.tokenize(String)";
		return result;
	}

}
