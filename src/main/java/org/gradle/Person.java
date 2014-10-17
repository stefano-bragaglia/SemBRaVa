package org.gradle;

import org.apache.commons.collections4.list.GrowthList;

public class Person {
	
	private final String name;

	public Person(String name) {
		this.name = name;
		new GrowthList<String>();
	}

	public String getName() {
		return name;
	}
	
}
