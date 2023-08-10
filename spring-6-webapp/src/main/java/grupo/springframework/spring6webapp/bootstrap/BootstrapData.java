package grupo.springframework.spring6webapp.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import grupo.springframework.spring6webapp.domain.Author;
import grupo.springframework.spring6webapp.domain.Book;
import grupo.springframework.spring6webapp.domain.Publisher;
import grupo.springframework.spring6webapp.repositories.AuthorRepository;
import grupo.springframework.spring6webapp.repositories.BookRepository;
import grupo.springframework.spring6webapp.repositories.PublisherRepository;

@Component
public class BootstrapData implements CommandLineRunner {

	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;
	private final PublisherRepository publisherRepository;

	public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

	@Override
	public void run(String... args) throws Exception {
		Author eric = new Author();
		eric.setFirstName("Eric");
		eric.setLastName("Evans");
		System.out.println(eric); // Author [id=null, firstName=Eric, lastName=Evans, books=null]

		Book ddd = new Book();
		ddd.setTitle("Domain Driven Design");
		ddd.setIsbn("123456");
		System.out.println(ddd); // Book [id=null, title=Domain Driven Design, isbn=123456, authors=null]

		Author ericSaved = authorRepository.save(eric);
		Book dddSaved = bookRepository.save(ddd);
		System.out.println(ericSaved); // Author [id=1, firstName=Eric, lastName=Evans, books=null]
		System.out.println(dddSaved); // Book [id=1, title=Domain Driven Design, isbn=123456, authors=null]

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
		System.out.println(ericSaved); // Author [id=1, firstName=Eric, lastName=Evans, books=[Book [id=1, title=Domain
										// Driven Design, isbn=123456, authors=[]]]]
		System.out.println(rodSaved); // Author [id=2, firstName=Rod, lastName=Johnson, books=[Book [id=2, title=J2EE
										// Development without EJB, isbn=54757585, authors=[]]]]

		System.out.println("In Bootstrap");
		System.out.println("Author Count: " + authorRepository.count()); // 2
		System.out.println("Book Count: " + bookRepository.count()); // 2
		
        Publisher publisher = new Publisher();
        publisher.setPublisherName("Sample Publisher");
        publisher.setAddress("123 Main St");
        publisher.setCity("Springfield");
        publisher.setState("IL");
        publisher.setZip("12345");
        publisherRepository.save(publisher);

        System.out.println("Number of Publishers: " + publisherRepository.count());
	}

}
