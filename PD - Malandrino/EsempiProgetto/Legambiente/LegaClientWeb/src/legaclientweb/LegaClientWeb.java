/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legaclientweb;

import java.util.List;
import server.Circolo;

/**
 *
 * @author CORSO_PD
 */
public class LegaClientWeb {
    public static void main(String[] args) {
        List<Circolo> lista = findByRegione("Campania");
        for (Circolo c : lista) {
            System.out.println(c.getNome() + c.getResponsabile() + c.getCitta());
        }
    }
    
    private static java.util.List<server.Circolo> findByRegione(java.lang.String arg0){
        server.CircoloEJBService service = new server.CircoloEJBService();
        server.CircoloEJB port = service.getCircoloEJBPort();
        return port.findByRegione(arg0);
    }    
}
