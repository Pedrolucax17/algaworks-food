package com.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafoods.api.model.dto.EstadoRegistroDTO;
import com.algafoods.domain.model.Estado;

@Component
public class EstadoInputDisassembler {
	@Autowired
	private ModelMapper mapper;

	public Estado toDomainModel(EstadoRegistroDTO estadoRegistro) {
		return mapper.map(estadoRegistro, Estado.class);
	}

	public void copyToDomainObject(EstadoRegistroDTO estadoInput, Estado estado) {
		mapper.map(estadoInput, estado);
	}
}
