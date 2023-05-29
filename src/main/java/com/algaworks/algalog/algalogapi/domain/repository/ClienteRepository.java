package com.algaworks.algalog.algalogapi.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algalog.algalogapi.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  // Metodo customizado a partir do spring data jpa
  // prefixo vvalido para padronizar a interface
  // ex: findBy"Nome da propriedade"
  List<Cliente> findByNome(String nome);

  // adicionando containing apos o nome o spring jpa fornece uuma implementação
  // para
  // para dizer que o nome não é exato
  List<Cliente> findByNomeContaining(String nome);

  // retornando po email
  Optional<Cliente> findByEmail(String email);

}
