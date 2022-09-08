package com.example.sonda.factors;

import com.example.sonda.dto.AeronaveDTO;
import com.example.sonda.enuns.Marca;

public class AeronaveFactory {
	
	public static AeronaveDTO insrtDTO() {
		AeronaveDTO dto = new AeronaveDTO();
		dto.setNome("Douglas DC-3");
		dto.setMarca(Marca.Boeing);
		dto.setDescricao("Boeing DC-3");
		dto.setAno(2000);
		dto.setVendido(false);
		return dto;
	}
}
