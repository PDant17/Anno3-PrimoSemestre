/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import server.*;

/**
 *
 * @author CORSO_PD
 */
@Singleton
@LocalBean
@Startup
@DataSourceDefinition(
        className = "org.apache.derby.jdbc.EmbeddedDataSource40",
        name = "java:global/jdbc/EsameDS",
        user = "APP",
        password = "APP",
        properties = {"connectionAttributes=;create=true"}
)
public class DatabasePopulator {
    @Inject
    private CircoloEJB ejb;
    
    private Circolo c1, c2, c3;
    
    @PostConstruct
    public void populateDB(){
        c1 = new Circolo("Legambiente Benevento", "Antonio Basile", "legambiente.benevento@gmail.com", "Benevento", "Benevento", "Campania");
        c2 = new Circolo("Legambiente Valle dell'Irno", "Antonio D'Auria", "info@legambienteirno.it", "Baronissi", "Salerno", "Campania");
        c3 = new Circolo("Esther Ada", "Francesco Sanguedolce", "rancosangi@gmail.com", "Lampedusa", "Agrigento", "Sicilia");
        
        ejb.createCircolo(c1);
        ejb.createCircolo(c2);
        ejb.createCircolo(c3);
    }
    
    @PreDestroy
    public void clearDB(){
        ejb.deleteCircolo(c1);
        ejb.deleteCircolo(c2);
        ejb.deleteCircolo(c3);
    }
    
}
