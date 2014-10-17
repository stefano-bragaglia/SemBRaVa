/**
 * 
 */
package org.gradle.core.flexi;

/**
 * @author stefano
 *
 */
public enum POSTag {

	CC ("CC", "Coordinating conjunction"),
	CD ("CD", "Cardinal number"),
	DT ("DT", "Determiner"),
	EX ("EX", "Existential there"),
	FW ("FW", "Foreign word"),
	IN ("IN", "Preposition or subordinating conjunction"),
	JJ ("JJ", "Adjective"),
	JJR ("JJR", "Adjective, comparative"),
	JJS ("JJS", "Adjective, superlative"),
	LCB ("-LCB-", "Left curly bracket"),
	LRB ("-LRB-", "Left round bracket"),
	LS ("LS", "List item marker"),
	LSB ("-LSB-", "Left square bracket"),
	MD ("MD","Modal"),
	NN ("NN", "Noun, singular or mass"),
	NNP ("NNP","Proper noun, singular"),
	NNPS ("NNPS", "Proper noun, plural"),
	NNS ("NNS", "Noun, plural"),
	PDT ("PDT", "Predeterminer"),
	POS ("POS", "Possessive ending"),
	PRP ("PRP", "Personal pronoun"),
	PRP$ ("PRP$", "Possessive pronoun"),
	RB ("RB", "Adverb"),
	RBR ("RBR", "Adverb, comparative"),
	RBS ("RBS", "Adverb, superlative"),
	RCB ("-RCB-", "Right curly bracket"),
	RP ("RP", "Particle"),
	RRB ("-RRB-", "Right round bracket"),
	RSB ("-RSB-", "Right square bracket"),
	SYM ("SYM", "Symbol"),
	TO ("TO", "to"),
	UH ("UH", "Interjection"),
	VB ("VB", "Verb, base form"),
	VBD ("VBD", "Verb, past tense"),
	VBG ("VBG", "Verb, gerund or present participle"),
	VBN ("VBN", "Verb, past participle"),
	VBP ("VBP", "Verb, non­3rd person singular present"),
	VBZ ("VBZ", "Verb, 3rd person singular present"),
	WDT ("WDT", "Wh-­determiner"),
	WP ("WP", "Wh-­pronoun"),
	WP$ ("WP$", "Possessive wh-­pronoun"),
	WRB ("WRB", "Wh-­adverb"),
	UNKNOWN ("UNKNOWN", "Unknown");

	public static POSTag decode(String symbol) {
		if (null == symbol || (symbol=symbol.trim()).isEmpty())
			return POSTag.UNKNOWN;
		else switch(symbol){
			case "CC": return POSTag.CC;
			case "CD": return POSTag.CD;
			case "DT": return POSTag.DT;
			case "EX": return POSTag.EX;
			case "FW": return POSTag.FW;
			case "IN": return POSTag.IN;
			case "JJ": return POSTag.JJ;
			case "JJR": return POSTag.JJR;
			case "JJS": return POSTag.JJS;
			case "-LCB-": return POSTag.LCB;
			case "-LRB-": return POSTag.LRB;
			case "LS": return POSTag.LS;
			case "-LSB-": return POSTag.LSB;			
			case "MD": return POSTag.MD;
			case "NN": return POSTag.NN;
			case "NNP": return POSTag.NNP;
			case "NNPS": return POSTag.NNPS;
			case "NNS": return POSTag.NNS;
			case "PDT": return POSTag.PDT;
			case "POS": return POSTag.POS;
			case "PRP": return POSTag.PRP;
			case "PRP$": return POSTag.PRP$;
			case "RB": return POSTag.RB;
			case "RBR": return POSTag.RBR;
			case "RBS": return POSTag.RBS;
			case "-RCB-": return POSTag.RCB;
			case "RP": return POSTag.RP;
			case "-RRB-": return POSTag.RRB;
			case "-RSB-": return POSTag.RSB;
			case ".": case ",":
			case "SYM": return POSTag.SYM;
			case "TO": return POSTag.TO;
			case "UH": return POSTag.UH;
			case "VB": return POSTag.VB;
			case "VBD": return POSTag.VBD;
			case "VBG": return POSTag.VBG;
			case "VBN": return POSTag.VBN;
			case "VBP": return POSTag.VBP;
			case "VBZ": return POSTag.VBZ;
			case "WDT": return POSTag.WDT;
			case "WP": return POSTag.WP;
			case "WP$": return POSTag.WP$;
			case "WRB": return POSTag.WRB;
			default: return POSTag.UNKNOWN;
		}
	}
	
	public static boolean isValid(String symbol) {
		return decode(symbol) != POSTag.UNKNOWN; 
	} 
	
	private String definition;
	
	private String symbol;
	
	private POSTag(String symbol, String definition) {
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
