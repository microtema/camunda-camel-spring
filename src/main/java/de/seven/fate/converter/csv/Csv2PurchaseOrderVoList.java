package de.seven.fate.converter.csv;

import com.e2open.model.converter.csv.AbstractCsv2ObjectConverter;
import de.seven.fate.vo.PurchaseOrderVo;
import org.springframework.stereotype.Component;

@Component
public class Csv2PurchaseOrderVoList extends AbstractCsv2ObjectConverter<PurchaseOrderVo> {

    public String[] getColumnMapping() {

        return new String[]{"orderNumber", "addresName", "addresEmail", "itemNumber", "itemProductName", "itemQuatity", "itemPrice", "itemGiftWrap"};
    }
}
