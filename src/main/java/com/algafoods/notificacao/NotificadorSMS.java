package com.algafoods.notificacao;

import com.algafoods.model.Cliente;

public class NotificadorSMS implements Notificador{

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s através do telefone %s: %s", cliente.getNome(), cliente.getTelefone(), mensagem);
	}
	
}
