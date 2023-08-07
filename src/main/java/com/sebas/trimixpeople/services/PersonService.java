package com.sebas.trimixpeople.services;

import com.sebas.trimixpeople.enums.Documento;
import com.sebas.trimixpeople.exceptions.EntityValidationException;
import com.sebas.trimixpeople.models.Persona;
import com.sebas.trimixpeople.repositories.PersonRepository;
import com.sebas.trimixpeople.specifications.PersonSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Persona insertPerson(Persona person) throws EntityValidationException {
        String mensajeValidacion = validarPersona(person, null);
        if (!mensajeValidacion.equals("")) {
            throw new EntityValidationException(mensajeValidacion, 400);
        }
        return personRepository.save(person);
    }

    public Persona updatePerson(Long id, Persona person) throws EntityValidationException {
        Optional<Persona> preexistente = personRepository.findById(id);
        if (!preexistente.isPresent()) {
            throw new EntityValidationException("No se encontró una persona con el Id " + id, 400);
        }
        String mensajeValidacion = validarPersona(person, id);
        if (!mensajeValidacion.equals("")) {
            throw new EntityValidationException(mensajeValidacion, 400);
        }
        person.setPerId(id);
        return personRepository.save(person);
    }

    public List<Persona> getPeople(String nombre, Documento documento) {
        Specification<Persona> spec = Specification.where(null);
        if (null != nombre) {
            //El challenge pide filtrar por nombre, opto que el mismo filtro se use como un OR en el apellido.
            spec = spec.or(PersonSpecification.byNombre((nombre))).or(PersonSpecification.byApellido(nombre));
        }
        if (null != documento) {
            spec = spec.and(PersonSpecification.byTipoDocumento(documento));
        }
        System.out.println(spec.toString());
        return personRepository.findAll(spec);
    }

    public void deletePerson(Long id) throws EntityValidationException {
        if (!personRepository.existsById(id)) {
            throw new EntityValidationException("No se encontró una persona con el Id " + id, 400);
        }
        personRepository.deleteById(id);
    }

    private String validarPersona(Persona person, Long id) {
        if (null == person.getPerTipoDocumento() || 0 == person.getPerNumeroDocumento() || isNull(person.getPerApellido()) || isNull(person.getPerNombre()) || null == person.getPerFechaNacimiento()) {
            return "Ningún valor puede ser nulo.";
        }
        List<Persona> preexistente = personRepository.findByPerTipoDocumentoAndPerNumeroDocumento(person.getPerTipoDocumento(), person.getPerNumeroDocumento());
        if (!preexistente.isEmpty()) {
            if (null == id || id != preexistente.get(0).getPerId()) {
                return "Ya existe registrada una persona con esa combinación de tipo y número de Documento.";
            }
        }
        return "";
    }

    private boolean isNull(String valor) {
        return null == valor || valor.equals("");
    }
}
