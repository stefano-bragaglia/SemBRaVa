/**
 * 
 */
package org.gradle.core.flexi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author stefano
 *
 */
public class Base {

	private Map<String, Map<String, Set<String>>> definitions;

	/**
	 * Default constructor.
	 */
	public Base() {
		this.definitions = new HashMap<>();
		assert invariant() : "Illegal state in Base()";
	}

	public void addFactType(String term, String factType) {
		if (null == term || (term = term.trim()).isEmpty() || !contains(term))
			throw new IllegalArgumentException("Illegal 'term' argument in Base.addFactType(String, String): " + term);
		if (null == factType || (factType = factType.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'factType' argument in Base.addFactType(String, String): " + factType);
		Map<String, Set<String>> properties = definitions.get(term);
		if (null == properties) {
			properties = new HashMap<>();
			definitions.put(term, properties);
		}
		Set<String> objects = properties.get(factType);
		if (null == objects) {
			objects = new HashSet<>();
			properties.put(factType, objects);
		}
		objects.add("<boolean>");
		assert invariant() : "Illegal state in Base.addFactType(String, String)";
	}

	public void addFactType(String term, String factType, String object) {
		if (null == term || (term = term.trim()).isEmpty() || !contains(term))
			throw new IllegalArgumentException("Illegal 'term' argument in Base.addFactType(String, String, String): " + term);
		if (null == factType || (factType = factType.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'factType' argument in Base.addFactType(String, String, String): " + factType);
		if (null == object || (object = object.trim()).isEmpty() || !contains(object))
			throw new IllegalArgumentException("Illegal 'object' argument in Base.addFactType(String, String, String): " + object);
		Map<String, Set<String>> properties = definitions.get(term);
		if (null == properties) {
			properties = new HashMap<>();
			definitions.put(term, properties);
		}
		Set<String> objects = properties.get(factType);
		if (null == objects) {
			objects = new HashSet<>();
			properties.put(factType, objects);
		}
		objects.add(object);
		assert invariant() : "Illegal state in Base.addFactType(String, String, String)";
	}

	public void addTerm(String term) {
		if (null == term || (term = term.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'term' argument in Base.addTerm(String): " + term);
		Map<String, Set<String>> properties = definitions.get(term);
		if (null == properties) {
			properties = new HashMap<>();
			definitions.put(term, properties);
		}
		assert invariant() : "Illegal state in Base.addTerm(String)";
	}

	public boolean contains(String term) {
		if (null == term || (term = term.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'term' argument in Base.contains(String): " + term);
		boolean result = definitions.containsKey(term);
		assert invariant() : "Illegal state in Base.contains(String)";
		return result;
	}
	
	public boolean contains(String term, String verb) {
		if (null == term || (term = term.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'term' argument in Base.contains(String, String): " + term);
		if (null == verb || (verb = verb.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'verb' argument in Base.contains(String, String): " + verb);
		boolean result = definitions.containsKey(term) && definitions.get(term).containsKey(verb);
		assert invariant() : "Illegal state in Base.contains(String, String)";
		return result;
	}

	/**
	 * Invariant check against the internal state.
	 * 
	 * @return <code>true</code> if this instance's state is consistent,
	 *         <code>false</code> otherwise
	 */
	private boolean invariant() {
		return (null != definitions);
	}

	/**
	 * @return
	 */
	public boolean isEmpty() {
		boolean result = definitions.isEmpty();
		assert invariant() : "Illegal state in Base.isEmpty()";
		return result;
	}

	public void removeFactType(String term, String factType) {
		if (null == term || (term = term.trim()).isEmpty() || !contains(term))
			throw new IllegalArgumentException("Illegal 'term' argument in Base.removeFactType(String, String): " + term);
		if (null == factType || (factType = factType.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'factType' argument in Base.removeFactType(String, String): " + factType);
		Map<String, Set<String>> properties = definitions.get(term);
		Set<String> objects = properties.get(factType);
		if (null != objects)
			objects.remove("<boolean>");
		assert invariant() : "Illegal state in Base.removeFactType(String, String)";
	}

	public void removeTerm(String term) {
		if (null == term || (term = term.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'term' argument in Base.removeTerm(String): " + term);
		definitions.remove(term);
		assert invariant() : "Illegal state in Base.removeTerm(String)";
	}

	public void removeType(String term, String factType, String object) {
		if (null == term || (term = term.trim()).isEmpty() || !contains(term))
			throw new IllegalArgumentException("Illegal 'term' argument in Base.removeType(String, String, String): " + term);
		if (null == factType || (factType = factType.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'factType' argument in Base.removeType(String, String, String): " + factType);
		if (null == object || (object = object.trim()).isEmpty())
			throw new IllegalArgumentException("Illegal 'object' argument in Base.removeType(String, String, String): " + object);
		Map<String, Set<String>> properties = definitions.get(term);
		Set<String> objects = properties.get(factType);
		if (null != objects)
			objects.remove(object);
		assert invariant() : "Illegal state in Base.removeType(String, String, String)";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Base(" + definitions + ")";
	}

}
