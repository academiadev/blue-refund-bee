package br.com.academiadev.bluerefund.service;

import java.util.Random;

public class SenhaService {
	
	private String alfabeto = "abcdefghijklmnopqrstuvwxyz";
	
	public String novaSenha() {
		String senha = "";
		Random gerador = new Random();
		senha += alfabeto.toUpperCase().charAt(gerador.nextInt(alfabeto.length()));
		for(int i=0; i<3; i++)
			senha += alfabeto.charAt(gerador.nextInt(alfabeto.length()));
		senha += "_";
		for(int i=0; i<3; i++)
			senha += Integer.toString(gerador.nextInt(10));
		return senha;
	}

}
