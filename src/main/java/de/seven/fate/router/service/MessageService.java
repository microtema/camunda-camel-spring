package de.seven.fate.router.service;

import lombok.extern.java.Log;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Log
@Service
public class MessageService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("execute delegateExecution");
    }

    public void execute(Object body, Map<String, Object> headers) throws Exception {
        log.info("execute body&header");
    }

    public void executeOutput(Object body, Object headers) {
        log.info("executeOutput MessageService");
    }
}
