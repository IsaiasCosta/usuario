package com.isaiascosta.usuario.controller;

import com.isaiascosta.usuario.business.UsuarioService;
import com.isaiascosta.usuario.business.dto.UsuarioDTO;
import com.isaiascosta.usuario.infrastructure.entity.Usuario;
import com.isaiascosta.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor

public class UsuarioController {

   private final UsuarioService usuarioService;
   private final AuthenticationManager authenticationManager;
   private final JwtUtil jwtUtil;

   //Login do Usuario
   @PostMapping("/login")
   public String loginUsuario(@RequestBody UsuarioDTO usuarioDTO) {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
      );
      return "Bearer" + jwtUtil.generateToken(authentication.getName());
   }

   //Criando  o usuario no banco
   @PostMapping
   public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
      return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
   }

   //Buscar Por Email
   @GetMapping("/por-email")
   ResponseEntity<Usuario> buscarPorEmail(@RequestParam("email") String email) {
      return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
   }

   //Busca Usuario por nome
   @GetMapping("/por-nome")
   public ResponseEntity<Usuario> buscarUsuarioPorNome(@RequestParam("nome") String nome) {
      return ResponseEntity.ok(usuarioService.buscarUsuarioPorNome(nome));
   }

   //Deleta usuario  Por Id
   @DeleteMapping("/id/{id}")
   public ResponseEntity<Void> excluirPorId(@PathVariable Long id) {
      usuarioService.excluirPorId(id);
      return ResponseEntity.ok().build();
   }

   //Deleta usuario por email
   @DeleteMapping("{email}")
   public ResponseEntity<Void> excluirPorEmail(@PathVariable String email) {
      usuarioService.excluirPorEmail(email);
      return ResponseEntity.ok().build();
   }

   //Atualizar Usuario
   @PutMapping
   public ResponseEntity<Usuario> atualizarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
      return ResponseEntity.ok(usuarioService.atualizarUsuario(usuarioDTO));
   }
}
