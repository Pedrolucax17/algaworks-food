package com.algafoods.notificacao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algafoods.model.Cliente;

@Profile("dev")
@Component
@TipoDoNotificador(NivelUrgencia.URGENTE)
public class NotificadorEmailMock implements Notificador{

	public NotificadorEmailMock() {
		System.out.println("Construtor do notificador email Mock");
	}

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("MOCK: Notificando %s através do email %s: %s \n", cliente.getNome(), cliente.getEmail(), mensagem);
	}
	
}
