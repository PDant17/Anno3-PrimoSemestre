package oggetticlient;

import java.util.List;
import java.util.Scanner;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import server.Oggetto;
import server.OggettoEJBRemote;

public class OggettiClient {
    public static void main(String[] args) throws NamingException{
        Context ctx = (Context) new InitialContext();
        OggettoEJBRemote ejb = (OggettoEJBRemote) ctx.lookup("java:global/OggettiEJB/OggettoEJB!server.OggettoEJBRemote");
        Scanner scan = new Scanner(System.in);
        String luogo;
        
        List<Oggetto> lista1 = ejb.findAll();
        System.out.println("Stampa di tutti gli oggetti smarriti: \n");
        for(Oggetto o : lista1) {
            System.out.println(o.toString());
        }
        
        System.out.println("Inserisci il luogo di ritrovamento desiderato: ");
        luogo = scan.nextLine();
        List<Oggetto> lista2 = ejb.findByLuogo(luogo);
        System.out.println("Gli oggetti ritrovati nel luogo specificato sono: \n");
        for(Oggetto o : lista2){
            System.out.println(o.toString());
        }
    }   
}