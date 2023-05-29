package com.algaworks.algalog.algalogapi.domain.service;

import org.springframework.stereotype.Service;

import com.algaworks.algalog.algalogapi.domain.exception.NegocioException;
import com.algaworks.algalog.algalogapi.domain.model.Entrega;
import com.algaworks.algalog.algalogapi.domain.model.StatusEntrega;
import com.algaworks.algalog.algalogapi.domain.repository.EntregaRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FinalizacaoEntregaService {

  private EntregaRepository entregaRepository;
  private BuscaEntregaService buscaEntregaService;

  @Transactional
  public void finalizar(Long entregaId) {
    Entrega entrega = buscaEntregaService.buscar(entregaId);

    entrega.finalizar();
    entregaRepository.save(entrega);

  }
}
