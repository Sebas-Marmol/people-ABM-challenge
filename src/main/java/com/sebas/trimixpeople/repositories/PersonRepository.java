package com.sebas.trimixpeople.repositories;

import com.sebas.trimixpeople.enums.Documento;
import com.sebas.trimixpeople.models.Persona;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Persona, Long>, JpaSpecificationExecutor<Persona> {

    List<Persona> findByPerTipoDocumentoAndPerNumeroDocumento(Documento tipoDocumento, Long numeroDocumento);

    List<Persona> findAll(Specification<Persona> spec);
}
