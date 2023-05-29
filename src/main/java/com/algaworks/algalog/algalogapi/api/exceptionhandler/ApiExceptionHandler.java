package com.algaworks.algalog.algalogapi.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algalog.algalogapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algalog.algalogapi.domain.exception.NegocioException;

import lombok.AllArgsConstructor;

/*
 * Essa annotation tem por função enfatizar expecificamente que esta classe será
 * utilizada para tratamento de exceções
 * 
 * Response Exception Handler trata várias exceptions por base logo será herdade pela nossa api
 * 
 * Chamandoo o metodo handle METHOD ARGUMENT NOT VALID
 */

@AllArgsConstructor // fazendo a injeção do messageSource pelo constructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
  // Ao criar o message.properties e colocar os tipos de menssagens que serão
  // enviada para os determinados tipos de erros

  // interface para resolver mensagens
  private MessageSource messageSource;

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    // criando uma lista de campos
    List<Problema.Campo> campos = new ArrayList<>();
    // o campo está setado mais tem que adicionar a lista os atributos do campo

    // pegando todos os erros que foram atribuidos na requisição
    // retorna uma lista de erros e coloca-se na variável errors
    for (ObjectError error : ex.getBindingResult().getAllErrors()) {
      // fazendo o casting consegue-se pegar o campo do erro e seu nome
      String nome = ((FieldError) error).getField();

      // pegando a mensagem
      // String mensagem = error.getDefaultMessage();
      // ao inves de pegar a mesagem do error
      // pegar do:
      String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());

      // adiciona os campos de erros que vem do erro em nome e mensagem
      campos.add(new Problema.Campo(nome, mensagem));
    }

    Problema problema = new Problema();
    // pegando o status do tratamento
    problema.setStatus(status.value());
    // pega o tempo exato do erro
    problema.setDataHora(OffsetDateTime.now());
    // seta a mensagem de aviso
    problema.setTitulo(
        "Um ou mais campos estão inválidos Faça o preenchimento correto e tente novamente.");
    // setando a lista de campos
    problema.setCampo(campos);

    // TODO Auto-generated method stub
    // CHAMANDO O HANDLE EXCEPTION INTERNAL
    // retornando o problema depois de setado
    return handleExceptionInternal(ex, problema, headers, status, request);
  }

  // metodo para tratar o erro de email
  // para poder tratar esse negocioException colocar a annotation
  @ExceptionHandler(NegocioException.class)
  public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
    // para passar o status
    HttpStatus status = HttpStatus.BAD_REQUEST;

    Problema problema = new Problema();
    // pegando o status do tratamento
    problema.setStatus(status.value());
    // pega o tempo exato do erro
    problema.setDataHora(OffsetDateTime.now());
    // seta a mensagem de aviso
    problema.setTitulo(
        ex.getMessage());

    return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
  }

  @ExceptionHandler(EntidadeNaoEncontradaException.class)
  public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
    // para passar o status
    HttpStatus status = HttpStatus.NOT_FOUND;

    Problema problema = new Problema();
    // pegando o status do tratamento
    problema.setStatus(status.value());
    // pega o tempo exato do erro
    problema.setDataHora(OffsetDateTime.now());
    // seta a mensagem de aviso
    problema.setTitulo(
        ex.getMessage());

    return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
  }
}
