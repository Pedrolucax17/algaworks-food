package com.algafoods.api.exceptionhandler;

import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algafoods.domain.exception.EntidadeEmUsoException;
import com.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algafoods.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Throwable causeRoot = ExceptionUtils.getRootCause(ex);

		if (causeRoot instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) causeRoot, headers, (HttpStatus) status,
					request);
		}

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREEENSIVEL;
		String detail = "O corpo da requisição está inválido, verifique erro de sintaxe";

		Problem problem = createProblemBuilder((HttpStatus) status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREEENSIVEL;
		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problem problem = createProblemBuilder((HttpStatus) status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e,
			WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;

		Problem problem = createProblemBuilder(status, problemType, e.getMessage()).build();

		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);

	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;

		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;

		Problem problem = createProblemBuilder(status, problemType, e.getMessage()).build();

		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		ProblemType problemType = ProblemType.NEGOCIO_EXCEPTION;

		Problem problem = createProblemBuilder(status, problemType, e.getMessage()).build();

		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {

		String reasonPhrase = (status instanceof HttpStatus httpStatus) ? httpStatus.getReasonPhrase() : "Erro";

		if (body == null) {
			body = Problem.builder().status(status.value()).title(reasonPhrase).build();
		} else if (body instanceof String) {
			body = Problem.builder().title((String) body).status(status.value()).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {

		return Problem.builder().status(status.value()).title(problemType.getTitle()).type(problemType.getUri())
				.details(detail);
	}

}
