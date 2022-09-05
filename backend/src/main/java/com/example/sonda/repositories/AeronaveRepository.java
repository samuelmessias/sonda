package com.example.sonda.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.sonda.entities.Aeronave;
import com.example.sonda.projections.AeronaveMinProjection;

public interface AeronaveRepository extends JpaRepository<Aeronave, Long>, JpaSpecificationExecutor<Aeronave>{
	
	
	@Query("SELECT COUNT(a) FROM Aeronave a WHERE a.vendido= :vendido")
    long getCountByVendido(boolean vendido);
	
	
	@Query(value = "SELECT marca, COUNT(*)qto FROM TB_AERONAVE GROUP BY marca ORDER BY marca ASC", nativeQuery=true)
	List<AeronaveMinProjection> getCountByMarca();
	
	@Query(value = "SELECT COUNT(*) qto FROM TB_AERONAVE WHERE created  > now() - 7", nativeQuery=true)
    long getCountLastWeek();
	

}
