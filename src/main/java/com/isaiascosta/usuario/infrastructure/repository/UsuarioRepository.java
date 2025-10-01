package com.isaiascosta.usuario.infrastructure.repository;


import com.isaiascosta.usuario.business.dto.UsuarioDTO;
import com.isaiascosta.usuario.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
   boolean existsByEmail(String email);

   Optional<UsuarioDTO> findByEmail(String email);

   @Transactional
   void deleteByEmail(String email);

}
