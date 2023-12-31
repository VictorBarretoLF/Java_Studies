package guru.springframework.sdjpaintro;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.sdjpaintro.repositories.BookRepository;

@SpringBootTest
class SdjpaIntroApplicationTests {
	
    @Autowired
    BookRepository bookRepository;
    
    @Test
    void testBookRepository() {
        long count = bookRepository.count();
        
        System.out.println("AQUIIIIII: " + count);

        assertThat(count).isGreaterThan(0);
    }

	@Test
	void contextLoads() {
	}

}
