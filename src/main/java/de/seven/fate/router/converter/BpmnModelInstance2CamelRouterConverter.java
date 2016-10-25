package de.seven.fate.router.converter;

import de.seven.fate.bpmn.BpmnService;
import de.seven.fate.converter.AbstractConverter;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.lang.Validate;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.springframework.stereotype.Component;

@Component
public class BpmnModelInstance2CamelRouterConverter extends AbstractConverter<RouteBuilder, BpmnModelInstance> {

    private final BpmnService bpmnService;
    private final BpmnModelInstance2RouteDefinitionConverter bpmnModelInstance2RouteDefinitionConverter;

    public BpmnModelInstance2CamelRouterConverter(BpmnService bpmnService, BpmnModelInstance2RouteDefinitionConverter bpmnModelInstance2RouteDefinitionConverter) {
        this.bpmnService = bpmnService;
        this.bpmnModelInstance2RouteDefinitionConverter = bpmnModelInstance2RouteDefinitionConverter;
    }

    @Override
    public RouteBuilder convert(BpmnModelInstance orig) {
        if (orig == null) {
            return null;
        }

        RouteBuilder routeBuilder = createRouteBuilder();

        update(routeBuilder, orig);

        return routeBuilder;
    }

    @Override
    public void update(RouteBuilder dest, BpmnModelInstance orig) {
        Validate.notNull(dest);

        if (orig == null) {
            return;
        }

        StartEvent startEvent = bpmnService.getStartEvent(orig);
        bpmnModelInstance2RouteDefinitionConverter.convert(startEvent, dest);
    }

    private RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {

            }
        };
    }
}
