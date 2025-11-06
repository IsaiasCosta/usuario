package com.isaiascosta.usuario.business;

import com.isaiascosta.usuario.business.converter.UsuarioConverter;
import com.isaiascosta.usuario.business.dto.EnderecoDTO;
import com.isaiascosta.usuario.business.dto.TelefoneDTO;
import com.isaiascosta.usuario.business.dto.UsuarioDTO;
import com.isaiascosta.usuario.infrastructure.entity.Endereco;
import com.isaiascosta.usuario.infrastructure.entity.Telefone;
import com.isaiascosta.usuario.infrastructure.entity.Usuario;
import com.isaiascosta.usuario.infrastructure.exceptions.ConflictException;
import com.isaiascosta.usuario.infrastructure.exceptions.ResourceNotFoundExecption;
import com.isaiascosta.usuario.infrastructure.exceptions.UnauthorizedException;
import com.isaiascosta.usuario.infrastructure.repository.EnderecoRepository;
import com.isaiascosta.usuario.infrastructure.repository.TelefoneRepository;
import com.isaiascosta.usuario.infrastructure.repository.UsuarioRepository;
import com.isaiascosta.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UsuarioService {

   //injeção de dependência
   private final UsuarioRepository usuarioRepository;
   private final EnderecoRepository enderecoRepository;
   private final TelefoneRepository telefoneRepository;
   private final UsuarioConverter usuarioConverter;
   private final PasswordEncoder passwordEncoder;
   private final JwtUtil jwtUtil;
   private final AuthenticationManager authenticationManager;


   // Regra de negócio: antes de salvar o usuário, verificar se o e-mail já está registrado no sistema.
   public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
      try {
         emailExiste(usuarioDTO.getEmail());
         usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
         Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
         return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

      } catch (ConflictException e) {
         throw new ConflictException("Esse email já Cadastrato " , e);
      }
   }

   //Autenticar usuario
   public String autenticarUsuario(UsuarioDTO usuarioDTO) {
      try {
         Authentication authentication = authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
         );
         return "Bearer " + jwtUtil.generateToken(authentication.getName());

      } catch (BadCredentialsException | UsernameNotFoundException | AuthorizationDeniedException e) {
         throw new UnauthorizedException("Usuario ou senha inválidos", e.getCause());
      }
   }

   // Regra de negócio: verificar se o e-mail já existe. Caso exista, lançar uma exceção informando a duplicidade.
   public void emailExiste(String email) {
      try {
         boolean existe = verificaEmailExistente(email);
         if (existe) {
            throw new ConflictException("Esse email já Cadastrato " + email);
         }
      } catch (ConflictException e) {
         throw new ConflictException("Esse email já Cadastrato " + e.getCause());
      }
   }

   // Consulta o repositório para verificar a existência do e-mail.
   public boolean verificaEmailExistente(String email) {
      return usuarioRepository.existsByEmail(email);
   }

   //Busca usuario Por Email
   public UsuarioDTO buscarPorEmail(String email) {
      try {
         return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                 () -> new ResourceNotFoundExecption("Usuario não encontrado  " + email)));
      } catch (ResourceNotFoundExecption e) {
         throw new ResourceNotFoundExecption("Usuario não encontrado ");
      }
   }

   //Buscar usuario por nome
   public UsuarioDTO buscarUsuarioPorNome(String nome) {
      try {
         return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByNome(nome).orElseThrow(
                 () -> new ResourceNotFoundExecption("Usuario não encontrado  " + nome)));
      } catch (ResourceNotFoundExecption e) {
         throw new ResourceNotFoundExecption("Usuario não encontrado");
      }
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

   //Regra Atualizar dados do usuario - extrair o email atravez do token
   public UsuarioDTO atualizarUsuarioPorEmail(String token, UsuarioDTO dto) {
      // Extrai o email do usuário autenticado a partir do token JWT
      String email = jwtUtil.extraiEmailToken(token.substring(7));
      // Criptografa a senha enviada, se estiver presente no DTO
      dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);
      // Busca o usuário no banco de dados pelo email; lança exceção se não existir
      Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
              () -> new ResourceNotFoundExecption("Email não encontrado !" + email));
      // Mescla os dados recebidos com os dados existentes do usuário
      Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
      // Salva o usuário atualizado e retorna como DTO
      return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
   }

   // Atualiza o endereço por ID
   public EnderecoDTO atualizarEnderecoPorId(Long idEndereco, EnderecoDTO enderecoDTO) {
      // Busca o endereço pelo ID; lança exceção se não existir
      Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(
              () -> new ResourceNotFoundExecption("ID do endereço não encontrado ! " + idEndereco));
      // Mescla os dados recebidos com os dados existentes do endereço
      Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);
      // Salva o endereço atualizado e retorna como DTO
      return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
   }

   // Atualiza o telefone por ID
   public TelefoneDTO atualizaTelefonePorID(Long idTelefone, TelefoneDTO telefoneDTO) {
      // Busca o telefone pelo ID; lança exceção se não existir
      Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(
              () -> new ResourceNotFoundExecption("ID do telefone não encontrado! " + idTelefone));
      // Mescla os dados recebidos com os dados existentes do telefone
      Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, entity);
      // Salva o telefone atualizado e retorna como DTO
      return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));

   }
}
