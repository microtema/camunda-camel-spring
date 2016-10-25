package de.seven.fate.router.converter;

import de.seven.fate.bpmn.BpmnService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.FromDefinition;
import org.apache.camel.model.RouteDefinition;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

public class BpmnModelInstance2RouteDefinitionConverterTest {

    StartEvent2RouteDefinitionConverter sut;

    BpmnService bpmnService;

    BpmnModelInstance bpmnModelInstance;

    StartEvent startEvent;
    RouteBuilder routeBuilder = new RouteBuilder() {
        @Override
        public void configure() throws Exception {

        }
    };

    @Before
    public void setUp() throws Exception {
        bpmnService = new BpmnService();
        sut = new StartEvent2RouteDefinitionConverter(bpmnService);
        bpmnModelInstance = getBpmnModelInstance();
        startEvent = bpmnService.getStartEvent(bpmnModelInstance);
    }

    @Test
    public void createOnNull() {

        assertNull(sut.convert(null, routeBuilder));
    }

    @Test
    public void create() {

        RouteDefinition routeDefinition = sut.convert(startEvent, routeBuilder);

        assertNotNull(routeDefinition);

        List<FromDefinition> inputs = routeDefinition.getInputs();

        assertFalse(inputs.isEmpty());
        assertEquals(1, inputs.size());

        FromDefinition fromDefinition = inputs.iterator().next();

        assertNotNull(fromDefinition);
        assertEquals(bpmnService.getStartEventMessage(startEvent).getName(), fromDefinition.getUri());
    }

    private BpmnModelInstance getBpmnModelInstance() {

        InputStream inputStream = BpmnModelInstance2CamelRouterConverterTest.class.getResourceAsStream("/processes/simple.bpmn");

        return Bpmn.readModelFromStream(inputStream);
    }
}
