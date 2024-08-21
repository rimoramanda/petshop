package com.amanda.petshop.controller;

import com.amanda.petshop.dto.AuthenticationDTO;
import com.amanda.petshop.dto.RegisterDTO;
import com.amanda.petshop.entity.Usuario;
import com.amanda.petshop.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity cpf(@RequestBody @Validated AuthenticationDTO data) {
        var userNameSenha = new UsernamePasswordAuthenticationToken(data.cpf(),data.senha());
        var auth = this.authenticationManager.authenticate(userNameSenha);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data){

        if(this.usuarioRepository.findByCpf(data.cpf()) !=null) return ResponseEntity.badRequest().build();

        String encryptedSenha = new BCryptPasswordEncoder().encode(data.senha());
        Usuario novoUsuario = new Usuario();
        this.usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }
}
