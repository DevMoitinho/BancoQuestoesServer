package com.DevMoitinho.bancoQuestoes.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.DevMoitinho.bancoQuestoes.model.Usuario;
import com.DevMoitinho.bancoQuestoes.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<Usuario> userOptional = repo.findByUsername(username);
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("Usuario não exixste!");
        }
        return userOptional.get();
    }
}
