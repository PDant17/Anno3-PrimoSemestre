/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author CORSO_PD
 */
@Remote
public interface CircoloEJBRemote {
    void createCircolo(Circolo c);
    void modifyCircolo(Circolo c);
    void deleteCircolo(Circolo c);
    
    List<Circolo> findAll();
    Circolo findById(int id);
    List<Circolo> findByRegione(String regione);
}
