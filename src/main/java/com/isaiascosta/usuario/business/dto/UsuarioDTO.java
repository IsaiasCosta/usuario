package com.isaiascosta.usuario.business.dto;

import lombok.*;

import java.util.List;

//Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
   //DTO: use somente o que fornecessario par  aplicação

   private String nome;
   private String email;
   private String senha;
   private List<EnderecoDTO> enderecos;
   private List<TelefoneDTO> telefones;

}