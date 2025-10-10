package com.algafoods.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafoods.api.model.dto.CozinhaExibicaoDTO;
import com.algafoods.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler {
	@Autowired
	private ModelMapper mapper;
	
	public CozinhaExibicaoDTO toModel(Cozinha cozinhaSalva) {
		return mapper.map(cozinhaSalva, CozinhaExibicaoDTO.class);
	}
	
	public List<CozinhaExibicaoDTO> toCollectList(List<Cozinha> cozinhas) {
		return cozinhas.stream()
				.map(r -> toModel(r))
				.collect(Collectors.toList());
	}
}
