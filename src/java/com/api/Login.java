/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api;

import com.model.User;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import service.UserFacadeREST;

/**
 * REST Web Service
 *
 * @author tudoraurelian
 */
@Stateless
@Path("login")
public class Login {

   
    @EJB
    private UserFacadeREST userFacadeREST;
    
    @PersistenceContext(unitName = "WebAPIPU")
    private EntityManager em;



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {

        try {
            System.out.println(username);
            System.out.println(password);
            User user = authenticate(username, password);
            String token = issueToken(user);
            return Response.ok(token).build();
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    
    
    @GET
    @Secured
    @Produces("application/json")
    @Path("access")
    public Response securedMethod(@Context SecurityContext securityContext) {
        return Response.ok().build();

    }

    private User authenticate(String username, String password) {

        User user = userFacadeREST.find(username, password);
        System.out.println("user found");
        return user;

    }

    private String issueToken(User user) {
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        user.setToken(token);
        userFacadeREST.edit(user);
        return token;
    }


}
