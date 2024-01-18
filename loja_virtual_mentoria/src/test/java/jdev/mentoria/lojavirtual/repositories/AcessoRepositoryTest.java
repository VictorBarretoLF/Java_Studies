package jdev.mentoria.lojavirtual.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

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
	
	private Acesso acesso;
	
	@BeforeEach
	public void setup() {
	    this.acesso = new Acesso();
	    this.acesso.setId(1L);
	    this.acesso.setDescricao("ROLE_ADMIN");
	}

	@Test
	void testRepositorioNaoEhNulo() {
	    assertNotNull(repository);
	}

	@Test
	void testDeletarPorIDNaoEncontrado() {
	    assertThrows(EmptyResultDataAccessException.class, () -> {
	    	repository.deleteById(1L);
	    });
	}

	@Test
	void testSalvarAcesso() {
	    Acesso acessoSalvo = repository.save(acesso);

	    assertNotNull(repository.findById(acessoSalvo.getId()));
	    assertNotNull(acessoSalvo);
	    assertNotNull(acessoSalvo.getId());
	    assertEquals("ROLE_ADMIN", acessoSalvo.getDescricao());
	}

	@Test
	void testDeletarAcesso() {
	    Acesso savedAcesso = repository.save(acesso);

	    repository.deleteById(savedAcesso.getId());

	    Optional<Acesso> deletedAcesso = repository.findById(savedAcesso.getId());
	    assertFalse(deletedAcesso.isPresent());
	}


//    @Test
//    @Rollback(false)
//    @Order(3)
//    void testSaveAcessoWhenSavedNotNull() {
//        repository.save(this.acesso);
//
//        assertTrue(repository.findById(1L).isPresent());
//    }

//    @DisplayName("Deletando um Acesso")
//    @Test
//    @Order(4)
//    void testDeletionOfAcesso() {
//        repository.deleteById(1L);
//
//        assertFalse(repository.findById(1L).isPresent());
//    }
//    
//    @DisplayName("Sucesso ao deletar um Acesso")
//    @Test
//    @Order(5)
//    void testeSucessoAoDeletarAcesso() {
////        assertFalse();
//System.out.println(repository.findById(1L).isPresent());
//    }
//
//    @Test
//    void testSaveThrowsDataIntegrityViolationException() {
//    	Acesso acesso2 = new Acesso();
//        assertThrows(DataIntegrityViolationException.class, () -> {
//            repository.save(acesso2);
//        });
//    }
    
//    @Rollback
//    @Test
//    @Transactional
//    void testSaveAcessoWhenSaved() {
//        repository.saveAll(acessos);
//
//        assertNotNull(repository.findById(1L));
//        assertNotNull(repository.findById(2L));
//    }
//    
//    @Rollback
//    @Test
//    @Transactional
//    void testSaveAcessoWhenSavedAndCheckLength() {
//    	repository.saveAll(acessos);
//
//        assertNotNull(repository.findById(1L));
//        assertNotNull(repository.findById(2L));
//
//        List<Acesso> allAcessos = repository.findAll();
//
//        assertEquals(2, allAcessos.size());
//    }
//    
//    @Rollback
//    @Test
//    @Transactional
//    void testTransactionalRollback() {
//        repository.save(acessos.get(0));
//
//        Acesso acesso = new Acesso();
//        assertThrows(DataIntegrityViolationException.class, () -> repository.save(acesso));
//
//        assertFalse(repository.findById(1L).isPresent());
//    }
//    
//    @Rollback
//    @Test
//    @Transactional
//    void testForUniquieIdConstraints() {
//        repository.saveAll(acessos);
//        
//        Acesso acesso = new Acesso();
//        acesso.setId(1L); // This ID is already used in the setup method.
//        acesso.setDescricao("ROLE_SUPER_ADMIN");
//        
//        System.out.println(repository.findAll().size());
//
//        // Expecting an exception here due to the duplicate ID.
////        assertThrows(DataIntegrityViolationException.class, () -> repository.save(acesso));
//    }
//    


}
