package com.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafoods.api.model.dto.CidadeRegistroDTO;
import com.algafoods.domain.model.Cidade;
import com.algafoods.domain.model.Estado;

@Component
public class CidadeInputDisassembler {

	@Autowired
	private ModelMapper mapper;
	
	public Cidade toDomainModel(CidadeRegistroDTO cidadeRegistro) {
		Cidade cidade = new Cidade();
		cidade.setNome(cidadeRegistro.getNome());

		Estado estado = new Estado();
		estado.setId(cidadeRegistro.getEstadoId().getId());

		cidade.setEstado(estado);

		return cidade;
	}

	public void copyToDomainObject(CidadeRegistroDTO cidadeInput, Cidade cidade) {
		cidade.setEstado(new Estado());
		mapper.map(cidadeInput, cidade);
	}
}
