package de.seven.fate.service;


import com.e2open.model.converter.Converter;
import de.seven.fate.converter.yaml.PurchaseOrderDto2YamlFileConverter;
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
public class YamlWritePurchaseOrderService implements JavaDelegate {


    @Inject
    private PurchaseOrderDto2YamlFileConverter purchaseOrderDto2YamlFileConverter;

    @Inject
    private Converter<PurchaseOrderDto, PurchaseOrder> purchaseOrder2PurchaseOrderDtoConverter;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String outputDir = (String) delegateExecution.getVariable("outputDir");

        Object purchaseOrders = delegateExecution.getVariable("purchaseOrders");

        List<PurchaseOrderDto> purchaseOrderDtos = purchaseOrder2PurchaseOrderDtoConverter.convertList((List<PurchaseOrder>) purchaseOrders);

        List<File> files = purchaseOrderDto2YamlFileConverter.convertList(purchaseOrderDtos, outputDir);

        files.stream().forEach(file -> log.info("Write Yaml-File to: " + file));
    }
}

