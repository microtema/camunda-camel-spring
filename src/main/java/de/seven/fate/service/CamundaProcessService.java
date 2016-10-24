package de.seven.fate.service;

import lombok.extern.java.Log;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Log
@Service
public class CamundaProcessService {

    @Inject
    private RuntimeService runtimeService;

    private String processInstanceKey = "purchaseOrderRequest";

    @PostConstruct
    public void startProcess() {

        try {

            runtimeService.startProcessInstanceByKey(processInstanceKey);
        } catch (Exception e) {
            log.info("unable to start Process-Instance By Key: " + processInstanceKey);
        }
    }
}
