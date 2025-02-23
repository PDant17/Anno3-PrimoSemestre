/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legaclient;

import java.util.List;
import java.util.Scanner;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import server.Circolo;
import server.CircoloEJBRemote;

/**
 *
 * @author CORSO_PD
 */
public class LegaClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NamingException{
        Context ctx = (Context) new InitialContext();
        CircoloEJBRemote ejb = (CircoloEJBRemote) ctx.lookup("java:global/LegaEJB/CircoloEJB!server.CircoloEJBRemote");
        Scanner scan = new Scanner(System.in);
        String regione;
        
        stampa("TUTTI I CIRCOLI", ejb.findAll());
        
        System.out.println("Inserisci la regione di cui cercare i circoli:");
        regione = scan.nextLine();
        
        stampa("CIRCOLI DI " + regione, ejb.findByRegione(regione));
    }
    
    public static void stampa(String msg, List<Circolo> lista){
        System.out.println("***" + msg + "***");
        lista.forEach(System.out::println);
        System.out.println("\n\n");
    }
}
