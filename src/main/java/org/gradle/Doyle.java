/**
 * 
 */
package org.gradle;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import opennlp.tools.cmdline.PerformanceMonitor;

import org.gradle.core.flexi.Factory;
import org.gradle.core.flexi.Paraphernalia;

/**
 * @author stefano
 *
 */
public class Doyle {

	private static String description(String tag) {
		if (null == tag || (tag = tag.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'tag' argument in Doyle.qwerty(String): " + tag);
		if (tag.equals("CC"))
			return "Coordinating conjunction";
		else if (tag.equals("CD"))
			return "Cardinal number";
		else if (tag.equals("DT"))
			return "Determiner";
		else if (tag.equals("EX"))
			return "Existential there";
		else if (tag.equals("FW"))
			return "Foreign word";
		else if (tag.equals("IN"))
			return "Preposition or subordinating conjunction";
		else if (tag.equals("JJ"))
			return "Adjective";
		else if (tag.equals("JJR"))
			return "Adjective, comparative";
		else if (tag.equals("JJS"))
			return "Adjective, superlative";
		else if (tag.equals("LS"))
			return "List item marker";
		else if (tag.equals("MD"))
			return "Modal";
		else if (tag.equals("NN"))
			return "Noun, singular or mass";
		else if (tag.equals("NNS"))
			return "Noun, plural";
		else if (tag.equals("NNP"))
			return "Proper noun, singular";
		else if (tag.equals("NNPS"))
			return "Proper noun, plural";
		else if (tag.equals("PDT"))
			return "Predeterminer";
		else if (tag.equals("POS"))
			return "Possessive ending";
		else if (tag.equals("PRP"))
			return "Personal pronoun";
		else if (tag.equals("PRP$"))
			return "Possessive pronoun";
		else if (tag.equals("RB"))
			return "Adverb";
		else if (tag.equals("RBR"))
			return "Adverb, comparative";
		else if (tag.equals("RBC"))
			return "Adverb, comparative";
		else if (tag.equals("RBS"))
			return "Adverb, superlative";
		else if (tag.equals("RP"))
			return "Particle";
		else if (tag.equals("SYM"))
			return "Symbol";
		else if (tag.equals("TO"))
			return "to";
		else if (tag.equals("UH"))
			return "Interjection";
		else if (tag.equals("VB"))
			return "Verb, base form";
		else if (tag.equals("VBD"))
			return "Verb, past tense";
		else if (tag.equals("VBG"))
			return "Verb, gerund or present participle";
		else if (tag.equals("VBN"))
			return "Verb, past participle";
		else if (tag.equals("VBP"))
			return "Verb, non­3rd person singular present";
		else if (tag.equals("VBZ"))
			return "Verb, 3rd person singular present";
		else if (tag.equals("WDT"))
			return "Wh­determiner";
		else if (tag.equals("WP"))
			return "Wh­pronoun";
		else if (tag.equals("WP$"))
			return "Possessive wh­pronoun";
		else if (tag.equals("WRB"))
			return "Wh­adverb";
		else if (tag.equals("-LRB-"))
			return "Left round bracket";
		else if (tag.equals("-RRB-"))
			return "Right round bracket";
		else if (tag.equals("-LSB-"))
			return "Left square bracket";
		else if (tag.equals("-RSB-"))
			return "Right square bracket";
		else if (tag.equals("-LCB-"))
			return "Left curly bracket";
		else if (tag.equals("-RCB-"))
			return "Right curly bracket";
		else
			return "Special (" + tag + ")";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Set<String>> map = new TreeMap<>();
		Paraphernalia para = Factory.get(Locale.ENGLISH);
		PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sentence");
		try {
			Path path = Paths.get("/Users/stefano/Documents/workspace/bristol/Semantic/pg1661.txt");
			String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			perfMon.startAndPrintThroughput();
			String[] sentences = para.detect(text);
			for (String sentence : sentences) {
				String tokens[] = para.tokenize(sentence);
				String[] tags = para.tag(tokens);
				String[] chunks = para.chunk(tokens, tags);
				for (int i = 0; i < chunks.length; i++)
					if (chunks[i].endsWith("NP")) {
						Set<String> set = map.get(tags[i]);
						if (null == set) {
							set = new TreeSet<>();
							map.put(tags[i], set);
						}
						set.add(tokens[i].toLowerCase());
					}
				perfMon.incrementCounter();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		perfMon.stopAndPrintFinalResult();
		System.err.println();
		for (String tag : map.keySet()) {
			System.err.println("* " + tag + ": " + description(tag));
			System.err.println("   " + String.join(", ", map.get(tag)));
		}
		System.err.println("Tags: " + map.keySet());
		System.err.println();
		System.err.println("Done.");
	}
}
