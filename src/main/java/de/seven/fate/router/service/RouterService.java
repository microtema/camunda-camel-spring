package de.seven.fate.router.service;

import com.e2open.model.converter.bpmn.dsl.BpmnModelInstance2CamelRouterConverter;
import com.e2open.model.converter.xml.camel.dsl.RouteBuilder2XmlConverter;
import lombok.extern.java.Log;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.commons.lang3.Validate;
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

    @Inject
    private RouteBuilder2XmlConverter routeBuilder2XmlConverter;

    @PostConstruct
    private void init() {

        addAllRoutes();

        log.info("Start All Routes");
    }

    public void startRoute(String routeId) {
        Validate.notNull(routeId);

        try {
            camelContext.startRoute(routeId);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to start route: " + routeId);
        }
    }

    public void stopRoute(String routeId) {
        Validate.notNull(routeId);

        try {
            camelContext.stopRoute(routeId);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to stop route: " + routeId);
        }
    }

    public void removeRoute(String routeId) {
        Validate.notNull(routeId);

        try {
            camelContext.removeRoute(routeId);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to remove route: " + routeId);
        }
    }

    private void addAllRoutes() {
        getRouteBuilders().forEach(this::addRoutes);
    }

    private void addRoutes(RouteBuilder routeBuilder) {
        try {

            log.info("addRoutes: " + routeBuilder2XmlConverter.convert(routeBuilder));

            camelContext.addRoutes(routeBuilder);

            List<RouteDefinition> routes = camelContext.getRouteDefinitions();

            routes.forEach(route -> {
                log.info("RouteDefinition: " + route.getId() + " : " + route.getDescriptionText() + " : " + route.getAutoStartup());
            });

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

            Resource[] resources = resourceLoader.getResources("classpath:/messageflows/*.bpmn");

            return Arrays.asList(resources).stream().map(this::getBpmnModelInstance).collect(Collectors.toList());

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<String> listRoutes() {

        return camelContext.getRoutes().stream().map(route -> route.getId()).collect(Collectors.toList());
    }
}
