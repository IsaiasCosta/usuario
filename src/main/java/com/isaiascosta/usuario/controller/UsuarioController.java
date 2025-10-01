package com.isaiascosta.usuario.controller;

import com.isaiascosta.usuario.business.UsuarioService;
import com.isaiascosta.usuario.business.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor

public class UsuarioController {

   private final UsuarioService usuarioService;

   //Criando  o usuario no banco
   @PostMapping
   public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
      return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
   }
}
