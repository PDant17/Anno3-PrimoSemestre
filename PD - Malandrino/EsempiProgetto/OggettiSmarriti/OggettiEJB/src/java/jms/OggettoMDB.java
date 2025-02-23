package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import server.Oggetto;
import server.OggettoEJB;

//Alternativamente: @MessageDriven (mappedName = "jms/javaee7/Topic")
//Non sono sicuro se per lei faccia differenza
@MessageDriven(activationConfig ={
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "jms/javaee7/Topic"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/javaee7/Topic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "jms/javaee7/Topic"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
})
public class OggettoMDB implements MessageListener{
    @Inject
    OggettoEJB ejb;
    @Inject
    Event<Oggetto> event;
    
    @Override
    public void onMessage(Message msg){
        try{
            MessageWrapper mw = msg.getBody(MessageWrapper.class);
            Oggetto o = new Oggetto();
            o = ejb.findById(mw.getId());
            if((o.getRestituito()) != (mw.getRestituito())){
                o.setRestituito(mw.getRestituito());
                ejb.modifyOggetto(o);
                event.fire(o);
            }
        }catch(JMSException e){
            e.printStackTrace();
        }
    }
}