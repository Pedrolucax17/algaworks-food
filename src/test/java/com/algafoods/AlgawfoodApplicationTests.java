package com.algafoods;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import com.algafoods.domain.exception.CozinhaNaoEncontradaException;
import com.algafoods.domain.exception.EntidadeEmUsoException;
import com.algafoods.domain.model.Cozinha;
import com.algafoods.domain.repository.CozinhaRepository;
import com.algafoods.domain.service.CozinhaService;
import com.algafoods.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class AlgawfoodApplicationTests {

	@Autowired
	private CozinhaService cozinhaService;

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		RestAssured.basePath = "/cozinhas";
		RestAssured.port=port;

		databaseCleaner.clearTables();
		
		prepararDados();
	}

	@Test
	public void testarCadastroCozinhaComSucesso() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Chinesa");

		cozinha = cozinhaService.salvar(cozinha);

		assertThat(cozinha).isNotNull();
		assertThat(cozinha.getId()).isNotNull();
	}

	@Test
	public void deveRetornarStatus200QuandoConsultarCozinhas() {
		RestAssured
		 .given()
		 	.accept(ContentType.JSON)
		 .when()
		 	.get()
		 .then()
		 	.statusCode(200);
	}

	@Test
	public void testarCadastroCozinhaSemNome() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(null);

		ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
			cozinhaService.salvar(cozinha);
		});

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalharQuandoExcluirCozinhaEmUso() {
		EntidadeEmUsoException erroEsperado = Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
			cozinhaService.remover(1L);
		});

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalharQuandoExcluirCozinhaInexistente() {
		CozinhaNaoEncontradaException erroEsperado = Assertions.assertThrows(CozinhaNaoEncontradaException.class,
				() -> {
					cozinhaService.remover(100L);
				});

		assertThat(erroEsperado).isNotNull();
	}
	
	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);

		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);
	}

}
