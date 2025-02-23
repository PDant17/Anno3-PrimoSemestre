package oggettiwebclient;

import java.util.List;
import server.Oggetto;

//Per qualche motivo, qui voleva che specificassi anche 'server.'
public class OggettiWebClient {
    public static void main(String[] args) {
        List<Oggetto> lista = findByCategoria("Elettronica");
        System.out.println("Numero di oggetti appartenenti alla categoria elettronica: " + lista.size());
        //Usare i getter per stampare, il toString implementato non viene visto ed usa quello di default automaticamente
        System.out.println("Elenco degli oggetti: \n");
        for(Oggetto o : lista){
            System.out.println(o.toString());
        }
    }
    
    private static java.util.List<server.Oggetto> findByCategoria(java.lang.String arg0){
        server.OggettoEJBService service = new server.OggettoEJBService();
        server.OggettoEJB port = service.getOggettoEJBPort();
        return port.findByCategoria(arg0);
    }
}