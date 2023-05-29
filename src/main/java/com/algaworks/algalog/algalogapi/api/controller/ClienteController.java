package com.algaworks.algalog.algalogapi.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.algalogapi.domain.model.Cliente;
import com.algaworks.algalog.algalogapi.domain.repository.ClienteRepository;
import com.algaworks.algalog.algalogapi.domain.service.CatalogoClienteService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/*
 * As vezes para testes o autowired pode ocasionar erro
 * Uma forma de fazer o clienteRepository sem precisar usá-lo
 * Cria-se um construtor para o repository a partir do Lombok
 * que cria um construtor com allArgsContructor
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
  // interface do jakarta persistense usada para fazer as operações com as
  // entidades
  // injeta um entity manager na variavel de instância manager
  // from cliente -> jpql -> linguagem de consultas do jakarta persistence
  // feita para consultar objetos, segundo parametro é a entidade
  // se cliente está escrito maiúsculo é from Cliente
  /*
   * @PersistenceContext
   * private EntityManager manager;
   * não é uma boa prática utilizar essa metodologia
   * manager.createQuery("from Cliente", Cliente.class)
   * .getResultList();// retorna uma lista de clientes
   */
  private ClienteRepository clienteRepository;
  private CatalogoClienteService clienteService;

  @GetMapping("")
  public List<Cliente> listar() {

    return clienteRepository.findAll();

  }

  @GetMapping("/{clienteId}")
  public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
    // Optional é tipo um container que possui algo dentro e pode retornar alguma
    // coisa ou vazio
    // Optional<Cliente> cliente = clienteRepository.findById(clienteId);

    // melhorando o codigo
    return clienteRepository.findById(clienteId)
        // .map(cliente -> ResponseEntity.ok(cliente))// cliente representa o cliente e
        // retorna ok e o cliente tbm
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());// ou retorna notfound

    // se cliente estiver no objeto, retorna um 200
    // if (cliente.isPresent()) {
    // return ResponseEntity.ok(cliente.get());
    // }

    // retorna um 404 com que não foi encontrado
    // return ResponseEntity.notFound().build();
    // or else retorna o que tem dentro do optional, ou retorna null
    // mas não é uma boa prática cliente.orElse(null);

  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED) // forma de retornar o status sem necessitar destacar
  // colocar o @Valid para validar antes de acionar os dados do cliente e entrar
  // no save no atualizar tbm
  public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
    // O jakarta validation esta entrando no repository e ai sim lança as sua
    // exceptions
    // mais o certo e acionar antes para não ter uma sobrecarga desnecessária de
    // dados
    return clienteService.salvar(cliente);
    // return clienteRepository.save(cliente);
  }

  @PutMapping("/{clienteId}")
  public ResponseEntity<Cliente> atualizar(
      @PathVariable Long clienteId,
      @Valid @RequestBody Cliente cliente) {
    // vai lá no repositorio e ve se existe
    if (!clienteRepository.existsById(clienteId)) {
      return ResponseEntity.notFound().build();
    }

    // ele não atualiza o cliente porque o id vai na url então ele cria um novo
    // cliente
    // porisso, seta-se o id
    cliente.setId(clienteId);
    // cliente = clienteRepository.save(cliente);
    clienteService.salvar(cliente);

    return ResponseEntity.ok(cliente);
  }

  @DeleteMapping("/{clienteId}")
  public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
    // vai lá no repositorio e ve se existe
    if (!clienteRepository.existsById(clienteId)) {
      return ResponseEntity.notFound().build();
    }
    // se existir
    // clienteRepository.deleteById(clienteId);
    clienteService.excluir(clienteId);

    // como não está retornando um corpo usa-se o noContent ou 204
    return ResponseEntity.noContent().build();
  }
}
