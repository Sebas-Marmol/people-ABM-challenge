package com.sebas.trimixpeople.repositories;

import com.sebas.trimixpeople.enums.Documento;
import com.sebas.trimixpeople.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

    List<Person> findByPerTipoDocumentoAndPerNumeroDocumento(Documento tipoDocumento, Long numeroDocumento);
    List<Person> findByPerTipoDocumento(Documento documento);
    List<Person> findByPerNombreContainsIgnoreCaseOrPerApellidoContainingIgnoreCase(String nombre,String apellido);
}
