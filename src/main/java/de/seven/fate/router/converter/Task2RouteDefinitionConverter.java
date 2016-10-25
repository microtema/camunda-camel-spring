package de.seven.fate.router.converter;

import de.seven.fate.bpmn.BpmnService;
import de.seven.fate.converter.AbstractMetaConverter;
import org.apache.camel.model.RouteDefinition;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.camunda.bpm.model.xml.type.attribute.Attribute;
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

        ModelElementType elementType = orig.getElementType();

        Attribute<String> delegateExpression = (Attribute<String>) elementType.getAttribute("delegateExpression");

        String value = delegateExpression.getValue(orig);

        return meta.to(bindBean(value));
    }

    private String bindBean(String delegateExpression) {

        String beanName = getBeanName(delegateExpression);

        return "bean:" + beanName + "?method=execute(${body}, ${headers})";
    }

    private String getBeanName(String delegateExpression) {

        return delegateExpression.replace("$", "").replace("{", "").replace("}", "");
    }

}
