package com.isaiascosta.usuario.business.dto;

import lombok.*;

//Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {

   private  Long id ;
   private String rua;
   private Long numero;
   private String complemento;
   private String cidade;
   private String estado;
   private String cep;

}
