package de.seven.fate.router.converter;

import de.seven.fate.bpmn.BpmnService;
import de.seven.fate.converter.AbstractMetaConverter;
import org.apache.camel.model.RouteDefinition;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.Message;
import org.springframework.stereotype.Component;

@Component
public class EndEvent2RouteDefinitionConverter extends AbstractMetaConverter<RouteDefinition, EndEvent, RouteDefinition> {

    private final BpmnService bpmnService;

    public EndEvent2RouteDefinitionConverter(BpmnService bpmnService) {
        this.bpmnService = bpmnService;
    }

    @Override
    public RouteDefinition convert(EndEvent orig, RouteDefinition meta) {
        if (orig == null) {
            return null;
        }

        Message message = bpmnService.getEndEventMessage(orig);

        if (message == null) {
            return null;
        }

        String toUri = message.getName();

        return meta.to(toUri);
    }

}