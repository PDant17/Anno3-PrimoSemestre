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
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import static server.Oggetto.*;

/**
 *
 * @author CORSO_PD
 */
@WebService
@Stateless
@LocalBean
@Interceptors(Interceptor.class)
public class OggettoEJB implements OggettoEJBRemote {
    @Inject
    private EntityManager em;
    
    @Override
    public void createOggetto(Oggetto s){
        em.persist(s);
    }
    
    @Override
    public void modifyOggetto(Oggetto s){
        em.merge(s);
    }
    
    @Override
    public void deleteOggetto(Oggetto s){
        em.remove(s);
    }

    @Override
    public List<Oggetto> findAll(){
        return em.createNamedQuery(FIND_ALL, Oggetto.class).getResultList();
    }
    
    @Override
    public List<Oggetto> findByCategoria(String categoria){
        return em.createNamedQuery(FIND_BY_CATEGORIA, Oggetto.class).setParameter("categoria", categoria).getResultList();
    }
    
    @Override
    public List<Oggetto> findByLuogo(String luogo){
        return em.createNamedQuery(FIND_BY_LUOGO, Oggetto.class).setParameter("luogo", luogo).getResultList();
    }
    
    @Override
    public Oggetto findById(int id){
        return em.createNamedQuery(FIND_BY_ID, Oggetto.class).setParameter("id", id).getSingleResult();
    }
}
