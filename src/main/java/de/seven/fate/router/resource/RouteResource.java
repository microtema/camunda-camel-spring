package de.seven.fate.router.resource;

import de.seven.fate.router.service.RouterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;


@RestController
public class RouteResource {

    @Inject
    private RouterService service;

    @RequestMapping("/")
    public List<String> listRoutes() {
        return service.listRoutes();
    }

    @RequestMapping("/start/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void startRoutes(@PathVariable("id") String id) {
        service.startRoute(id);
    }

    @RequestMapping("/stop/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void stopRoutes(@PathVariable("id") String id) {
        service.stopRoute(id);
    }

    @RequestMapping("/remove/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeRoutes(@PathVariable("id") String id) {
        service.removeRoute(id);
    }
}
