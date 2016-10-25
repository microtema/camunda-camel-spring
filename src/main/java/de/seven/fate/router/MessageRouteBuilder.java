package de.seven.fate.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MessageRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("seda:output").bean("messageService", "executeOutput").to("file://C:/data/done");
    }
}
