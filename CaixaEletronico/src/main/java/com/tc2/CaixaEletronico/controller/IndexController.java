	package com.tc2.CaixaEletronico.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.tc2.CaixaEletronico.models.Conta;
import com.tc2.CaixaEletronico.repository.ContaRepository;

@Controller
public class IndexController {
	
	@Autowired
	private ContaRepository cr2;
	
	@GetMapping("/home")
	public String index() {
		return "home/1";
	}
	
	@GetMapping("/home/{id}")
	public ModelAndView home(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Conta> opt = cr2.findById(id);
		
		if(opt.isEmpty()) {
			md.setViewName("redirect:/home");
			return md;
		}

		md.setViewName("/home");
		Conta conta = opt.get();

		md.addObject("conta", conta);
		return md;
	}
	
	
}
