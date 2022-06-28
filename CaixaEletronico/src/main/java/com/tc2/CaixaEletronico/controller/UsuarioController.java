package com.tc2.CaixaEletronico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tc2.CaixaEletronico.models.Usuario;
import com.tc2.CaixaEletronico.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository ur;

	@RequestMapping("/usuario/registro")
	public String form() {
		return "Usuario/registro";
	}
	@RequestMapping("/usuario/login")
	public String login() {
		return "Usuario/login";
	}
	
	@PostMapping("/usuario/registro/sucesso")
	public String adicionar(Usuario usuario) {
		ur.save(usuario);
		return "Usuario/login";
	}
	
	@PostMapping("/logar")
	public String logar(Usuario usuario) {
		Usuario user = this.ur.Login(usuario.getLogin(), usuario.getSenha());
		if(user!=null) {
			return "redirect:/usuario/login";
			}
		return("/usuario/login");
	}
}
