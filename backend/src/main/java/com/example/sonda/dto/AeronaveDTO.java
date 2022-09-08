package com.example.sonda.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.example.sonda.entities.Aeronave;
import com.example.sonda.enuns.Marca;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AeronaveDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public interface AeronaveView {
		public static interface RegistrationPostPut{}		
	}
	
	private Long id;
	
	@NotBlank(groups = AeronaveView.RegistrationPostPut.class, message = "Campo de preenchimento obrigatório")	
	@JsonView(AeronaveView.RegistrationPostPut.class)
	private String nome;	
	
	@JsonView(AeronaveView.RegistrationPostPut.class)
	private Marca marca;
		
	@JsonView(AeronaveView.RegistrationPostPut.class)
	private Integer ano;	
	
	@JsonView(AeronaveView.RegistrationPostPut.class)
	private String descricao;
	
	@JsonView(AeronaveView.RegistrationPostPut.class)
	private boolean vendido;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime created;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime updated;
	
	public AeronaveDTO() {
		
	}
	
	public AeronaveDTO(Aeronave entity) {
		id = entity.getId();
		nome = entity.getNome();
		marca = entity.getMarca();
		ano = entity.getAno();
		descricao = entity.getDescricao();
		vendido = entity.isVendido();
		created = entity.getCreated();
		updated = entity.getUpdated();
	}

}
