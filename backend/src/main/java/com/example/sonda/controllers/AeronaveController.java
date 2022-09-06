package com.example.sonda.controllers;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.sonda.dto.AeronaveDTO;
import com.example.sonda.enuns.Marca;
import com.example.sonda.projections.AeronaveMinProjection;
import com.example.sonda.services.AeronaveService;
import com.example.sonda.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/aeronaves")
public class AeronaveController {
	
	@Autowired
	private AeronaveService service;
	
	@GetMapping
	public ResponseEntity<Page<AeronaveDTO>> findAll(Pageable pageable){		
		Page<AeronaveDTO> list = service.findAll(pageable); 
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/find")
	public ResponseEntity<Page<AeronaveDTO>> find(SpecificationTemplate.AeronaveSpec spec, Pageable pageable){		
		Page<AeronaveDTO> list = service.find(spec, pageable); 
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<AeronaveDTO> findById(@PathVariable Long id){
		AeronaveDTO obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<AeronaveDTO> inserir(@RequestBody @Validated(AeronaveDTO.AeronaveView.RegistrationPostPut.class) @JsonView(AeronaveDTO.AeronaveView.RegistrationPostPut.class) AeronaveDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<AeronaveDTO> update(@PathVariable Long id, @RequestBody @Validated(AeronaveDTO.AeronaveView.RegistrationPostPut.class) @JsonView(AeronaveDTO.AeronaveView.RegistrationPostPut.class) AeronaveDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping(value = "/vendidas")
	public ResponseEntity<Long> getCountByVendido(){
		return ResponseEntity.ok().body(service.getCountByVendido());
	}
	
	@GetMapping(value = "/qto/marcas")
	public ResponseEntity<List<AeronaveMinProjection>> getCountByMarca(){
		return ResponseEntity.ok().body(service.getCountByMarca());
	}
	
	@GetMapping(value = "/semana")
	public ResponseEntity<Long> getCountLastWeek(){
		return ResponseEntity.ok().body(service.getCountLastWeek());
	}
	
	@GetMapping(value = "/marcas")
	public ResponseEntity<List<Marca>> getMarca(){
		return ResponseEntity.ok().body(Arrays.asList(Marca.values()));
	}
	

}
