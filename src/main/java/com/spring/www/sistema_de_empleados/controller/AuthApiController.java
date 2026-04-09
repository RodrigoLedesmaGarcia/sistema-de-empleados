package com.spring.www.sistema_de_empleados.controller;

import com.spring.www.sistema_de_empleados.entities.Usuario;
import com.spring.www.sistema_de_empleados.security.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthApiController {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UsuarioRepository usuarioRepository;

    public AuthApiController(BCryptPasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping({"/login"})
    public String loginView(){
        return "login";
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model){

        Usuario usuario = usuarioRepository.findByUsername(username);

        if(username == null || !passwordEncoder.matches(password, usuario.getPassword())){
            model.addAttribute("error", "usuario o contraseña incorrectas");
            return "login";
        }

        session.setAttribute("usuario", usuario);


        String redirect = (String) session.getAttribute("redirectAfterLogin");
        if(redirect != null){
            session.removeAttribute("redirectAfterLogin");
            return "redirect"+redirect;
        }
        return "rediret:/employee/inicio";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect/login";
    }
}
