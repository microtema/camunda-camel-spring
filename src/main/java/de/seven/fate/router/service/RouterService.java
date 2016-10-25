package de.seven.fate.router.service;

import de.seven.fate.router.converter.BpmnModelInstance2CamelRouterConverter;
import lombok.extern.java.Log;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

@Log
@Service
public class RouterService {


    @Inject
    private CamelContext camelContext;

    @Inject
    private ResourcePatternResolver resourceLoader;

    @Inject
    private BpmnModelInstance2CamelRouterConverter modelInstance2CamelRouterConverter;

    @PostConstruct
    private void init() {

        RouteBuilder routeBuilder = getRouteBuilder();

        try {

            camelContext.addRoutes(routeBuilder);

            camelContext.startAllRoutes();

        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Start camelContext");
    }

    private RouteBuilder getRouteBuilder() {
        return modelInstance2CamelRouterConverter.convert(getBpmnModelInstance());
    }

    private BpmnModelInstance getBpmnModelInstance() {

        Resource resource = resourceLoader.getResource("classpath:/processes/simple.bpmn");

        try {
            InputStream inputStream = resource.getInputStream();

            return Bpmn.readModelFromStream(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
