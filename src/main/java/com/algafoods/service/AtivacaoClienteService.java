package com.algafoods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.algafoods.model.Cliente;
import com.algafoods.notificacao.Notificador;

@Component
public class AtivacaoClienteService {
	
	@Autowired(required = false)
	@Qualifier("EMAIL")
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
