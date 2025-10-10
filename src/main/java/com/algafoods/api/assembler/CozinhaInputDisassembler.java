package com.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafoods.api.model.dto.CozinhaRegistroDTO;
import com.algafoods.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler {
	@Autowired
	private ModelMapper mapper;

	public Cozinha toDomainModel(CozinhaRegistroDTO cozinhaRegistro) {
		return mapper.map(cozinhaRegistro, Cozinha.class);
	}

	public void copyToDomainObject(CozinhaRegistroDTO cozinhaInput, Cozinha cozinha) {
		mapper.map(cozinhaInput, cozinha);
	}
}
