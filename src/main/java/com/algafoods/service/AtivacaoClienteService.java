package com.algafoods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafoods.model.Cliente;
import com.algafoods.notificacao.NivelUrgencia;
import com.algafoods.notificacao.Notificador;
import com.algafoods.notificacao.TipoDoNotificador;

@Component
public class AtivacaoClienteService {
	
	@Autowired
	@TipoDoNotificador(NivelUrgencia.URGENTE)
	private Notificador notificador;

	public void ativar(Cliente cliente) {
		cliente.ativar();
				
		notificador.notificar(cliente, "seu cadastro foi realizado com sucesso");
		
	}

}
