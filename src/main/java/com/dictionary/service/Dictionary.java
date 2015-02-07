package com.dictionary.service;

import com.dictionary.Synonym;

import java.util.List;

/**
 * Author: rocio
 */
public interface Dictionary {

	List<Synonym> getSynonyms(String word);
}
