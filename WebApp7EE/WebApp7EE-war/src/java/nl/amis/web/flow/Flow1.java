/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amis.web.flow;

/**
 *
 * @author edwin
 */
import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.inject.Named;

import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

@Named("flow1")
public class Flow1 implements Serializable {

    private static final long serialVersionUID = -1L;

    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
 
        String flowId = "flow1";
        flowBuilder.id("", flowId);
        flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").
                markAsStartNode();
 
        flowBuilder.flowCallNode("flow1").flowReference("", "flow1a");
        flowBuilder.navigationCase().fromViewId("*").fromOutcome("exit").toViewId("/main.xhtml");
        return flowBuilder.getFlow();
    }


}
