package com.algaworks.algalog.algalogapi.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Problema {
  // Atributos que serão utilizados para retornar a exception
  private Integer status;
  private OffsetDateTime dataHora;
  private String titulo;
  private List<Campo> campo;

  @AllArgsConstructor
  @Getter
  public static class Campo {
    // campo que está com problema e a mensagem
    private String nome;
    private String mensagem;
  }
}
