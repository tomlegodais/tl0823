package com.tomlegodais.app;

import com.tomlegodais.shared.model.PriceInfo;
import com.tomlegodais.shared.model.ToolBrand;
import com.tomlegodais.shared.model.ToolType;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public interface Constants {

    interface ToolBrands {
        ToolBrand STIHL = new ToolBrand("Stihl");
        ToolBrand WERNER = new ToolBrand("Werner");
        ToolBrand DEWALT = new ToolBrand("DeWalt");
        ToolBrand RIDGID = new ToolBrand("Ridgid");
    }

    interface ToolTypes {
        ToolType CHAINSAW = new ToolType("Chainsaw", new PriceInfo(BigDecimal.valueOf(1.49), true, true, false));
        ToolType LADDER = new ToolType("Ladder", new PriceInfo(BigDecimal.valueOf(1.99), true, true, false));
        ToolType JACKHAMMER = new ToolType("Jackhammer", new PriceInfo(BigDecimal.valueOf(2.99), true, false, false));
    }

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");
}
