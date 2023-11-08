package guru.springframework.sdjpaintro.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;

@Profile({"local", "default"})
@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        bookRepository.deleteAll();

        // Array of generated books
        Book[] generatedBooks = new Book[]{
            new Book("Optional interactive capability", "9781759084046", "Mack Group"),
            new Book("Automated exuding strategy", "9780499517166", "Glass, Smith and Matthews"),
            new Book("Switchable local benchmark", "9781609557867", "Owen Ltd"),
            new Book("Operative fault-tolerant policy", "9780326854945", "Edwards-Reed"),
            new Book("Enhanced disintermediate utilization", "9780053881993", "Mitchell, Pratt and Grant"),
            new Book("Function-based interactive Internet solution", "9781034741336", "Johnson, Mccarty and Weber"),
            new Book("Progressive non-volatile solution", "9780041858617", "Davis, Kent and Mann"),
            new Book("Upgradable non-volatile analyzer", "9780854438907", "Gallagher-Love"),
            new Book("Function-based disintermediate moderator", "9781880326442", "Bailey and Sons"),
            new Book("Organized multi-state moderator", "9780554391526", "Wood-Riley")
        };

        // Save each generated book to the repository
        for (Book book : generatedBooks) {
            bookRepository.save(book);
        }

        // Print out all books in the repository
        bookRepository.findAll().forEach(book -> {
            System.out.println("Book Id: " + book.getId());
            System.out.println("Book Title: " + book.getTitle());
        });
    }
}
