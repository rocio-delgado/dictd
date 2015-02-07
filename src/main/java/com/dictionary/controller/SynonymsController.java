package com.dictionary.controller;

import com.dictionary.Synonym;
import com.dictionary.service.Dictionary;
import exception.SynonymNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Iterables.isEmpty;

/**
 * Author: rocio
 */
@RestController
@RequestMapping("/synonyms")
public class SynonymsController {

	@Autowired Dictionary dictionary;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public Synonym synonym() {
		throw new SynonymNotFound();
	}

	@ResponseBody
	@RequestMapping(value = "/{word}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Synonym> synonym(@PathVariable(value = "word") String word) {
		List<Synonym> result = dictionary.getSynonyms(word);
		if (isEmpty(result)) {
			throw new SynonymNotFound(word);
		}
		return result;
	}

}