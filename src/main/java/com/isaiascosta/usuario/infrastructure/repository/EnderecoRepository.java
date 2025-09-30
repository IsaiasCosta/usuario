package com.isaiascosta.usuario.infrastructure.repository;

import com.icsdetec.aprendendospring.infrastruture.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
