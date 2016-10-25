package de.seven.fate.router.converter;

import de.seven.fate.bpmn.BpmnService;
import de.seven.fate.converter.AbstractMetaConverter;
import org.apache.camel.model.RouteDefinition;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.springframework.stereotype.Component;

@Component
public class Task2RouteDefinitionConverter extends AbstractMetaConverter<RouteDefinition, Task, RouteDefinition> {

    private final BpmnService bpmnService;

    public Task2RouteDefinitionConverter(BpmnService bpmnService) {
        this.bpmnService = bpmnService;
    }

    @Override
    public RouteDefinition convert(Task orig, RouteDefinition meta) {
        if (orig == null) {
            return null;
        }

        String delegateExpression = bpmnService.getDelegateExpression(orig);
        String uri = bindBean(delegateExpression);

        return meta.to(uri);
    }

    private String bindBean(String delegateExpression) {

        String beanName = getBeanName(delegateExpression);

        return "bean:" + beanName + "?method=execute(${body}, ${headers})";
    }

    private String getBeanName(String delegateExpression) {

        return delegateExpression.replace("$", "").replace("{", "").replace("}", "");
    }

}
