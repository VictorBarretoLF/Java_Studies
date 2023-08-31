package grupo.springframework.spring6webapp.services;

import grupo.springframework.spring6webapp.domain.Author;

public interface AuthorService {

	Iterable<Author> findAll();

}
