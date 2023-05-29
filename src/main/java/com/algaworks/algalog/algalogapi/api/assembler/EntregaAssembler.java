package com.algaworks.algalog.algalogapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algalog.algalogapi.api.model.EntregaModel;
import com.algaworks.algalog.algalogapi.api.model.input.EntregaInput;
import com.algaworks.algalog.algalogapi.domain.model.Entrega;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EntregaAssembler {
  private ModelMapper modelMapper;

  // Metodo para não precisar ta repedindo a conversão
  public EntregaModel toModel(Entrega entrega) {
    return modelMapper.map(entrega, EntregaModel.class);
  }

  // metodo para converter caso seja uma lista a retornar
  public List<EntregaModel> toCollectionModel(List<Entrega> entregas) {
    return entregas.stream()
        .map(this::toModel)
        .collect(Collectors.toList());// converte a lista de entrega para uma lista entrega model
  }

  public Entrega toEntity(EntregaInput entregaInput) {
    return modelMapper.map(entregaInput, Entrega.class);
  }
}
