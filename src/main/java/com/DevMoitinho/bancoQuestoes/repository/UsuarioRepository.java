
package com.DevMoitinho.bancoQuestoes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DevMoitinho.bancoQuestoes.model.Usuario;

/**
 * Repository interface for accessing Usuario entities.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    public Optional<Usuario> findByUsername(String username);
    
}
