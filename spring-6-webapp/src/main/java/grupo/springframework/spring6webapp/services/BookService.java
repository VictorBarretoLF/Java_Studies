package grupo.springframework.spring6webapp.services;

import grupo.springframework.spring6webapp.domain.Book;

public interface BookService {

	Iterable<Book> findAll();

}