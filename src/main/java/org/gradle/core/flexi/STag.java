/**
 * 
 */
package org.gradle.core.flexi;

/**
 * @author stefano
 *
 */
public enum STag {
	
	ADJP ("ADJP", "Adjective phrase"),
	ADVP ("ADVP", "Adverb phrase"),
	CONJP ("CONJP", "Conjunctive phrase (?)"),
	DP ("DP", "Determinative phrase"),
	INTJ ("INTJ", "Interjection (?)"),
	LST ("LST", "List number"),
	NP ("NP", "Noun phrase"),
	PP ("PP", "Preposition phrase"),
	PRT ("PRT", "Preposition (?)"),
	SBAR ("SBAR", "-really no idea- (?)"),
	VP ("VP", "Verb phrase"),
	UNKNOWN ("UNKNOWN", "Unknown");

	public static STag decode(String symbol) {
		if (null == symbol || (symbol=symbol.trim()).isEmpty())
			return STag.UNKNOWN;
		else switch(symbol){
			case "ADJP": return STag.ADJP;
			case "ADVP": return STag.ADVP;
			case "CONJP": return STag.CONJP;
			case "DP": return STag.DP;
			case "INTJ": return STag.INTJ;
			case "LST": return STag.LST;
			case "NP": return STag.NP;
			case "PP": return STag.PP;
			case "PRT": return STag.PRT;
			case "SBAR": return STag.SBAR;
			case "VP": return STag.VP;
			default: return STag.UNKNOWN;
		}
	}
	
	public static boolean isValid(String symbol) {
		return decode(symbol) != STag.UNKNOWN; 
	} 
	
	private String definition;
	
	private String symbol;
	
	private STag(String symbol, String definition) {
		this.symbol = symbol;
		this.definition = definition;
	}
	
	public String getDefinition() {
		return definition;
	}

	public String getSymbol() {
		return symbol;
	}	

}
