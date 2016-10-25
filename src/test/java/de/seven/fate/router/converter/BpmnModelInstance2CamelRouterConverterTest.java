package de.seven.fate.router.converter;

import de.seven.fate.bpmn.BpmnService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BpmnModelInstance2CamelRouterConverterTest {

    BpmnService bpmnService = new BpmnService();

    BpmnModelInstance2CamelRouterConverter sut = new BpmnModelInstance2CamelRouterConverter(bpmnService, new BpmnModelInstance2RouteDefinitionConverter(bpmnService));

    BpmnModelInstance bpmnModelInstance = getBpmnModelInstance();

    @Test
    public void createOnNull() {

        assertNull(sut.convert(null));
    }

    @Test
    public void create() {

        RouteBuilder routeBuilder = sut.convert(bpmnModelInstance);

        routeBuilder.from("seda:test").to("seda:mock");

        assertNotNull(routeBuilder);

        List<RouteDefinition> routes = routeBuilder.getRouteCollection().getRoutes();
    }

    private BpmnModelInstance getBpmnModelInstance() {

        InputStream inputStream = BpmnModelInstance2CamelRouterConverterTest.class.getResourceAsStream("/processes/simple.bpmn");

        return Bpmn.readModelFromStream(inputStream);
    }
}
