package com.algafoods.main;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algafoods.AlgawfoodApplication;
import com.algafoods.domain.model.Cozinha;
import com.algafoods.domain.model.Restaurante;
import com.algafoods.domain.repository.CozinhaRepository;
import com.algafoods.domain.repository.RestauranteRepository;

public class Main {
	
	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgawfoodApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository repository = applicationContext.getBean(CozinhaRepository.class);
		
		List<Cozinha> cozinhas = repository.listar();
//		
//		for(Cozinha c : cozinhas) {
//			System.out.println(c.getNome());
//		}
//		
//		Cozinha cozinha = new Cozinha();
//		cozinha.setNome("Chinesa");
//		
//		repository.salvar(cozinha);
		
		RestauranteRepository repoRestaurante = applicationContext.getBean(RestauranteRepository.class);
		
		List<Restaurante> restaurantes = repoRestaurante.listar();
		
		for(Restaurante r : restaurantes) {
			System.out.println(r.getNome());
		}
	
		System.out.println(repoRestaurante.buscar(1L).getNome());
		
		Restaurante danda = new Restaurante();
		danda.setNome("Danda");
		danda.setTaxaFrete(new BigDecimal(5.0));
		danda.setCozinha(cozinhas.get(0));
		
		repoRestaurante.salvar(danda);
		
	}

}
