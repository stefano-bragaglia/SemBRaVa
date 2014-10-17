/**
 * 
 */
package org.gradle;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.gradle.core.flexi.Chunk;
import org.gradle.core.flexi.Document;
import org.gradle.core.flexi.Factory;
import org.gradle.core.flexi.Sentence;

/**
 * @author stefano
 *
 */
public class Service {

	public static final String TEXT = "  Skip. \nA noisy sentence. Another   noisy   sentence.\nEverything I'll do, is on my brother's behalf. \nStill another noisy sentence.";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Document document = Factory.getDocument(Locale.ENGLISH, TEXT);
		Document document = Factory.getDocument(Locale.ENGLISH, "pg1661.txt", StandardCharsets.UTF_8);

		Set<String> intjs = new TreeSet<>();
		Set<String> lsts = new TreeSet<>();
		Set<String> pps = new TreeSet<>();
		Set<String> prts = new TreeSet<>();
		Set<String> sbars = new TreeSet<>();

		Set<String> types = new TreeSet<>();
		for (Sentence sentence : document) {
			// System.out.println(sentence);
			for (Chunk chunk : sentence) {
				types.add(chunk.getTag().getSymbol());
				if ("INTJ".equals(chunk.getTag().getSymbol()))
					intjs.add(chunk.getText());
				if ("LST".equals(chunk.getTag().getSymbol()))
					lsts.add(chunk.getText());
				if ("PP".equals(chunk.getTag().getSymbol()))
					pps.add(chunk.getText());
				if ("PRT".equals(chunk.getTag().getSymbol()))
					prts.add(chunk.getText());
				if ("SBAR".equals(chunk.getTag().getSymbol()))
					sbars.add(chunk.getText());

				// System.out.println("\t" + chunk);
				// for (Token token : chunk) {
				// System.out.println("\t\t" + token);
				// }
			}
		}
		System.out.println("Types:\t" + types);
		System.out.println("Intjs:\t" + intjs);
		System.out.println("Lsts:\t" + lsts);
		System.out.println("Pps:\t" + pps);
		System.out.println("Prts:\t" + prts);
		System.out.println("Sbars:\t" + sbars);

	}

}
