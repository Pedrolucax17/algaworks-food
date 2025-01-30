package com.algafoods.notificacao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.algafoods.model.Cliente;

@Component
@TipoDoNotificador(NivelUrgencia.NORMAL)
public class NotificadorSMS implements Notificador{

	public NotificadorSMS() {
		System.out.println("Construtor do notificador sms chamado");
	}

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s através do sms %s: %s \n", cliente.getNome(), cliente.getTelefone(), mensagem);
	}
	
}
