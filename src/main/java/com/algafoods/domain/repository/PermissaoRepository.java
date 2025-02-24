package com.algafoods.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafoods.domain.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long>{
	
}
