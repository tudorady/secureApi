/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api;

import com.model.Role;
import com.model.User;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.ws.rs.NotAuthorizedException;
import service.UserFacadeREST;

/**
 *
 * @author tudoraurelian
 */
@RequestScoped
public class AuthenticatedUserProducer {
 
    @Produces
    @RequestScoped
    @AuthenticatedUser
    private User authenticatedUser;

    @EJB
    private UserFacadeREST userFacadeREST;
    
    public void handleAuthenticationEvent(@Observes @AuthenticatedUser String token) {
        System.out.println("com.api.AuthenticatedUserProducer.handleAuthenticationEvent()");
        this.authenticatedUser = findUser(token);
    }
    
    public User findUser(String token){
         try {
            User user = userFacadeREST.find(token);
            return user;
        } catch (Exception e) {
            throw new NotAuthorizedException("Invalid token");
        }
    }

   
}
