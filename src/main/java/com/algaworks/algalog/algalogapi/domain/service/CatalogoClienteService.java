package com.algaworks.algalog.algalogapi.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algalog.algalogapi.domain.exception.NegocioException;
import com.algaworks.algalog.algalogapi.domain.model.Cliente;
import com.algaworks.algalog.algalogapi.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CatalogoClienteService {

  private ClienteRepository clienteRepository;

  // ela declara que o metodo deve ser executado dentro de uma transação
  @Transactional
  public Cliente salvar(Cliente cliente) throws NegocioException {
    // antes de salver fazer a regra de negocio
    boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
        .stream()// se o email for diferente do email que esta no banco vai da match se der match
                 // dará true
        .anyMatch(clienteExistente -> clienteExistente.equals(cliente));// clienete cujo o emai é igual ao
                                                                        // cliente.getEmail()
    // exception ccriada em umas pasta exception no domain para retornar este erro
    if (emailEmUso) {
      throw new NegocioException("Já existe um email cadastrado com este e-mail");
    }
    return clienteRepository.save(cliente);
  }

  @Transactional
  public void excluir(Long clienteId) {
    clienteRepository.deleteById(clienteId);
  }

  // função para verificar se o id ta correto na hora de buscar
  public Cliente buscar(Long clienteId) {
    // ele ira buscar no cliente repository se o id
    // colocado do cliente na entrega e igual ao id que esta no banco
    // ele pega o que esta dentro do optional e atribui a variável cliente
    // se nao tiver nada no optional ele lança uma exception
    return clienteRepository.findById(clienteId)
        .orElseThrow(() -> new NegocioException("Cliente não encontrado"));
  }
}
