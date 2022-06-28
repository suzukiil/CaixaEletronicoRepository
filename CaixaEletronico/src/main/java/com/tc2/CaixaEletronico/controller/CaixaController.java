package com.tc2.CaixaEletronico.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tc2.CaixaEletronico.models.Conta;
import com.tc2.CaixaEletronico.repository.ContaRepository;

@Controller
public class CaixaController {

	@Autowired
	private ContaRepository cr;

	@GetMapping("/sacar")
	public String sacar() {

		return "sacar";
	}

	@GetMapping("/depositar")
	public String depositar() {

		return "depositar";
	}

	@GetMapping("/sacar/{id}")
	public ModelAndView saque(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Conta> opt = cr.findById(id);
		
		if(opt.isEmpty()) {
			md.setViewName("redirect:/sacar");
			return md;
		}

		md.setViewName("/sacar");
		Conta conta = opt.get();

		md.addObject("conta", conta);
		return md;
	}
	
	@GetMapping("/depositar/{id}")
	public ModelAndView deposito(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Conta> opt = cr.findById(id);
		
		if(opt.isEmpty()) {
			md.setViewName("redirect:/depositar");
			return md;
		}

		md.setViewName("/depositar");
		Conta conta = opt.get();

		md.addObject("conta", conta);
		return md;
	}
	
	@PostMapping("/depositar/{id}")
	public String efetuardeposito(@PathVariable Long id,RedirectAttributes attributes, double dinheiro){		
		Optional<Conta> opt = cr.findById(id);
		Conta conta = opt.get();	
		attributes.addFlashAttribute("mensagem", "DEPÓSITO EFETUADO COM SUCESSO!");
		conta.setDinheiro(conta.getDinheiro()+dinheiro);
		
		if(conta.getDinheiro()<dinheiro) {
			conta.setStatus(false);
			attributes.addFlashAttribute("mensagem", "CONTA INATIVA");
			cr.save(conta);
		}else {
			conta.setStatus(true);
			cr.save(conta);
		}
		
		ModelAndView md = new ModelAndView();
		md.addObject("conta", conta);
		
		
		return "redirect:/depositar/{id}";
	}
	@PostMapping("/sacar/{id}")
	public String efetuarsaque(@PathVariable Long id, RedirectAttributes attributes,double dinheiro){		
		Optional<Conta> opt = cr.findById(id);
		Conta conta = opt.get();
		if(dinheiro==0) {
			attributes.addFlashAttribute("mensagem", "informe algum valor");
			conta.setStatus(false);
		}else {
			if(conta.getDinheiro()==0) {
				conta.setStatus(false);
			}
			
			if(conta.getDinheiro()<dinheiro) {
				attributes.addFlashAttribute("mensagem", "Você não possui dinheiro suficiente na sua conta!");
					cr.save(conta);	
			}else {
				attributes.addFlashAttribute("mensagem", "SAQUE EFETUADO COM SUCESSO!");
				conta.setDinheiro(conta.getDinheiro()-dinheiro);
				if(conta.getDinheiro()==0) {
					conta.setStatus(false);
					attributes.addFlashAttribute("mensagem", "SAQUE EFETUADO COM SUCESSO, porém, sua conta ficou inativa!");
					cr.save(conta);
				}else {
					conta.setStatus(true);
					cr.save(conta);
				}
			}
			ModelAndView md = new ModelAndView();
			md.addObject("conta", conta);
		}
		
		return "redirect:/sacar/{id}";
	}

}
