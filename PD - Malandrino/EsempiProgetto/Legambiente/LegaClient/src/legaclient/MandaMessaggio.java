/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legaclient;

import java.util.Scanner;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import server.Circolo;
import server.CircoloEJBRemote;

/**
 *
 * @author CORSO_PD
 */
public class MandaMessaggio {
    public static void main(String[] args) throws NamingException{
        Context ctx = (Context) new InitialContext();
        CircoloEJBRemote ejb = (CircoloEJBRemote) ctx.lookup("java:global/LegaEJB/CircoloEJB!server.CircoloEJBRemote");
        Scanner scan = new Scanner(System.in);
        int id;
        String nome;
        String responsabile;
        
        System.out.println("Inserisci l'id del circolo:");
        id = scan.nextInt(); scan.nextLine();
        System.out.println("Inserisci il nome del circolo:");
        nome = scan.nextLine();
        System.out.println("Inserisci il responsabile del circolo:");
        responsabile = scan.nextLine();
        
        Circolo c = ejb.findById(id);
        c.setNome(nome);
        c.setResponsabile(responsabile);
        
        ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/javaee7/ConnectionFactory");
        Destination topic = (Destination) ctx.lookup("jms/javaee7/Topic");
        
        try(JMSContext jmsContext = cf.createContext()){
            jmsContext.createProducer().send(topic, c);
            System.out.println("Circolo inviato");
        }
    }
}
