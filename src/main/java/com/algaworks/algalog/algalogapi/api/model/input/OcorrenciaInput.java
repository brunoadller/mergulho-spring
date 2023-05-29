package com.algaworks.algalog.algalogapi.api.model.input;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcorrenciaInput {

  @NotBlank
  private String descricao;

}
