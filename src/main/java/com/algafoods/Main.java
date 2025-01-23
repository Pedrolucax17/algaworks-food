package com.algafoods;

import com.algafoods.model.Cliente;
import com.algafoods.notificacao.Notificador;
import com.algafoods.notificacao.NotificadorEmail;
import com.algafoods.notificacao.NotificadorSMS;
import com.algafoods.service.AtivacaoClienteService;

public class Main {
	public static void main(String[] args) {
		
		Cliente cliente = new Cliente("Carol", "carol@gmail.com", "249999999");
		
		AtivacaoClienteService ativacaoClienteService = new AtivacaoClienteService();
		
		ativacaoClienteService.ativar(cliente);
		
		
	}

}
