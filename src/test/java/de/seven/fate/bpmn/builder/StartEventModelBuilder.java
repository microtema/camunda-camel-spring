package de.seven.fate.bpmn.builder;

import de.seven.fate.bpmn.enums.ExtensionPropertyType;
import de.seven.fate.model.builder.AbstractModelBuilder;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;

import java.util.UUID;

public class StartEventModelBuilder extends AbstractModelBuilder<StartEvent> {

    BpmnModelInstance modelInstance = Bpmn.createEmptyModel();

    @Override
    public StartEvent min() {

        StartEvent startEvent = minImpl();

        ExtensionElements extensionElements = modelInstance.newInstance(ExtensionElements.class);

        CamundaProperties camundaProperties = extensionElements.addExtensionElement(CamundaProperties.class);

        CamundaProperty camundaProperty = createElement(camundaProperties, ExtensionPropertyType.ID.getName(), CamundaProperty.class);

        camundaProperty.setCamundaName(ExtensionPropertyType.ID.getName());
        camundaProperty.setCamundaValue(randomUri());

        camundaProperty = createElement(camundaProperties, ExtensionPropertyType.URI.getName(), CamundaProperty.class);

        camundaProperty.setCamundaName(ExtensionPropertyType.URI.getName());
        camundaProperty.setCamundaValue(randomUri());

        startEvent.setExtensionElements(extensionElements);

        return startEvent;
    }

    private StartEvent minImpl() {

        Definitions definitions = modelInstance.newInstance(Definitions.class);

        definitions.setTargetNamespace("http://camunda.org/examples");
        modelInstance.setDefinitions(definitions);

        Process process = createElement(definitions, "process-with-one-task", Process.class);

        return createElement(process, "start", StartEvent.class);
    }

    private String randomUri() {
        return UUID.randomUUID().toString();
    }

    protected <T extends BpmnModelElementInstance> T createElement(BpmnModelElementInstance parentElement, String id, Class<T> elementClass) {
        T element = modelInstance.newInstance(elementClass);
        element.setAttributeValue("id", id, true);
        parentElement.addChildElement(element);
        return element;
    }

    public SequenceFlow createSequenceFlow(Process process, FlowNode from, FlowNode to) {

        String identifier = from.getId() + "-" + to.getId();

        SequenceFlow sequenceFlow = createElement(process, identifier, SequenceFlow.class);
        process.addChildElement(sequenceFlow);

        sequenceFlow.setSource(from);
        from.getOutgoing().add(sequenceFlow);

        sequenceFlow.setTarget(to);
        to.getIncoming().add(sequenceFlow);

        return sequenceFlow;
    }


}
