package com.sebas.trimixpeople.controllers;

import com.sebas.trimixpeople.enums.Documento;
import com.sebas.trimixpeople.exceptions.EntityValidationException;
import com.sebas.trimixpeople.models.Person;
import com.sebas.trimixpeople.services.PersonService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Path("api/people")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //El challenge pide filtrar por nombre, opto que el mismo filtro se use como un OR en el apellido.
    public Response getPeople(@QueryParam("nombre") String nombre, @QueryParam("documento") Documento documento) {
        return Response.ok(personService.getPeople(nombre, documento)).status(200).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertPerson(Person person) {
        try {
            return Response.ok(personService.insertPerson(person)).status(201).build();
        } catch (EntityValidationException e) {
            return Response.ok(e.getResponseDetail()).status(e.getStatus()).build();
        }
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(Person person, @PathParam("id") Long id) {
        try {
            return Response.ok(personService.updatePerson(id, person)).status(200).build();
        } catch (EntityValidationException e) {
            return Response.ok(e.getResponseDetail()).status(e.getStatus()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("id") Long id) {
        try {
            personService.deletePerson(id);
        } catch (EntityValidationException e) {
            return Response.ok(e.getResponseDetail()).status(e.getStatus()).build();
        }
        return Response.status(200).build();
    }
}
