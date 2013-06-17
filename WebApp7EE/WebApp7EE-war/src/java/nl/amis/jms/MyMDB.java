/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amis.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import nl.amis.events.CDIJmsEvent;

/**
 *
 * @author edwin
 */


@MessageDriven(  
  mappedName = "java:global/jms/DemoQueue",
  activationConfig = {
     @ActivationConfigProperty( propertyName = "destinationType" ,
                                propertyValue ="javax.jms.Queue")
  }
)
public class MyMDB implements MessageListener {

    @Inject
    @CDIJmsEvent
    Event<Message> jmsEvent;
    
    public void onMessage(Message msg) {
        try {
            int deliveryCount = msg.getIntProperty("JMSXDeliveryCount");
            String body = msg.getBody(String.class);
            System.out.println("MyMDB attempt: "+ deliveryCount +" received: "+body);
            
            jmsEvent.fire(msg);
            
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
}
