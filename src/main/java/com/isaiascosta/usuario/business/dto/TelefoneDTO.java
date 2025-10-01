package com.isaiascosta.usuario.business.dto;

import lombok.*;

//Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneDTO {

   private String numero;
   private String ddd;

}
