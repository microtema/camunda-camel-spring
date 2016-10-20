package de.seven.fate.service;


import de.seven.fate.model.PurchaseOrder;
import de.seven.fate.reader.PurchaseOrdersFlatFileReader;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;


@Component
public class ReadPurchaseOrderService implements JavaDelegate {


    @Inject
    private ResourcePatternResolver resourceLoader;

    @Inject
    private PurchaseOrdersFlatFileReader ordersFlatFileReader;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String inputFileName = (String) delegateExecution.getVariable("inputFileName");

        Resource resource = resourceLoader.getResource("classpath:/purchaseOrders/" + inputFileName);

        List<PurchaseOrder> purchaseOrders = ordersFlatFileReader.readAll(resource);

        System.out.println("get resource" + purchaseOrders);

        delegateExecution.setVariable("purchaseOrders", purchaseOrders);
    }
}

