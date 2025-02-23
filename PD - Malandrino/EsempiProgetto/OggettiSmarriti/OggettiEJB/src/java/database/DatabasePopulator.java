package database;

import java.sql.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import server.*;

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
    private OggettoEJB ejb;
    
    private Oggetto o1, o2, o3;
    
    @PostConstruct
    public void populateDB(){
        o1 = new Oggetto(1, "Smartphone Samsung", "Elettronica", "Stazione Centrale", new Date(2024, 01, 12), false);
        o2 = new Oggetto(2, "Passaporto", "Documenti", "Aeroporto", new Date(2024, 01, 14), true);
        o3 = new Oggetto(3, "Orologio Rolex", "Gioielli", "Centro Commerciale", new Date(2024, 01, 20), false);
        
        ejb.createOggetto(o1); 
        ejb.createOggetto(o2);
        ejb.createOggetto(o3);
    }
    
    @PreDestroy
    public void clearDB(){
        ejb.deleteOggetto(o1);
        ejb.deleteOggetto(o2);
        ejb.deleteOggetto(o3);
    }
}