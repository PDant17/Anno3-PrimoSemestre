package jms;

import javax.enterprise.event.Observes;
import server.Oggetto;

public class ModificaStatoNotification {
    public void notify(@Observes Oggetto o){
        System.out.println("Oggetto restituito al proprietario: " + o.getDescrizione());
        if(o.getCategoria().equals("Documenti")){
            System.out.println("Attenzione, documenti importanti restituiti");
        }
    }
}