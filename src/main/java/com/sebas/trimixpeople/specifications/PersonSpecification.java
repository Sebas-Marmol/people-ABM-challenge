package com.sebas.trimixpeople.specifications;

import com.sebas.trimixpeople.enums.Documento;
import com.sebas.trimixpeople.models.Persona;
import org.springframework.data.jpa.domain.Specification;

public class PersonSpecification {
    public static Specification<Persona> byNombre(String nombre) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("perNombre")), "%" + nombre.toLowerCase() + "%");
    }

    public static Specification<Persona> byApellido(String apellido) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("perApellido")), "%" + apellido.toLowerCase() + "%");
    }

    public static Specification<Persona> byTipoDocumento(Documento documento) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("perTipoDocumento"), documento);
    }
}
