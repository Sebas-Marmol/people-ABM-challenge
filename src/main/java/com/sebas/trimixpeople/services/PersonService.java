package com.sebas.trimixpeople.services;

import com.sebas.trimixpeople.enums.Documento;
import com.sebas.trimixpeople.exceptions.EntityValidationException;
import com.sebas.trimixpeople.models.Person;
import com.sebas.trimixpeople.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person insertPerson(Person person) throws EntityValidationException {
        String mensajeValidacion = validarPersona(person, null);
        if (!mensajeValidacion.equals("")) {
            throw new EntityValidationException(mensajeValidacion, 400);
        }
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person person) throws EntityValidationException {
        Optional<Person> preexistente = personRepository.findById(id);
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

    public List<Person> getPeople(String nombre, Documento documento) {
        if (null != nombre) {
            //El challenge pide filtrar por nombre, opto que el mismo filtro se use como un OR en el apellido.
            return personRepository.findByPerNombreContainsIgnoreCaseOrPerApellidoContainingIgnoreCase(nombre, nombre);
        }
        if (null != documento) {
            return personRepository.findByPerTipoDocumento(documento);
        }

        return personRepository.findAll();
    }

    public void deletePerson(Long id) throws EntityValidationException {
        if (!personRepository.existsById(id)) {
            throw new EntityValidationException("No se encontró una persona con el Id " + id, 400);
        }
        personRepository.deleteById(id);
    }

    private String validarPersona(Person person, Long id) {
        if (isNull(person.getPerTipoDocumento().name()) || 0 == person.getPerNumeroDocumento() || isNull(person.getPerApellido()) || isNull(person.getPerNombre()) || null == person.getPerFechaNacimiento()) {
            return "Ningún valor puede ser nulo.";
        }
        Date today = new Date();
        System.out.println(today);
        if (person.getPerFechaNacimiento().after(today)) {
            return "La fecha de nacimiento no puede ser posterior al día de hoy.";
        }
        List<Person> preexistente = personRepository.findByPerTipoDocumentoAndPerNumeroDocumento(person.getPerTipoDocumento(), person.getPerNumeroDocumento());
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
