package de.seven.fate.router.converter;

import de.seven.fate.bpmn.BpmnService;
import de.seven.fate.converter.AbstractMetaConverter;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.commons.lang.Validate;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.springframework.stereotype.Component;

@Component
public class StartEvent2RouteDefinitionConverter extends AbstractMetaConverter<RouteDefinition, StartEvent, RouteBuilder> {

    private final BpmnService bpmnService;

    public StartEvent2RouteDefinitionConverter(BpmnService bpmnService) {
        this.bpmnService = bpmnService;
    }

    @Override
    public RouteDefinition convert(StartEvent orig, RouteBuilder meta) {
        if (orig == null) {
            return null;
        }

        Validate.notNull(meta);

        String uri = bpmnService.getEventUri(orig);

        return meta.from(uri);
    }


}
