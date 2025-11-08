package com.isaiascosta.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "telefone")
public class Telefone {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(name = "usuario_id")
   private Long usuario_id;
   @Column(name = "numero", length = 12)
   private String numero;
   @Column(name = "ddd", length = 3)
   private String ddd;

}
