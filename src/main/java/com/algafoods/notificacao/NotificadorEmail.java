package com.algafoods.notificacao;

import org.springframework.stereotype.Component;

import com.algafoods.model.Cliente;

@Component
public class NotificadorEmail implements Notificador{

	public NotificadorEmail() {
		System.out.println("Construtor do notificador email chamado");
	}

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s através do email %s: %s", cliente.getNome(), cliente.getEmail(), mensagem);
	}
	
}
