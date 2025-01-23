package com.algafoods.service;

import org.springframework.stereotype.Component;

import com.algafoods.model.Cliente;
import com.algafoods.notificacao.Notificador;

@Component
public class AtivacaoClienteService {
	
	private Notificador notificador;
	
	public AtivacaoClienteService(Notificador notificador) {
		this.notificador = notificador;
	}

	public void ativar(Cliente cliente) {
		cliente.ativar();
				
		notificador.notificar(cliente, "seu cadastro foi realizado com sucesso");
	}

}
