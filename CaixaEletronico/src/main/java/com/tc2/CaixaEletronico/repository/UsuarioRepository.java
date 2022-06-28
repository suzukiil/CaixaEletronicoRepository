package com.tc2.CaixaEletronico.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tc2.CaixaEletronico.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	
	@Query(value="select * from usuario where login = :login, and senha = :senha", nativeQuery = true)
	public Usuario Login(String login, String senha);
}
