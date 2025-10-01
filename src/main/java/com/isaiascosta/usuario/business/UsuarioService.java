package com.isaiascosta.usuario.business;

import com.isaiascosta.usuario.business.converter.UsuarioConverter;
import com.isaiascosta.usuario.business.dto.UsuarioDTO;
import com.isaiascosta.usuario.infrastructure.entity.Usuario;
import com.isaiascosta.usuario.infrastructure.exeception.ConflictExeception;
import com.isaiascosta.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

   //injertar dependecia
   private final UsuarioRepository usuarioRepository;
   private final UsuarioConverter usuarioConverter;
   private final PasswordEncoder passwordEncoder;

   // Regra de negócio: antes de salvar o usuário, verificar se o e-mail já está registrado no sistema.

   public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
      try {
         Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
         usuario = usuarioRepository.save(usuario);
         return  usuarioConverter.paraUsuarioDTO(usuario);

//
//         emailExist(usuarioDTO.getEmail());
//         usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
//         return usuarioRepository.save(usuarioDTO);
      } catch (ConflictExeception e) {

         throw new ClassCastException("Esse email já cadastrado ! " + e.getCause());
      }
   }

   // Regra de negócio: verificar se o e-mail já existe. Caso exista, lançar uma exceção informando a duplicidade.

   public void emailExist(String email) {
      try {
         boolean existe = verificarEmailExistente(email);
         if (existe) {
            throw new ClassCastException("Esse email já cadastrado ! " + email);
         }
      } catch (ClassCastException e) {
         throw new ClassCastException("Esse email já cadastrado ! " + e.getCause());
      }
   }

   // Consulta o repositório para verificar a existência do e-mail.
   public boolean verificarEmailExistente(String email) {
      return usuarioRepository.existsByEmail(email);
   }
}
