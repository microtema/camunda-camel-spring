package de.seven.fate.router.converter;

import de.seven.fate.bpmn.BpmnService;
import de.seven.fate.converter.AbstractConverter;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.commons.lang.Validate;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.springframework.stereotype.Component;

@Component
public class BpmnModelInstance2CamelRouterConverter extends AbstractConverter<RouteBuilder, BpmnModelInstance> {

    private final BpmnService bpmnService;
    private final StartEvent2RouteDefinitionConverter bpmnModelInstance2RouteDefinitionConverter;
    private final EndEvent2RouteDefinitionConverter endEvent2RouteDefinitionConverter;
    private final Task2RouteDefinitionConverter task2RouteDefinitionConverter;

    public BpmnModelInstance2CamelRouterConverter(BpmnService bpmnService, StartEvent2RouteDefinitionConverter bpmnModelInstance2RouteDefinitionConverter, EndEvent2RouteDefinitionConverter endEvent2RouteDefinitionConverter, Task2RouteDefinitionConverter task2RouteDefinitionConverter) {
        this.bpmnService = bpmnService;
        this.bpmnModelInstance2RouteDefinitionConverter = bpmnModelInstance2RouteDefinitionConverter;
        this.endEvent2RouteDefinitionConverter = endEvent2RouteDefinitionConverter;
        this.task2RouteDefinitionConverter = task2RouteDefinitionConverter;
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
        RouteDefinition routeDefinition = bpmnModelInstance2RouteDefinitionConverter.convert(startEvent, dest);

        task2RouteDefinitionConverter.convertList(bpmnService.getTasks(orig), routeDefinition);

        EndEvent endEvent = bpmnService.getEndEvent(orig);
        endEvent2RouteDefinitionConverter.convert(endEvent, routeDefinition);
    }

    private RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {

            }
        };
    }
}
