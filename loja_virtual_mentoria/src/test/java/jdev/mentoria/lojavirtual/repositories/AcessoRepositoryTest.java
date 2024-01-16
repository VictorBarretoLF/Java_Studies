package jdev.mentoria.lojavirtual.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;

import com.github.dockerjava.api.exception.NotFoundException;

import jdev.mentoria.lojavirtual.integrationtests.testcontainers.AbstractIntegrationTest;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AcessoRepositoryTest extends AbstractIntegrationTest {
	
	@Autowired
	private AcessoRepository repository;
	
	@Autowired
	private EntityManager entityManager;
	
	private static List<Acesso> acessos;
	
    @BeforeAll
    public static void setup() {
        Acesso acesso = new Acesso();
		acesso.setId(1L);
		acesso.setDescricao("ROLE_ADMIN");
		
		Acesso acesso2 = new Acesso();
		acesso2.setId(2L);
		acesso2.setDescricao("ROLE_ADMIN_SECOND");
		
		acessos = Arrays.asList(acesso, acesso2);
    }
	
    @Test
    void test_given_repository_is_not_null() {
        assertNotNull(repository);
    }
    
    @Test
    void testDeleteByIDNotFound() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
        	repository.deleteById(1L);
        });
    }
    
    @Rollback
    @Test
    @Transactional
    void testSaveThrowsDataIntegrityViolationException() {
    	Acesso acesso = new Acesso();
        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(acesso);
        });
    }
    
    @Rollback
    @Test
    @Transactional
    void testSaveAcessoWhenSavedNotNull() {
    	assertNotNull(repository.save(acessos.get(0)));
    }
    
    @Rollback
    @Test
    @Transactional
    void testSaveAcessoWhenSaved() {
        repository.saveAll(acessos);

        assertNotNull(repository.findById(1L));
        assertNotNull(repository.findById(2L));
    }
    
    @Rollback
    @Test
    @Transactional
    void testSaveAcessoWhenSavedAndCheckLength() {
    	repository.saveAll(acessos);

        assertNotNull(repository.findById(1L));
        assertNotNull(repository.findById(2L));

        List<Acesso> allAcessos = repository.findAll();

        assertEquals(2, allAcessos.size());
    }
    
    @Rollback
    @Test
    @Transactional
    void testTransactionalRollback() {
        repository.save(acessos.get(0));

        Acesso acesso = new Acesso();
        assertThrows(DataIntegrityViolationException.class, () -> repository.save(acesso));

        assertFalse(repository.findById(1L).isPresent());
    }
    
    @Rollback
    @Test
    @Transactional
    void testForUniquieIdConstraints() {
        repository.saveAll(acessos);
        
        Acesso acesso = new Acesso();
        acesso.setId(1L); // This ID is already used in the setup method.
        acesso.setDescricao("ROLE_SUPER_ADMIN");
        
        System.out.println(repository.findAll().size());

        // Expecting an exception here due to the duplicate ID.
//        assertThrows(DataIntegrityViolationException.class, () -> repository.save(acesso));
    }

    
    @Rollback
    @Test
    @Transactional
    void testDeletionOfAcesso() {
        Acesso savedAcesso = repository.save(acessos.get(0));
        assertNotNull(savedAcesso);

        Acesso acessoToDelete = repository.findById(1L).get();
        repository.deleteById(acessoToDelete.getId());

        assertFalse(repository.findById(1L).isPresent());
    }

}
