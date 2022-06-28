package com.tc2.CaixaEletronico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tc2.CaixaEletronico.models.Conta;
import com.tc2.CaixaEletronico.repository.ContaRepository;

@Controller
public class ContasController {
	
	@Autowired
	private ContaRepository cr;
	
	@GetMapping("/conta/form")
	public String form() {
		return "contas/formConta";
	}
	
	@GetMapping("/conta/form/novo")
	public String formnovo() {
		return "contas/formNovaConta";
	}
	
	@PostMapping("/conta")
	public String adicionar(Conta usuario) {
		cr.save(usuario);
		return "contas/conta-adicionada";
	}
}
