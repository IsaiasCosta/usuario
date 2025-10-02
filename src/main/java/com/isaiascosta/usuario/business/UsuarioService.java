package com.isaiascosta.usuario.business;

import com.isaiascosta.usuario.business.converter.UsuarioConverter;
import com.isaiascosta.usuario.business.dto.UsuarioDTO;
import com.isaiascosta.usuario.infrastructure.entity.Usuario;
import com.isaiascosta.usuario.infrastructure.exceptions.ConflictException;
import com.isaiascosta.usuario.infrastructure.exceptions.ResourceNotFoundExecption;
import com.isaiascosta.usuario.infrastructure.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

   //injeção de dependência
   private final UsuarioRepository usuarioRepository;
   private final UsuarioConverter usuarioConverter;
   private final PasswordEncoder passwordEncoder;

   // Regra de negócio: antes de salvar o usuário, verificar se o e-mail já está registrado no sistema.
   public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
      try {
         emailExiste(usuarioDTO.getEmail());
         usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
         Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
         return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

      } catch (ConflictException e) {

         throw new ConflictException("Esse email já está cadastrado ! " + e.getCause());
      }
   }

   // Regra de negócio: verificar se o e-mail já existe. Caso exista, lançar uma exceção informando a duplicidade.
   public void emailExiste(String email) {
      try {
         boolean existe = verificaEmailExistente(email);
         if (existe) {
            throw new ClassCastException("Esse email já Cadastrato" + email);
         }
      } catch (ClassCastException e) {
         throw new ClassCastException("Esse email já Cadastrato" + e.getCause());
      }
   }

   // Consulta o repositório para verificar a existência do e-mail.
   public boolean verificaEmailExistente(String email) {
      return usuarioRepository.existsByEmail(email);
   }

   //Busca usuario Por Email
   public Usuario buscarPorEmail(String email) {
      return usuarioRepository.findByEmail(email).orElseThrow(
              () -> new ResourceNotFoundExecption("Usuario não encontrado  " + email));
   }

   //Buscar usuario por nome
   public Usuario buscarUsuarioPorNome(String nome) {
      return usuarioRepository.findByNome(nome).orElseThrow(
              () -> new ResourceNotFoundExecption("Usuario não encontrado  " + nome));
   }

   //Deleta usuario  Por Id
   public void excluirPorId(Long id) {

      if (!usuarioRepository.existsById(id)) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado !");
      }
      usuarioRepository.deleteById(id);
   }

   //Excluir Por email
   public void excluirPorEmail(String email) {
      if (!usuarioRepository.existsByEmail(email)) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado !");
      }
      usuarioRepository.deleteByEmail(email);
   }

   //Atualiza usuario e verifica se existe email
   public Usuario atualizarUsuario(UsuarioDTO usuarioDTO) {
      Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuarioDTO.getEmail());
      if (usuarioExistente.isEmpty()) {
         throw new EntityNotFoundException("Usuario com email " + usuarioDTO.getEmail());
      }
      Usuario usuario = usuarioExistente.get();
      usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
      return usuarioRepository.save(usuario);
   }
}
