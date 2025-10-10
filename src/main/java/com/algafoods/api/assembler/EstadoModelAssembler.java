package com.algafoods.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafoods.api.model.dto.EstadoExibicaoDTO;
import com.algafoods.domain.model.Estado;

@Component
public class EstadoModelAssembler {
	@Autowired
	private ModelMapper mapper;
	
	public EstadoExibicaoDTO toModel(Estado estadoSalvo) {
		return mapper.map(estadoSalvo, EstadoExibicaoDTO.class);
	}
	
	public List<EstadoExibicaoDTO> toCollectList(List<Estado> estados) {
		return estados.stream()
				.map(r -> toModel(r))
				.collect(Collectors.toList());
	}
}
