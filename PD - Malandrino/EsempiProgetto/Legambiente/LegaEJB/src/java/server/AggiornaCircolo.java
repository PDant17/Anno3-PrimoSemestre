/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author CORSO_PD
 */
@MessageDriven(activationConfig ={
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "jms/javaee7/Topic"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/javaee7/Topic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "jms/javaee7/Topic"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
})
public class AggiornaCircolo implements MessageListener{
    @Inject
    private CircoloEJB ejb;
    
    public AggiornaCircolo(){
        
    }
    
    public void onMessage(Message message){
        Circolo c1, c2;
        try{
            c1 = message.getBody(Circolo.class);
            c2 = ejb.findById(c1.getId());
            String nuovoResponsabile = c1.getResponsabile();
            String vecchioResponsabile = c2.getResponsabile();
            if(!vecchioResponsabile.equals(nuovoResponsabile)){
                System.out.println("Responsabile cambiato in " + nuovoResponsabile);
            }
            ejb.modifyCircolo(c1);
        } catch (JMSException e){
            e.printStackTrace();
        }
    }
}
