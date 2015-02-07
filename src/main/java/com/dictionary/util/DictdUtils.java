package com.dictionary.util;

import com.dictionary.Synonym;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Author: rocio
 */
public class DictdUtils {

	private DictdUtils() {
	}

	/**
	 * Returns a list of synonyms from a comma separated string
	 *
	 * @param string
	 * @return
	 */
	public static List<Synonym> parseDictdSynonyms(String string) {
		final List<Synonym> synonyms = Lists.newArrayList();
		if (isNotBlank(string)) {
			List<String> matches = Arrays.asList(StringUtils.split(string, ","));
			for (String match : matches) {
				synonyms.add(new Synonym(match.trim()));
			}
		}
		return synonyms;
	}

	/**
	 * Returns true if the line represents a line for synonyms in the dict output.
	 *
	 * @param output
	 * @return
	 */
	public static boolean matchesSynonymOutput(final String output) {
		return isNotBlank(output) && !output.matches("\\d+ .*") && !output.startsWith(".");
	}

}
