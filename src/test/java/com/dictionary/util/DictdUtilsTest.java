package com.dictionary.util;

import com.dictionary.Synonym;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(BlockJUnit4ClassRunner.class)
public class DictdUtilsTest {

	private StringBuilder stringBuilderResultForWord;

	@Before
	public void setUp() throws Exception {
		stringBuilderResultForWord = new StringBuilder();
		stringBuilderResultForWord.append("    Bible oath, Parthian shot, account, acquaintance, adage, address,\n")
				.append("    admission, advice, affidavit, affirmance, affirmation, allegation, \n")
				.append("    altercation, ana, analects, announcement, annunciation, answer,");

	}


	@Test
	public void testParseDictdSynonyms_withNullArguments_returnEmptyCollection() throws Exception {
		assertTrue(DictdUtils.parseDictdSynonyms(null).isEmpty());
	}

	@Test
	public void testParseDictdSynonyms_success() throws Exception {
		List<Synonym> result = DictdUtils.parseDictdSynonyms(stringBuilderResultForWord.toString());
		assertEquals(result.size(), 18);
	}

	@Test
	public void testMatchesSynonymOutput_withEmptyString_returnsFalse() {
		assertFalse(DictdUtils.matchesSynonymOutput(""));
	}

	@Test
	public void testMatchesSynonymOutput_withNumericCode_returnsFalse() {
		String output = "220 florida dictd 1.12.0/rf on Linux 3.2.0-23-generic <auth.mime> <55.4753.1423292321@florida>";
		assertFalse(DictdUtils.matchesSynonymOutput(output));
	}

	@Test
	public void testMatchesSynonymOutput_withSmallNumericCode_returnsFalse() {
		String output = "22 Moby Thesaurus words for \"zone\":";
		assertFalse(DictdUtils.matchesSynonymOutput(output));
	}

	@Test
	public void testMatchesSynonymOutput_withSynonymOutput_returnsTrue() {
		String output = "	area, bailiwick, belt, circle, department, district, domain,";
		assertTrue(DictdUtils.matchesSynonymOutput(output));
	}

	@Test
	public void testMatchesSynonymOutput_withDot_returnsFalse() {
		String output = ".";
		assertFalse(DictdUtils.matchesSynonymOutput(output));
	}

}