/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import static server.Circolo.*;

/**
 *
 * @author CORSO_PD
 */
@WebService
@Stateless
@LocalBean
public class CircoloEJB implements CircoloEJBRemote {
    
    @Inject
    private EntityManager em;
    
    @Override
    public void createCircolo(Circolo c){
        em.persist(c);
    }
    
    @Override
    public void modifyCircolo(Circolo c){
        em.merge(c);
    }
    
    @Override
    public void deleteCircolo(Circolo c){
        em.remove(c);
    }
    
    @Override
    public List<Circolo> findAll(){
        return em.createNamedQuery(FIND_ALL, Circolo.class).getResultList();
    }
    
    @Override
    public Circolo findById(int id){
        return em.createNamedQuery(FIND_BY_ID, Circolo.class).setParameter("id", id).getSingleResult();
    }
    
    @Override
    public List<Circolo> findByRegione(String regione){
        return em.createNamedQuery(FIND_BY_REGIONE, Circolo.class).setParameter("regione", regione).getResultList();
    }
}