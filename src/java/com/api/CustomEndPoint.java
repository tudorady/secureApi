/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api;

import com.model.Role;
import com.model.User;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author tudoraurelian
 */
@RequestScoped
@Path("access")
public class CustomEndPoint {

    @Inject
    @AuthenticatedUser
    User authenticatedUser;

    @GET
    @Secured(Role.USER)
    @Produces("application/json")
    public Response securedMethod(@Context SecurityContext securityContext) {
        return Response.ok(authenticatedUser.getUsername()).build();

    }
}
