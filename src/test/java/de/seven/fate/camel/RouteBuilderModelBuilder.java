package de.seven.fate.camel;

import de.seven.fate.model.builder.AbstractModelBuilder;
import org.apache.camel.builder.RouteBuilder;

public class RouteBuilderModelBuilder extends AbstractModelBuilder<RouteBuilder> {

    @Override
    public RouteBuilder min() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {

            }
        };
    }
}
