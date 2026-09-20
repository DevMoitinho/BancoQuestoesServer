package com.DevMoitinho.bancoQuestoes.model;


import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user of the system, implementing Spring Security's UserDetails.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails{

    /**
     * Unique identifier of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique username for authentication.
     */
    @Column(nullable=false, unique=true)
    private String username;

    /**
     * Hashed password of the user.
     */
    @Column(nullable=false)
    private String password;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
	}
}
