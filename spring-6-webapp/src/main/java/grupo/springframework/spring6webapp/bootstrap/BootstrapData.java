package grupo.springframework.spring6webapp.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import grupo.springframework.spring6webapp.domain.Author;
import grupo.springframework.spring6webapp.domain.Book;
import grupo.springframework.spring6webapp.domain.Publisher;
import grupo.springframework.spring6webapp.repositories.AuthorRepository;
import grupo.springframework.spring6webapp.repositories.BookRepository;
import grupo.springframework.spring6webapp.repositories.PublisherRepository;
import jakarta.transaction.Transactional;

@Component
public class BootstrapData implements CommandLineRunner {

	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;
	private final PublisherRepository publisherRepository;

	public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository,
			PublisherRepository publisherRepository) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
		this.publisherRepository = publisherRepository;
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		Author eric = new Author();
		eric.setFirstName("Eric");
		eric.setLastName("Evans");

		Book ddd = new Book();
		ddd.setTitle("Domain Driven Design");
		ddd.setIsbn("123456");

		Author ericSaved = authorRepository.save(eric);
		Book dddSaved = bookRepository.save(ddd);

		Author rod = new Author();
		rod.setFirstName("Rod");
		rod.setLastName("Johnson");

		Book noEJB = new Book();
		noEJB.setTitle("J2EE Development without EJB");
		noEJB.setIsbn("54757585");

		Author rodSaved = authorRepository.save(rod);
		Book noEJBSaved = bookRepository.save(noEJB);

		ericSaved.getBooks().add(dddSaved);
		rodSaved.getBooks().add(noEJBSaved);
        dddSaved.getAuthors().add(ericSaved);
        noEJBSaved.getAuthors().add(rodSaved);

		Publisher publisher = new Publisher();
		publisher.setPublisherName("My Publisher");
		publisher.setAddress("123 Main");
		Publisher savedPublisher = publisherRepository.save(publisher);

		dddSaved.setPublisher(savedPublisher);
		noEJBSaved.setPublisher(savedPublisher);

		Author authorSaved = authorRepository.save(ericSaved);
		Author authorSaved2 = authorRepository.save(rodSaved);
		Book bookSaved = bookRepository.save(dddSaved);
		Book bookSaved2 = bookRepository.save(noEJBSaved);

		System.out.println("In Bootstrap");
		System.out.println("Author Count: " + authorRepository.count());
		System.out.println("Book Count: " + bookRepository.count());
		System.out.println("Publisher Count: " + publisherRepository.count());
		System.out.println(authorSaved);
		System.out.println(authorSaved2);
		System.out.println(bookSaved);
		System.out.println(bookSaved2);
	}

}
