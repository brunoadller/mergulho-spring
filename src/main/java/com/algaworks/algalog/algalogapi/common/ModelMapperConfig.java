package com.algaworks.algalog.algalogapi.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
  /*
   * Configuração do model mapper para o spring poder reconhecer
   * O @Bean vai dizer que ele será reconhecido pelo spring
   */
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
