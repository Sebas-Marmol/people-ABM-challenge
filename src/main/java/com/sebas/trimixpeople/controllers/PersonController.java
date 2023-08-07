package com.sebas.trimixpeople.controllers;

import com.sebas.trimixpeople.enums.Documento;
import com.sebas.trimixpeople.exceptions.EntityValidationException;
import com.sebas.trimixpeople.models.Persona;
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

    private Response addCorsHeaders(Response.ResponseBuilder response) {
        return response.header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PATCH").header("Access-Control-Allow-Headers", "Content-Type").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPeople(@QueryParam("nombre") String nombre, @QueryParam("documento") Documento documento) {
        return addCorsHeaders(Response.ok(personService.getPeople(nombre, documento)).status(200));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertPerson(Persona person) {
        try {
            return addCorsHeaders(Response.ok(personService.insertPerson(person)).status(201));
        } catch (EntityValidationException e) {
            return addCorsHeaders(Response.ok(e.getResponseDetail()).status(e.getStatus()));
        }
    }

    @PATCH
    @Path("{id : \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(Persona person, @PathParam("id") Long id) {
        try {
            return addCorsHeaders(Response.ok(personService.updatePerson(id, person)).status(200));
        } catch (EntityValidationException e) {
            return addCorsHeaders(Response.ok(e.getResponseDetail()).status(e.getStatus()));
        }
    }

    @DELETE
    @Path("/{id : \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("id") Long id) {
        try {
            personService.deletePerson(id);
        } catch (EntityValidationException e) {
            return addCorsHeaders(Response.ok(e.getResponseDetail()).status(e.getStatus()));
        }
        return addCorsHeaders(Response.status(200));
    }

    @OPTIONS
    @Produces(MediaType.APPLICATION_JSON)
    public Response options() {
        return getCorsHeaders();
    }

    @OPTIONS
    @Path("/{id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response patchOptions() {
        return getCorsHeaders();
    }

    private Response getCorsHeaders() {
        return Response.ok("")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PATCH, DELETE, OPTIONS")
                .build();
    }
}
