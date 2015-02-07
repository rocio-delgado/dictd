package com.dictionary;

/**
 * Author: rocio
 */
public class Synonym {

	private final String name;

	public Synonym(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Synonym{" +
				"name='" + name + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Synonym)) {
			return false;
		}

		Synonym synonym = (Synonym) o;
		return name.equals(synonym.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
