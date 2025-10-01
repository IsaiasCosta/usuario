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

   //converter a lista endereco
   public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoDTOS) {
      return enderecoDTOS.stream().map(this::paraEnderecoDTO).toList();
   }

   public EnderecoDTO paraEnderecoDTO(Endereco enderecoDTO) {
      return EnderecoDTO.builder()
              .rua(enderecoDTO.getRua())
              .numero(enderecoDTO.getNumero())
              .complemento(enderecoDTO.getComplemento())
              .cidade(enderecoDTO.getCidade())
              .estado(enderecoDTO.getEstado())
              .cep(enderecoDTO.getCep())
              .build();
   }
   //convereter lista telefone

   public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefoneDTOS) {
      return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();
   }

   public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO) {
      return TelefoneDTO.builder()
              .numero(telefoneDTO.getNumero())
              .ddd(telefoneDTO.getDdd())
              .build();
   }
}
