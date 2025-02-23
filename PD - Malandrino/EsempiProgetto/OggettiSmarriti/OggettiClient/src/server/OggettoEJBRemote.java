package server;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface OggettoEJBRemote {
    void createOggetto(Oggetto s);
    void modifyOggetto(Oggetto s);
    void deleteOggetto(Oggetto s);

    List<Oggetto> findAll();
    List<Oggetto> findByCategoria(String categoria);
    List<Oggetto> findByLuogo(String luogo);
    Oggetto findById(int id);
}