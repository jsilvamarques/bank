package br.com.impacta.bank.authorizationserver.repository;

import br.com.impacta.bank.authorizationserver.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String>{
	
	Authority findByName(String name);
	
}