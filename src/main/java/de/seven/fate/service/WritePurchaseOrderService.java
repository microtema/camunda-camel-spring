package de.seven.fate.service;


import de.seven.fate.converter.Converter;
import de.seven.fate.converter.PurchaseOrderDto2XmlFileConverter;
import de.seven.fate.dto.PurchaseOrderDto;
import de.seven.fate.model.PurchaseOrder;
import lombok.extern.java.Log;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.File;
import java.util.List;

@Log
@Component
public class WritePurchaseOrderService implements JavaDelegate {


    @Inject
    private PurchaseOrderDto2XmlFileConverter purchaseOrderDto2XmlFileConverter;

    @Inject
    private Converter<PurchaseOrderDto, PurchaseOrder> purchaseOrder2PurchaseOrderDtoConverter;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String outputDir = (String) delegateExecution.getVariable("outputDir");

        Object purchaseOrders = delegateExecution.getVariable("purchaseOrders");

        List<PurchaseOrderDto> purchaseOrderDtos = purchaseOrder2PurchaseOrderDtoConverter.convertList((List<PurchaseOrder>) purchaseOrders);

        List<File> files = purchaseOrderDto2XmlFileConverter.convertList(purchaseOrderDtos, outputDir);

        files.stream().forEach(file -> log.info("Write file to: " + file));
    }
}

