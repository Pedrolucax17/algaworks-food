package com.algafoods.notificacao;

import com.algafoods.model.Cliente;

public class NotificadorEmail implements Notificador{

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s através do email %s: %s", cliente.getNome(), cliente.getEmail(), mensagem);
	}
	
}
