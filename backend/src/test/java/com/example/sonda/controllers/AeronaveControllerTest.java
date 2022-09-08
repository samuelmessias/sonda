package com.example.sonda.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.example.sonda.dto.AeronaveDTO;
import com.example.sonda.factors.AeronaveFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AeronaveControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long existingId;
	private Long noExistingId;
	private AeronaveDTO dto;
	
	@BeforeEach
	void setUp()throws Exception {
		existingId = 1L;
		noExistingId = 1000L;
		
		dto = AeronaveFactory.insrtDTO();
	}
	
	@Test
	public void findAllShouldReturnSortedPageWhenSortByNome() throws Exception {
		ResultActions result = mockMvc.perform(get("/aeronaves?page=0&size=10&sort=id")			
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content[0].nome").value("E2-190"));
		result.andExpect(jsonPath("$.content[1].nome").value("737-100"));
		result.andExpect(jsonPath("$.content[2].nome").value("KC-390"));		
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		ResultActions result = mockMvc.perform(get("/aeronaves/{id}", noExistingId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findByIdShouldReturn200WhenIdExist() throws Exception {
		ResultActions result = mockMvc.perform(get("/aeronaves/{id}", existingId)				
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(1L));
		result.andExpect(jsonPath("$.nome").value("E2-190"));
	}
	
	@Test
	public void getCountByMarcaShouldReturn200() throws Exception {
		ResultActions result = mockMvc.perform(get("/aeronaves/qto/marcas", existingId)				
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$[0].marca").value("Airbus"));
		result.andExpect(jsonPath("$[0].qto").value(1));
		result.andExpect(jsonPath("$[1].marca").value("Boeing"));
		result.andExpect(jsonPath("$[1].qto").value(1));
		result.andExpect(jsonPath("$[2].marca").value("Embraer"));
		result.andExpect(jsonPath("$[2].qto").value(2));
	}
	
	@Test
	public void getCountLastWeekShouldReturn200() throws Exception {
		ResultActions result = mockMvc.perform(get("/aeronaves/semana", existingId)				
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$").value(1));
	}
	
	@Test
	public void getCountByVendidoShouldReturn200() throws Exception {
		ResultActions result = mockMvc.perform(get("/aeronaves/vendidas", existingId)				
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$").value(2));
	}
	
	
	@Test
	public void insertShouldReturn422WhenNomeBlank() throws Exception {
		dto.setNome("");
		
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result = mockMvc.perform(post("/aeronaves")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("nome"));
		result.andExpect(jsonPath("$.errors[0].message").value("Campo de preenchimento obrigatório"));
	}
	
	@Test
	public void deleteShouldReturnNoContentWhenIdExists() throws Exception {		
		ResultActions result = 
				mockMvc.perform(delete("/aeronaves/{id}", existingId)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {		
		ResultActions result = 
				mockMvc.perform(delete("/aeronaves/{id}", noExistingId)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	

}
