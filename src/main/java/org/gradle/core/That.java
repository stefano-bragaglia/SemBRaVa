/**
 * 
 */
package org.gradle.core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

import org.gradle.core.flexi.Factory;
import org.gradle.core.flexi.Paraphernalia;

/**
 * @author stefano
 *
 */
public class That {

	// public static final String TEXT = "Term: there are days";

	// public static final String TEXT =
	// "Term: a very nice pilot. \n Term: the shiny planes move people and are happy. \n Fact type: a brave pilot can fly planes. \n ";
	// public static final String TEXT =
	// "Term: a very nice pilot. \n Term: the shiny planes. \n Fact type: a brave pilot can eventually fly the planes. \n ";
	// public static final String TEXT =
	// "Term: a very nice pilot. \n Term: the shiny planes. \n Fact type: the very nice pilot can eventually fly the shiny planes. \n ";
	public static final String TEXT = "Term: pilot. Term: planes. Fact type: pilot can fly plane. Fact type: pilot is experienced. Rule: it is obligatory that each pilot can fly at least 1 plane. \n Rule: it is obligatory that each pilot which is experienced can fly at least 3 planes.";

	// public static final String TEXT =
	// "Term: the shiny planes move people and are experienced.";

	// "Term: pilot. \n Term: planes. \n Fact type: a brave pilot can fly planes. \n Fact type: a pilot can be experienced. \n Rule: it is obligatory that each pilot can fly at least 1 plane. \n Rule: it is obligatory that each pilot that is experienced can fly at least 3 planes.";

	public static void main(String[] args) {
		Paraphernalia paraphernalia = Factory.get(Locale.ENGLISH);
		String text = "";
		try {
			text = new String(Files.readAllBytes(Paths.get("/Users/stefano/Documents/workspace/bristol/Semantic/wiki.txt")), StandardCharsets.UTF_8);
			for (String sentence : paraphernalia.detect(text))
				if (sentence.toLowerCase().contains("that"))
					System.out.println("> " + sentence);
		} catch (IOException e) {
			System.out.println("Error!");
		}
		System.err.println("Done.");
	}
}
