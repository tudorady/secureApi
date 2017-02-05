/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.model.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author tudoraurelian
 */
@Stateless
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "WebAPIPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    public User find(String username, String password) {
        TypedQuery<User> query = em.createNamedQuery("User.findByUsernameAndPassword", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        if (query.getResultList().isEmpty()) {
            System.out.println("NO RESULT");
            return null;
        } else {
            System.out.println("RESULT OK");
            return query.getSingleResult();
        }
    }

    public User find(String token) {
        TypedQuery<User> query = em.createNamedQuery("User.findByToken", User.class);
        query.setParameter("token", token);
        return query.getSingleResult();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
