package de.seven.fate.router.converter;

import de.seven.fate.bpmn.BpmnService;
import de.seven.fate.bpmn.builder.StartEventModelBuilder;
import de.seven.fate.camel.RouteBuilderModelBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.FromDefinition;
import org.apache.camel.model.RouteDefinition;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class StartEvent2RouteDefinitionConverterTest {

    BpmnService bpmnService = new BpmnService();

    StartEvent2RouteDefinitionConverter sut = new StartEvent2RouteDefinitionConverter(bpmnService);

    StartEventModelBuilder builder = new StartEventModelBuilder();

    StartEvent startEvent = builder.min();

    RouteBuilder routeBuilder = new RouteBuilderModelBuilder().min();

    @Test
    public void convertNullOnNull() {
        assertNull(sut.convert(null, routeBuilder));
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertThrowIllegalArgumentExceptionOnNullRouteBuilder() {
        sut.convert(startEvent, null);
    }

    @Test
    public void convertBlank() {

        RouteDefinition routeDefinition = sut.convert(startEvent, routeBuilder);

        assertNotNull(routeDefinition);

        List<FromDefinition> inputs = routeDefinition.getInputs();
        assertNotNull(inputs);
        assertFalse(inputs.isEmpty());
        assertEquals(1, inputs.size());

        FromDefinition fromDefinition = inputs.get(0);
        assertNotNull(fromDefinition);

        assertEquals(bpmnService.getEventUri(startEvent), fromDefinition.getUri());
    }

}
