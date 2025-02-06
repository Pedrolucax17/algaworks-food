package com.algafoods.jpa;

import java.util.List;

import org.springframework.stereotype.Component;

import com.algafoods.domain.model.Cozinha;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Component
public class CrudCozinha {
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<Cozinha> listar(){
		return manager.createQuery("From Cozinha", Cozinha.class).getResultList();
	}
	
	@Transactional
	public Cozinha adicionar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}
	
	public Cozinha buscarPorId(Long id) {
		return manager.find(Cozinha.class, id);
	}
 
}
