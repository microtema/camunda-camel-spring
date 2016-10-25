package de.seven.fate.bpmn;

import de.seven.fate.CamundaCamelSpringApplication;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.Query;
import org.camunda.bpm.model.bpmn.instance.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CamundaCamelSpringApplication.class)
public class BpmnServiceTest {


    @Inject
    BpmnService sut;

    @Inject
    private ResourcePatternResolver resourceLoader;

    @Test
    public void getStartEvent() {
        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance();

        notNull(bpmnModelInstance);

        StartEvent startEvent = sut.getStartEvent(bpmnModelInstance);

        assertNotNull(startEvent);

        assertEquals("StartEvent_1", startEvent.getId());
        assertEquals("Start Event", startEvent.getName());
    }

    @Test
    public void getMessageEventDefinition() {

        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance();

        StartEvent startEvent = sut.getStartEvent(bpmnModelInstance);

        Collection<EventDefinition> eventDefinitions = startEvent.getEventDefinitions();

        assertNotNull(eventDefinitions);
        assertFalse(eventDefinitions.isEmpty());
        assertEquals(1, eventDefinitions.size());

        EventDefinition eventDefinition = eventDefinitions.iterator().next();

        assertNotNull(eventDefinition);

        MessageEventDefinition messageEventDefinition = (MessageEventDefinition) eventDefinition;

        Message message = messageEventDefinition.getMessage();

        assertNotNull(message);
        assertEquals("file://C:/data/input", message.getName());
    }

    @Test
    public void getStartEvents() {
        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance();

        assertNotNull(bpmnModelInstance);

        List<StartEvent> startEvents = sut.getStartEvents(bpmnModelInstance);

        assertNotNull(startEvents);
        assertFalse(startEvents.isEmpty());
        assertEquals(1, startEvents.size());
    }

    @Test
    public void getOutgoingSequenceFlows() {

        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance();

        StartEvent startEvent = sut.getStartEvent(bpmnModelInstance);

        Collection<SequenceFlow> outgoing = sut.getOutgoingSequenceFlows(startEvent);

        assertFalse(outgoing.isEmpty());
        assertEquals(1, outgoing.size());
    }

    @Test
    public void getOutgoingSequenceFlow() {

        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance();

        StartEvent startEvent = sut.getStartEvent(bpmnModelInstance);

        SequenceFlow sequenceFlow = sut.getOutgoingSequenceFlow(startEvent);

        assertNotNull(sequenceFlow);
        assertEquals("SequenceFlow_from_start_to_system_task", sequenceFlow.getId());
        assertNull(sequenceFlow.getName());
    }

    @Test
    public void getTarget() throws Exception {

        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance();

        StartEvent startEvent = sut.getStartEvent(bpmnModelInstance);

        SequenceFlow sequenceFlow = sut.getOutgoingSequenceFlow(startEvent);

        FlowNode target = sequenceFlow.getTarget();

        assertNotNull(target);
        assertEquals("Task_system_task", target.getId());
        assertEquals("System Task", target.getName());
    }

    @Test
    public void getOutgoingSequenceFlowFromTarget() throws Exception {

        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance();

        StartEvent startEvent = sut.getStartEvent(bpmnModelInstance);

        SequenceFlow incomingSequenceFlow = sut.getOutgoingSequenceFlow(startEvent);

        FlowNode systemTask = incomingSequenceFlow.getTarget();

        SequenceFlow outgoingSequenceFlow = sut.getOutgoingSequenceFlow(systemTask);

        assertNotNull(outgoingSequenceFlow);
        assertEquals("SequenceFlow_from_system_task_to_end_event", outgoingSequenceFlow.getId());
        assertNull(outgoingSequenceFlow.getName());
    }

    @Test
    public void getTargetFromOutgoingSequenceFlow() throws Exception {

        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance();

        StartEvent startEvent = sut.getStartEvent(bpmnModelInstance);

        SequenceFlow incomingSequenceFlow = sut.getOutgoingSequenceFlow(startEvent);

        FlowNode systemTask = incomingSequenceFlow.getTarget();

        SequenceFlow outgoingSequenceFlow = sut.getOutgoingSequenceFlow(systemTask);

        FlowNode target = outgoingSequenceFlow.getTarget();

        assertNotNull(target);
        assertEquals("EndEvent_success", target.getId());
        assertEquals("End Event", target.getName());
    }

    @Test
    public void getSucceedingNodes() throws Exception {

        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance();

        StartEvent startEvent = sut.getStartEvent(bpmnModelInstance);

        Query<FlowNode> succeedingNodes = startEvent.getSucceedingNodes();

        List<FlowNode> flowNodes = succeedingNodes.list();

        assertEquals(1, flowNodes.size());

        FlowNode flowNode = succeedingNodes.singleResult();

        assertNotNull(flowNode);

        assertEquals("Task_system_task", flowNode.getId());
        assertEquals("System Task", flowNode.getName());
    }

    @Test
    public void getMessageEndEventDefinition() {

        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance();

        EndEvent endEvent = sut.getEndEvent(bpmnModelInstance);

        Collection<EventDefinition> eventDefinitions = endEvent.getEventDefinitions();

        assertNotNull(eventDefinitions);
        assertFalse(eventDefinitions.isEmpty());
        assertEquals(1, eventDefinitions.size());

        EventDefinition eventDefinition = eventDefinitions.iterator().next();

        assertNotNull(eventDefinition);

        MessageEventDefinition messageEventDefinition = (MessageEventDefinition) eventDefinition;

        Message message = messageEventDefinition.getMessage();

        assertNotNull(message);
        assertEquals("seda:output", message.getName());
    }

    private BpmnModelInstance getBpmnModelInstance() {

        Resource resource = resourceLoader.getResource("classpath:/processes/simple.bpmn");

        try {
            InputStream inputStream = resource.getInputStream();

            BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(inputStream);

            return bpmnModelInstance;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
