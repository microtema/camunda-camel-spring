package de.seven.fate.configuration;

import com.e2open.model.converter.bpmn.dsl.BpmnModelInstance2CamelRouterConverter;
import com.e2open.model.converter.bpmn.dsl.BpmnModelInstance2CamelRouterGroupPatternListConverter;
import com.e2open.model.converter.bpmn.dsl.CamelRouterGroupPattern2CamelRouterConverter;
import com.e2open.model.converter.bpmn.dsl.event.EndEvent2RouteDefinitionConverter;
import com.e2open.model.converter.bpmn.dsl.event.EndEventGroupPattern2RouteBuilderConverter;
import com.e2open.model.converter.bpmn.dsl.event.StartEvent2RouteDefinitionConverter;
import com.e2open.model.converter.bpmn.dsl.event.StartEventGroupPattern2RouteBuilderConverter;
import com.e2open.model.converter.bpmn.dsl.multicast.MulticastGroupPattern2RouteBuilderConverter;
import com.e2open.model.converter.bpmn.dsl.multicast.ParallelGateway2MulticastDefinitionConverter;
import com.e2open.model.converter.bpmn.dsl.pipeline.PipelineGroupPattern2RouteBuilderConverter;
import com.e2open.model.converter.bpmn.dsl.router.ExclusiveGateway2ChoiceDefinitionConverter;
import com.e2open.model.converter.bpmn.dsl.router.MessageRouterGroupPattern2RouteBuilderConverter;
import com.e2open.model.converter.bpmn.dsl.task.*;
import com.e2open.model.converter.xml.camel.dsl.CamelContext2XmlConverter;
import com.e2open.model.converter.xml.camel.dsl.RouteBuilder2XmlConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfiguration {


    @Bean
    public BpmnModelInstance2CamelRouterConverter bpmnModelInstance2CamelRouterConverter() {
        return new BpmnModelInstance2CamelRouterConverter(bpmnModelInstance2CamelRouterGroupPatternListConverter(), camelRouterGroupPattern2CamelRouterConverter());
    }

    private BpmnModelInstance2CamelRouterGroupPatternListConverter bpmnModelInstance2CamelRouterGroupPatternListConverter() {
        return new BpmnModelInstance2CamelRouterGroupPatternListConverter();
    }

    private StartEventGroupPattern2RouteBuilderConverter startEventGroupPattern2RouteBuilderConverter() {
        return new StartEventGroupPattern2RouteBuilderConverter(startEvent2RouteDefinitionConverter());
    }

    private StartEvent2RouteDefinitionConverter startEvent2RouteDefinitionConverter() {
        return new StartEvent2RouteDefinitionConverter();
    }

    private PipelineGroupPattern2RouteBuilderConverter pipelineGroupPattern2RouteBuilderConverter() {

        return new PipelineGroupPattern2RouteBuilderConverter(serviceTask2RouteDefinitionConverter());
    }

    private ServiceTask2RouteDefinitionConverter serviceTask2RouteDefinitionConverter() {
        return new ServiceTask2RouteDefinitionConverter(
                new ServiceTask2MessageFilterConverter(),
                new ServiceTask2MessageTranslatorConverter(),
                new ServiceTask2ContentFilterConverter(),
                new MessageContentEnricherProcessor());
    }

    private EndEventGroupPattern2RouteBuilderConverter endEventGroupPattern2RouteBuilderConverter() {
        return new EndEventGroupPattern2RouteBuilderConverter(new EndEvent2RouteDefinitionConverter());
    }

    private ParallelGateway2MulticastDefinitionConverter parallelGateway2MulticastDefinitionConverter() {
        return new ParallelGateway2MulticastDefinitionConverter();
    }

    private MulticastGroupPattern2RouteBuilderConverter multicastGroupPattern2RouteBuilderConverter() {
        return new MulticastGroupPattern2RouteBuilderConverter(parallelGateway2MulticastDefinitionConverter(), pipelineGroupPattern2RouteBuilderConverter(), endEventGroupPattern2RouteBuilderConverter());
    }

    private MessageRouterGroupPattern2RouteBuilderConverter messageRouterGroupPattern2RouteBuilderConverter() {
        return new MessageRouterGroupPattern2RouteBuilderConverter(new ExclusiveGateway2ChoiceDefinitionConverter(), pipelineGroupPattern2RouteBuilderConverter(), endEventGroupPattern2RouteBuilderConverter());
    }

    private CamelRouterGroupPattern2CamelRouterConverter camelRouterGroupPattern2CamelRouterConverter() {
        return new CamelRouterGroupPattern2CamelRouterConverter(startEventGroupPattern2RouteBuilderConverter()
                , pipelineGroupPattern2RouteBuilderConverter()
                , multicastGroupPattern2RouteBuilderConverter()
                , messageRouterGroupPattern2RouteBuilderConverter()
                , endEventGroupPattern2RouteBuilderConverter());

    }

    @Bean
    public RouteBuilder2XmlConverter routeBuilder2XmlConverter() {
        return new RouteBuilder2XmlConverter(new CamelContext2XmlConverter());
    }

}
