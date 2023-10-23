package jdev.mentoria.lojavirtual.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

public class Acesso implements GrantedAuthority {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_acesso")
	private Long id;
	
	private String descricao; /* Acesso ex: ROLE_ADMIN ou ROLE_SECRETARIO */

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.descricao;
	}
	
	
}
