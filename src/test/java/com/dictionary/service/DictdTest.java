package com.dictionary.service;

import com.dictionary.Synonym;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.net.Socket;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DictdTest {

	@InjectMocks Dictd dictd;


	private StringBuilder resultForWord;
	private StringBuilder resultForZone;
	private StringBuilder resultForBogusWord;
	private StringBuilder resultForWordWithSyntaxError;
	private Socket socket;
	private InputStream inputStream;

	@Before
	public void setUp() throws Exception {
		resultForWord = new StringBuilder();
		resultForWord.append("220 florida dictd 1.12.0/rf on Linux 3.2.0-23-generic <auth.mime> <55.4753.1423292321@florida> \n")
				.append("150 1 definitions retrieved \n")
				.append("151 \"word\" moby-thesaurus \"Moby Thesaurus II by Grady Ward, 1.0\" \n")
				.append("331 Moby Thesaurus words for \"word\": \n")
				.append("    Bible oath, Parthian shot, account, acquaintance, adage, address,\n")
				.append("    admission, advice, affidavit, affirmance, affirmation, allegation, \n")
				.append("    altercation, ana, analects, announcement, annunciation, answer, \n")
				.append("							\n")
				.append("							\n")
				.append(".\n")
				.append("250 ok [d/m/c = 1/0/8; 0.000r 0.000u 0.000s] \n");

		resultForZone = new StringBuilder();
		resultForZone.append("220 florida dictd 1.12.0/rf on Linux 3.2.0-23-generic <auth.mime> <55.4753.1423292321@florida> \n")
				.append("150 1 definitions retrieved \n")
				.append("151 \"zone\" moby-thesaurus \"Moby Thesaurus II by Grady Ward, 1.0\" \n")
				.append("22 Moby Thesaurus words for \"zone\": \n")
				.append("	area, bailiwick, belt, circle, department, district, domain,\n")
				.append("	locale, locality, precinct, province, quarter, realm, region,\n")
				.append("							\n")
				.append("							\n")
				.append(".\n")
				.append("250 ok [d/m/c = 1/0/8; 0.000r 0.000u 0.000s] \n");

		resultForBogusWord = new StringBuilder();
		resultForBogusWord.append("220 florida dictd 1.12.0/rf on Linux 3.2.0-23-generic <auth.mime> <55.4753.1423292321@florida>\n")
				.append("552 no match [d/m/c = 0/0/12; 0.000r 0.000u 0.000s]\n");

		resultForWordWithSyntaxError = new StringBuilder();
		resultForWordWithSyntaxError.append("220 florida dictd 1.12.0/rf on Linux 3.2.0-23-generic <auth.mime> <55.4753.1423292321@florida>")
				.append("501 syntax error, illegal parameters");

		socket = mock(Socket.class);
		when(socket.isConnected()).thenReturn(true);
	}

	@Test
	public void testGetSynonyms_withEmptyWord_returnsEmptyCollection() throws Exception {
		assertTrue(dictd.getSynonyms("").isEmpty());
	}

	@Test
	public void testReadResponse_withNullSocket_returnsEmptyCollection() throws Exception {
		assertTrue(dictd.readResponse(null).isEmpty());
	}

	@Test
	public void testReadResponse_withResponseForWord_success() throws Exception {
		inputStream = IOUtils.toInputStream(resultForWord.toString());
		when(socket.getInputStream()).thenReturn(inputStream);
		assertFalse(dictd.readResponse(socket).isEmpty());
	}

	@Test
	public void testReadResponse_withResponseForZone_success() throws Exception {
		inputStream = IOUtils.toInputStream(resultForZone.toString());
		when(socket.getInputStream()).thenReturn(inputStream);
		assertFalse(dictd.readResponse(socket).isEmpty());
	}

	@Test
	public void testReadResponse_withResponseForWord_containsSynonym() throws Exception {
		inputStream = IOUtils.toInputStream(resultForWord.toString());
		when(socket.getInputStream()).thenReturn(inputStream);
		assertTrue(dictd.readResponse(socket).contains(new Synonym("acquaintance")));
	}

	@Test
	public void testReadResponse_withResponseForZone_containsSynonym() throws Exception {
		inputStream = IOUtils.toInputStream(resultForZone.toString());
		when(socket.getInputStream()).thenReturn(inputStream);
		assertTrue(dictd.readResponse(socket).contains(new Synonym("bailiwick")));
	}


	@Test
	public void testReadResponse_withResponseForBogusWord_returnsEmptyCollection() throws Exception {
		inputStream = IOUtils.toInputStream(resultForBogusWord.toString());
		when(socket.getInputStream()).thenReturn(inputStream);
		assertTrue(dictd.readResponse(socket).isEmpty());
	}

	@Test
	public void testReadResponse_withResponseForSyntaxError_returnsEmptyCollection() throws Exception {
		inputStream = IOUtils.toInputStream(resultForWordWithSyntaxError.toString());
		when(socket.getInputStream()).thenReturn(inputStream);
		assertTrue(dictd.readResponse(socket).isEmpty());
	}


}