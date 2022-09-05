package com.example.sonda.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.sonda.entities.Aeronave;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public class SpecificationTemplate {
	
	@And({
		@Spec(path = "nome", spec = Like.class),
		@Spec(path = "marca", spec = Equal.class),
		@Spec(path = "ano", spec = Equal.class),
		@Spec(path = "vendido", spec = Equal.class)		
	})
	public interface AeronaveSpec extends Specification<Aeronave>{}
}
