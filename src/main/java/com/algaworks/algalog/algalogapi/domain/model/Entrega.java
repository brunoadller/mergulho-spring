package com.algaworks.algalog.algalogapi.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;

import java.util.List;

import com.algaworks.algalog.algalogapi.domain.ValidationGroups;
import com.algaworks.algalog.algalogapi.domain.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Entrega {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  /*
   * @ConvertGroup Faz a conversão e avisa o bean validation que não que
   * usar um determinaDO GRUPO E SIM OUTRO
   * converte de grupo padrão para o validation group criando
   */

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @Embedded // faz o mapeamento da outra tabela porem ela estará sendo mapeada para dentro
  // da tabela entrega
  private Destinatario destinatario;

  private BigDecimal taxa;

  // cascade faz o cascateamento das ocorrencias
  @OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL) // referencia a entrega da ocorrencia para esta o
                                                              // correncia que está na entrega
  private List<Ocorrencia> ocorrencias = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private StatusEntrega status;

  @JsonProperty(access = Access.READ_ONLY)
  private OffsetDateTime dataPedido;
  // a data finalizada não e necessário alterar, mas elea pode ser manipulada no
  // front e isso não é seguro
  // por isso usa-se o JsonProperty para ser lida somente
  private OffsetDateTime dataFinalizacao;

  public Ocorrencia adicionarOcorrencia(String descricao) {
    Ocorrencia ocorrencia = new Ocorrencia();

    ocorrencia.setDescricao(descricao);
    ocorrencia.setDataRegistro(OffsetDateTime.now());
    ocorrencia.setEntrega(this);// vai a propria entrega

    this.getOcorrencias().add(ocorrencia); // adiciona ocorrencia alista

    return ocorrencia;
  }

  public void finalizar() {
    // se nao poder ser finalizada
    if (naoPodeSerFinalizada()) {
      throw new NegocioException("Entrega não pode ser finalizada");
    }
    setStatus(StatusEntrega.FINALIZADA);
    setDataFinalizacao(OffsetDateTime.now());
  }

  public boolean podeSerFinalizada() {
    return StatusEntrega.PENDENTE.equals(getStatus());

  }

  public boolean naoPodeSerFinalizada() {
    return !podeSerFinalizada();
  }
}
