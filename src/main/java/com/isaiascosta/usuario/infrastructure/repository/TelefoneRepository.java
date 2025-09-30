package com.isaiascosta.usuario.infrastructure.repository;

import com.icsdetec.aprendendospring.infrastruture.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
