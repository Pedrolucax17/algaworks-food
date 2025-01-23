package com.algafoods.service;

import com.algafoods.model.Cliente;
import com.algafoods.notificacao.Notificador;

public class AtivacaoClienteService {
	
	private Notificador notificador;
	
	public void ativar(Cliente cliente) {
		cliente.ativar();
				
		notificador.notificar(cliente, "seu cadastro foi realizado com sucesso");
	}

}
