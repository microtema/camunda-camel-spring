package de.seven.fate.service;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Service
public class CamundaProcessService {

    @Inject
    private RuntimeService runtimeService;

    @PostConstruct
    public void startProcess() {
        runtimeService.startProcessInstanceByKey("purchaseOrderRequest");
    }
}
