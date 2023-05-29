package com.algaworks.algalog.algalogapi.domain.service;

import org.springframework.stereotype.Service;

import com.algaworks.algalog.algalogapi.domain.exception.NegocioException;
import com.algaworks.algalog.algalogapi.domain.model.Entrega;
import com.algaworks.algalog.algalogapi.domain.model.Ocorrencia;
import com.algaworks.algalog.algalogapi.domain.repository.EntregaRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegistroOcorrenciaService {

  private BuscaEntregaService buscaEntregaService;

  @Transactional
  public Ocorrencia registrar(Long entregaId, String descricao) {
    Entrega entrega = buscaEntregaService.buscar(entregaId);

    // esse adicionar ocorrencia ira adicionar a ocorrencia a lista que está em
    // entrega
    return entrega.adicionarOcorrencia(descricao);
  }
}
