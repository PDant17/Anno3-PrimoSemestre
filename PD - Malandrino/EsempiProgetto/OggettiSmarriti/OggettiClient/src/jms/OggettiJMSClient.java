package jms;

import java.util.Scanner;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class OggettiJMSClient {
    public static void main(String[] args) throws NamingException{
        Context ctx = (Context) new InitialContext();
        Destination topic = (Destination) ctx.lookup("jms/javaee7/Topic");
        ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/javaee7/ConnectionFactory");
        Scanner scan = new Scanner(System.in);
        int id;
        boolean restituito;
        
        System.out.println("Inserisci l'id dell'oggetto di cui modificare lo stato: ");
        id = scan.nextInt();
        scan.nextLine();
        System.out.println("Inserisci il nuovo stato (true/false): ");
        restituito = scan.nextBoolean();
        scan.nextLine();
        MessageWrapper mw = new MessageWrapper(id, restituito);
        try(JMSContext jmsctx = cf.createContext()){
            jmsctx.createProducer().send(topic, mw);
        }
    }
}