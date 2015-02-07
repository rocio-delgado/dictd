package com.dictionary.service;

import com.dictionary.Synonym;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.List;

import static com.dictionary.util.DictdUtils.matchesSynonymOutput;
import static com.dictionary.util.DictdUtils.parseDictdSynonyms;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Author: rocio
 */
@Service
public class Dictd implements Dictionary {

	private final static String DICT_DEFINE_COMMAND = "DEFINE moby-thesaurus %s\r\n";
	private final static String HOST = "127.0.0.1";
	private final static int PORT = 2628;
	private final static int SOCKET_TIMEOUT = 15000;

	private final static String OK_CODE = "250";
	private final static String MATCH_NOT_FOUND = "552";
	private final static String SYNTAX_ERROR = "501";


	@Override
	public List<Synonym> getSynonyms(String word) {
		if (isBlank(word)) {
			return Collections.emptyList();
		}

		Socket socket = new Socket();
		SocketAddress socketAddress = new InetSocketAddress(HOST, PORT);
		DataOutputStream outputStream = null;

		try {
			socket.connect(socketAddress);
			socket.setSoTimeout(SOCKET_TIMEOUT);

			//Send request
			outputStream = new DataOutputStream(socket.getOutputStream());
			Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
			writer.write(String.format(DICT_DEFINE_COMMAND, word));
			writer.flush();

			return readResponse(socket);

		} catch (IOException e) {
			//Needs better logging
			e.printStackTrace();
			return Collections.emptyList();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				socket.close();
			} catch (IOException io) {
			}
		}
	}


	List<Synonym> readResponse(Socket socket) throws IOException {
		StringBuilder synonymResultBuilder = new StringBuilder();
		if (socket != null && socket.isConnected()) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
				if (line.startsWith(OK_CODE) || line.startsWith(MATCH_NOT_FOUND) || line.startsWith(SYNTAX_ERROR)) {
					break;
				}
				if (matchesSynonymOutput(line)) {
					synonymResultBuilder.append(line);
				}
			}
		}

		return parseDictdSynonyms(synonymResultBuilder.toString());
	}
}