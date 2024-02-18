package jdev.mentoria.lojavirtual.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import jdev.mentoria.lojavirtual.service.AcessoService;

@Controller
@RestController
public class AcessoController {
	
	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
    @PersistenceContext
    private EntityManager entityManager;
	
	@ResponseBody
	@PostMapping(value = "/salvarAcesso")
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) { 
		

		Acesso acessoSalvo = acessoService.save(acesso);
		
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}
	
	@GetMapping(value = "/listarAcessos")
	public ResponseEntity<List<Acesso>> listarAcessos(@RequestParam(required = false) String search) {
	    List<Acesso> acessos;

	    if (search != null && !search.isEmpty()) {
	        // Use native SQL query with LIKE operator for partial matching
	        Query query = entityManager.createNativeQuery(
	            "SELECT * FROM acesso WHERE descricao LIKE :search", Acesso.class);
	        query.setParameter("search", "%" + search + "%"); // Adding % for partial match
	        acessos = query.getResultList();
	    } else {
	        acessos = acessoRepository.findAll();
	    }
	    return new ResponseEntity<>(acessos, HttpStatus.OK);
	}

}
