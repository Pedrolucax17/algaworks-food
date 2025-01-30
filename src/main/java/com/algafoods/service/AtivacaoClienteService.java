package com.algafoods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.algafoods.model.Cliente;
import com.algafoods.notificacao.NivelUrgencia;
import com.algafoods.notificacao.Notificador;
import com.algafoods.notificacao.TipoDoNotificador;

@Component
public class AtivacaoClienteService {
	
	@Autowired(required = false)
	@TipoDoNotificador(NivelUrgencia.NORMAL)
	private Notificador notificador;

	public void ativar(Cliente cliente) {
		cliente.ativar();
				
		if(notificador != null) {
			notificador.notificar(cliente, "seu cadastro foi realizado com sucesso");
		}else {
			System.out.println("seu cadastro foi realizado com sucesso, sem notificação");
		}
	}

}
