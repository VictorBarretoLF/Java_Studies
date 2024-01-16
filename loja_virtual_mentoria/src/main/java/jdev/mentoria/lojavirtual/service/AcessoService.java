package jdev.mentoria.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;

import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;

public class AcessoService {

	@Autowired
	private AcessoRepository acessoRepository;
	
	

	public Acesso save(Acesso acesso) {
		return acessoRepository.save(acesso);
	}

}
