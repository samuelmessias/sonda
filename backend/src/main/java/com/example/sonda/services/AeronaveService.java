package com.example.sonda.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sonda.dto.AeronaveDTO;
import com.example.sonda.entities.Aeronave;
import com.example.sonda.projections.AeronaveMinProjection;
import com.example.sonda.repositories.AeronaveRepository;
import com.example.sonda.services.exceptions.DatabaseException;
import com.example.sonda.services.exceptions.ResourceNotFoundException;
import com.example.sonda.specifications.SpecificationTemplate;

@Service
public class AeronaveService {
	
	@Autowired
	private AeronaveRepository repository;
	
	@Transactional(readOnly = true)
	public Page<AeronaveDTO> findAll(Pageable pageable){
		Page<Aeronave> page = repository.findAll(pageable);
		return page.map(x -> new AeronaveDTO(x));
	}
	
	@Transactional(readOnly = true)
	public Page<AeronaveDTO> find(SpecificationTemplate.AeronaveSpec spec, Pageable pageable){
		Page<Aeronave> page = repository.findAll(spec, pageable);
		return page.map(x -> new AeronaveDTO(x));
	}
	
	@Transactional(readOnly = true)
	public Aeronave getEntity(Long id){
		Optional<Aeronave> obj = repository.findById(id);
		Aeronave entity = obj.orElseThrow(() ->  new ResourceNotFoundException("Id da aeronave não encontrado: " + id));
		return entity;
	}
	
	
	@Transactional(readOnly = true)
	public AeronaveDTO findById(Long id){		
		Aeronave entity = getEntity(id);
		return new AeronaveDTO(entity);
	}
	
	@Transactional
	public AeronaveDTO insert(AeronaveDTO dto) {
		Aeronave entity = new Aeronave();		
		BeanUtils.copyProperties(dto, entity);		
		entity.setCreated(LocalDateTime.now(ZoneId.of("UTC")));
		entity.setUpdated(LocalDateTime.now(ZoneId.of("UTC")));
		entity = repository.save(entity);
		return new AeronaveDTO(entity);		
	}
	
	
	@Transactional
	public AeronaveDTO update(Long id, AeronaveDTO dto) {		
		Aeronave entity = getEntity(id);
		copyDtoToEntity(dto, entity);		
		entity.setUpdated(LocalDateTime.now(ZoneId.of("UTC")));
		entity = repository.save(entity);
		return new AeronaveDTO(entity);		
	}
	
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integridade do banco violada");
		}
	}
	
	private void copyDtoToEntity(AeronaveDTO dto, Aeronave entity) {
		entity.setNome(dto.getNome());
		entity.setMarca(dto.getMarca());
		entity.setAno(dto.getAno());
		entity.setDescricao(dto.getDescricao());		
		entity.setVendido(dto.isVendido());
	}
	
	
	
	public Long getCountByVendido() {
		return repository.getCountByVendido(true);
	}
	
	public List<AeronaveMinProjection> getCountByMarca(){
		return repository.getCountByMarca();
	}
	
	public Long getCountLastWeek() {
		return repository.getCountLastWeek();
	}
	

}
