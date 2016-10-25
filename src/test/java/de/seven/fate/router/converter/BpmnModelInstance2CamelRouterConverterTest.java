package de.seven.fate.router.converter;

import de.seven.fate.bpmn.BpmnService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.FromDefinition;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.ToDefinition;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

public class BpmnModelInstance2CamelRouterConverterTest {

    BpmnModelInstance2CamelRouterConverter sut;

    EndEvent2RouteDefinitionConverter endEvent2RouteDefinitionConverter;

    Task2RouteDefinitionConverter task2RouteDefinitionConverter;

    BpmnService bpmnService;

    BpmnModelInstance bpmnModelInstance;

    @Before
    public void setUp() throws Exception {

        bpmnService = new BpmnService();
        endEvent2RouteDefinitionConverter = new EndEvent2RouteDefinitionConverter(bpmnService);
        task2RouteDefinitionConverter = new Task2RouteDefinitionConverter(bpmnService);
        sut = new BpmnModelInstance2CamelRouterConverter(bpmnService, new StartEvent2RouteDefinitionConverter(bpmnService), endEvent2RouteDefinitionConverter, task2RouteDefinitionConverter);
        bpmnModelInstance = getBpmnModelInstance();
    }

    @Test
    public void createOnNull() {

        assertNull(sut.convert(null));
    }

    @Test
    public void create() {

        RouteBuilder routeBuilder = sut.convert(bpmnModelInstance);

        assertNotNull(routeBuilder);

        List<RouteDefinition> routes = routeBuilder.getRouteCollection().getRoutes();

        assertEquals(1, routes.size());

        RouteDefinition routeDefinition = routes.get(0);

        assertNotNull(routeDefinition);

        List<FromDefinition> inputs = routeDefinition.getInputs();
        assertEquals(1, inputs.size());
        FromDefinition fromDefinition = inputs.get(0);
        assertNotNull(fromDefinition);
        assertEquals("file://C:/data/input", fromDefinition.getUri());

        List<ProcessorDefinition<?>> outputs = routeDefinition.getOutputs();
        assertEquals(2, outputs.size());

        ToDefinition definition = (ToDefinition) outputs.get(0);
        assertNotNull(definition);
        assertEquals("bean:messageService?method=execute(${body}, ${headers})", definition.getUri());

        ToDefinition toDefinition = (ToDefinition) outputs.get(1);
        assertNotNull(toDefinition);
        assertEquals("seda:output", toDefinition.getUri());
    }

    private BpmnModelInstance getBpmnModelInstance() {

        InputStream inputStream = BpmnModelInstance2CamelRouterConverterTest.class.getResourceAsStream("/processes/validate-message.bpmn");

        return Bpmn.readModelFromStream(inputStream);
    }
}
