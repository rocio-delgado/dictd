package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: rocio
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SynonymNotFound extends RuntimeException {

	public SynonymNotFound() {
	}

	public SynonymNotFound(String word) {
		super(String.format("Unable to find synonyms for the word %s", word));
	}
}
