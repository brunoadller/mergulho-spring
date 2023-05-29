package com.algaworks.algalog.algalogapi.api.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.algalogapi.api.assembler.EntregaAssembler;
import com.algaworks.algalog.algalogapi.api.model.DestinatarioModel;
import com.algaworks.algalog.algalogapi.api.model.EntregaModel;
import com.algaworks.algalog.algalogapi.api.model.input.EntregaInput;
import com.algaworks.algalog.algalogapi.domain.model.Entrega;
import com.algaworks.algalog.algalogapi.domain.repository.EntregaRepository;
import com.algaworks.algalog.algalogapi.domain.service.FinalizacaoEntregaService;
import com.algaworks.algalog.algalogapi.domain.service.SolicitacaoEntregaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {

  private SolicitacaoEntregaService solicitacaoEntregaService;
  private EntregaRepository entregaRepository;
  private FinalizacaoEntregaService finalizacaoEntregaService;
  private EntregaAssembler entregaAssembler;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EntregaModel solicitar(@Valid @RequestBody EntregaInput entregaInput) {
    // converte o entrega input para uma etidade do tipo entrega
    Entrega novaEntrega = entregaAssembler.toEntity(entregaInput);
    Entrega entregaSolicitacao = solicitacaoEntregaService.solicitar(novaEntrega);
    return entregaAssembler.toModel(entregaSolicitacao);
  }

  @GetMapping
  public List<EntregaModel> listar() {
    List<Entrega> listaEntrega = entregaRepository.findAll();
    return entregaAssembler.toCollectionModel(listaEntrega);
  }

  @GetMapping("/{entregaId}")
  public ResponseEntity<EntregaModel> buscar(@PathVariable Long entregaId) {
    return entregaRepository.findById(entregaId)
        .map(entrega -> ResponseEntity.ok(entregaAssembler.toModel(entrega)))
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{entregaId}/finalizacao")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void finalizar(@PathVariable Long entregaId){
    finalizacaoEntregaService.finalizar(entregaId);
  }
}
