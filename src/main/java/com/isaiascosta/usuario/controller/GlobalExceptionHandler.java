package com.isaiascosta.usuario.controller;

import com.isaiascosta.usuario.infrastructure.exceptions.ConflictException;
import com.isaiascosta.usuario.infrastructure.exceptions.ResourceNotFoundExecption;
import com.isaiascosta.usuario.infrastructure.exceptions.UnauthorizedException;
import com.isaiascosta.usuario.infrastructure.exceptions.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(ResourceNotFoundExecption.class)
   public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundExecption(ResourceNotFoundExecption ex,
                                                                           HttpServletRequest request) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(buildError(HttpStatus.NOT_FOUND.value(),
                      ex.getMessage(),
                      request.getRequestURI(),
                      "Resource not found"
              ));

   }

   @ExceptionHandler(ConflictException.class)
   public ResponseEntity<ErrorResponseDTO> handleConflictExecption(ConflictException ex,
                                                                   HttpServletRequest request) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
              .body(buildError(HttpStatus.CONFLICT.value(),
                      ex.getMessage(),
                      request.getRequestURI(),
                      "Conflict with existing resource"
              ));
   }

   @ExceptionHandler(UnauthorizedException.class)
   public ResponseEntity<ErrorResponseDTO> handleUnauthorizedException(UnauthorizedException ex,
                                                                       HttpServletRequest request) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(buildError(HttpStatus.UNAUTHORIZED.value(),
                      ex.getMessage(),
                      request.getRequestURI(),
                      "Resource not found"
              ));
   }

   @ExceptionHandler(IllegalArgumentException.class)
   public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                          HttpServletRequest request) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(buildError(HttpStatus.BAD_REQUEST.value(),
                      ex.getMessage(),
                      request.getRequestURI(),
                      "Resource not found"
              ));
   }

   private ErrorResponseDTO buildError(int status, String mensagem, String path, String error) {
      return ErrorResponseDTO.builder()
              .timestamp(LocalDateTime.now())
              .status(status)
              .error(error)
              .message(mensagem)
              .path(path)
              .build();

   }
}
