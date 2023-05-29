package com.algaworks.algalog.algalogapi.domain.model;

import com.algaworks.algalog.algalogapi.domain.ValidationGroups;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
// gera os metodos equals and hashcode
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Cliente {
  // referenciando que so o id receberá o equals and hashcode
  // grupo de validação padrão para poder validar um determinado grupo
  // @NotNull(groups = Default.class)

  // Mas será usado como groups a interface criada para especificar que o
  // clienteId será validado naquele grupo
  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 60)
  private String nome;

  @NotBlank
  @Email
  @Size(max = 255)
  private String email;

  @NotBlank
  @Size(max = 20)
  @Column(name = "fone")
  private String telefone;

}
