/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amis.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConnectionFactoryDefinition;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.Queue;
import nl.amis.events.CDIJmsEvent;

/**
 *
 * @author edwin
 */
@JMSConnectionFactoryDefinition(
        name = "java:global/jms/MyConnectionFactory",
        maxPoolSize = 30,
        minPoolSize = 20,
        properties = {
    "addressList=mq://localhost:7676",
    "reconnectEnabled=true"
})
@JMSDestinationDefinition(
        name = "java:global/jms/DemoQueue",
        interfaceName = "javax.jms.Queue",
        destinationName="DemoQueue",
        description="My Demo Queue")

@Stateless
public class JmsSessionBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Inject
    @JMSConnectionFactory("java:global/jms/MyConnectionFactory")
    JMSContext context;
    
    @Resource(mappedName="java:global/jms/DemoQueue")
    Queue demoQueue2;
    
    public String sendMessage(String message) {
        try {
            context.createProducer().send(demoQueue2, message);
        } catch (JMSRuntimeException ex) {
            ex.printStackTrace();
        }
        return "Message sent";
    }
    
    
    public void onJMSMessage(@Observes @CDIJmsEvent Message msg) {
        try {
            String body = msg.getBody(String.class);
            System.out.println("JmsSessionBean received cdi event: "+body);
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
       
    }
    
}
