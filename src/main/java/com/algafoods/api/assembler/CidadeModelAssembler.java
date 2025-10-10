package com.algafoods.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafoods.api.model.dto.CidadeExibicaoDTO;
import com.algafoods.domain.model.Cidade;

@Component
public class CidadeModelAssembler {
	@Autowired
	private ModelMapper mapper;
	
	public CidadeExibicaoDTO toModel(Cidade cidadeSalva) {
		return mapper.map(cidadeSalva, CidadeExibicaoDTO.class);
	}
	
	public List<CidadeExibicaoDTO> toCollectList(List<Cidade> cidades) {
		return cidades.stream()
				.map(r -> toModel(r))
				.collect(Collectors.toList());
	}
}
