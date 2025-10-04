package com.isaiascosta.usuario.business.converter;

import com.isaiascosta.usuario.business.dto.EnderecoDTO;
import com.isaiascosta.usuario.business.dto.TelefoneDTO;
import com.isaiascosta.usuario.business.dto.UsuarioDTO;
import com.isaiascosta.usuario.infrastructure.entity.Endereco;
import com.isaiascosta.usuario.infrastructure.entity.Telefone;
import com.isaiascosta.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {
   public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
      return Usuario.builder()
              .nome(usuarioDTO.getNome())
              .email(usuarioDTO.getEmail())
              .senha(usuarioDTO.getSenha())
              .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
              .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
              .build();
   }

   //converter a lista endereco
   public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {
      return enderecoDTOS.stream().map(this::paraEndereco).toList();
   }

   public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
      return Endereco.builder()
              .rua(enderecoDTO.getRua())
              .numero(enderecoDTO.getNumero())
              .complemento(enderecoDTO.getComplemento())
              .cidade(enderecoDTO.getCidade())
              .estado(enderecoDTO.getEstado())
              .cep(enderecoDTO.getCep())
              .build();
   }
   //convereter lista telefone

   public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS) {
      return telefoneDTOS.stream().map(this::paraTelefone).toList();
   }

   public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
      return Telefone.builder()
              .numero(telefoneDTO.getNumero())
              .ddd(telefoneDTO.getDdd())
              .build();
   }

   // Convertendo UsuarioDTO para usuario

   public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO) {
      return UsuarioDTO.builder()
              .nome(usuarioDTO.getNome())
              .email(usuarioDTO.getEmail())
              .senha(usuarioDTO.getSenha())
              .enderecos(paraListaEnderecoDTO(usuarioDTO.getEnderecos()))
              .telefones(paraListaTelefoneDTO(usuarioDTO.getTelefones()))
              .build();
   }

   //converter a lista endereco entity
   public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoDTOS) {
      return enderecoDTOS.stream().map(this::paraEnderecoDTO).toList();
   }

   public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
      return EnderecoDTO.builder()
              .id(endereco.getId())
              .rua(endereco.getRua())
              .numero(endereco.getNumero())
              .complemento(endereco.getComplemento())
              .cidade(endereco.getCidade())
              .estado(endereco.getEstado())
              .cep(endereco.getCep())
              .build();
   }
   //convereter lista telefone entity

   public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefoneDTOS) {
      return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();
   }

   public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
      return TelefoneDTO.builder()
              .id(telefone.getId())
              .numero(telefone.getNumero())
              .ddd(telefone.getDdd())
              .build();
   }

   // Compara o usuarioDTO com o usuario

   public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity) {
      return Usuario.builder()
              .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
              .id(entity.getId())
              .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
              .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
              .enderecos(entity.getEnderecos())
              .telefones(entity.getTelefones())
              .build();
   }

   //Compara o endereço do usuario

   public Endereco updateEndereco(EnderecoDTO dto, Endereco entity) {
      return Endereco.builder()
              .id(entity.getId())
              .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
              .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
              .complemento(dto.getComplemento())
              .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
              .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
              .cep(dto.getCep())
              .build();
   }
   //Compare o telefone do usuario
   public Telefone updateTelefone(TelefoneDTO dto, Telefone entity) {
      return Telefone.builder()
              .id(entity.getId())
              .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
              .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
              .build();
   }
}
