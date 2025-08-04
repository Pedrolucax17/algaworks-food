package com.algafoods;

import static org.assertj.core.api.Assertions.assertThat;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.algafoods.domain.exception.CozinhaNaoEncontradaException;
import com.algafoods.domain.exception.EntidadeEmUsoException;
import com.algafoods.domain.model.Cozinha;
import com.algafoods.domain.service.CozinhaService;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlgawfoodApplicationTests {

	@Autowired
	private CozinhaService cozinhaService;

	@Autowired
	private Flyway flyway;

	@LocalServerPort
	private int port;

	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		RestAssured.basePath = "/cozinhas";
		RestAssured.port=port;

		flyway.migrate();
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

}
