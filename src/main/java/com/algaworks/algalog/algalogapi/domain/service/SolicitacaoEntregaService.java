package com.algaworks.algalog.algalogapi.domain.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.algaworks.algalog.algalogapi.domain.exception.NegocioException;
import com.algaworks.algalog.algalogapi.domain.model.Cliente;
import com.algaworks.algalog.algalogapi.domain.model.Entrega;
import com.algaworks.algalog.algalogapi.domain.model.StatusEntrega;
import com.algaworks.algalog.algalogapi.domain.repository.ClienteRepository;
import com.algaworks.algalog.algalogapi.domain.repository.EntregaRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SolicitacaoEntregaService {

  private EntregaRepository entregaRepository;
  private CatalogoClienteService catalogoClienteService;

  @Transactional
  public Entrega solicitar(Entrega entrega) {

    Cliente cliente = catalogoClienteService.buscar(entrega.getCliente().getId());

    // resolve o problema do cliente so vir com o id e os outros dados null
    entrega.setCliente(cliente);
    entrega.setStatus(StatusEntrega.PENDENTE);
    entrega.setDataPedido(OffsetDateTime.now());

    return entregaRepository.save(entrega);
  }
}
