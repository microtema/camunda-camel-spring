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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        addAllRoutes();

        log.info("Start All Routes");
    }


    private void addAllRoutes() {
        getRouteBuilders().forEach(this::addRoutes);
    }

    private void addRoutes(RouteBuilder routeBuilder) {
        try {
            camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<RouteBuilder> getRouteBuilders() {
        return modelInstance2CamelRouterConverter.convertList(getBpmnModelInstances());
    }

    private BpmnModelInstance getBpmnModelInstance(Resource resource) {

        try {
            InputStream inputStream = resource.getInputStream();

            return Bpmn.readModelFromStream(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    private List<BpmnModelInstance> getBpmnModelInstances() {

        try {

            Resource[] resources = resourceLoader.getResources("classpath:/processes/*-message.bpmn");

            return Arrays.asList(resources).stream().map(this::getBpmnModelInstance).collect(Collectors.toList());

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
