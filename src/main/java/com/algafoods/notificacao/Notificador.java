package com.algafoods.notificacao;

import com.algafoods.model.Cliente;

public interface Notificador {

	void notificar(Cliente cliente, String mensagem);
	
}
