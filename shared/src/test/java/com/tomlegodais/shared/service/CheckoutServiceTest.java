package com.tomlegodais.shared.service;

import com.tomlegodais.shared.model.PriceInfo;
import com.tomlegodais.shared.model.Tool;
import com.tomlegodais.shared.model.ToolBrand;
import com.tomlegodais.shared.model.ToolType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckoutServiceTest {

    private final CheckoutService checkoutService = new CheckoutService();

    @Test
    public void testCheckout_ScenarioOne() {
        var tool = new Tool("JAKR",
                new ToolType("Jackhammer", new PriceInfo(BigDecimal.valueOf(2.99), true, false, false)),
                new ToolBrand("Ridgid"));

        var thrown = assertThrows(
                IllegalArgumentException.class,
                () -> checkoutService.checkout(tool, LocalDate.of(2015, Month.SEPTEMBER, 3), 5, 101));

        assertThat(thrown.getMessage()).isEqualTo("Discount percent must be between 0 and 100");
    }

    @Test
    public void testCheckout_ScenarioTwo() {
        var tool = new Tool("LADW",
                new ToolType("Ladder", new PriceInfo(BigDecimal.valueOf(1.99), true, true, false)),
                new ToolBrand("Werner"));

        var agreement = checkoutService.checkout(tool, LocalDate.of(2020, Month.JULY, 2), 3, 10);

        assertThat(agreement.getTool().getCode()).isEqualTo("LADW");
        assertThat(agreement.getTool().getType().getName()).isEqualTo("Ladder");
        assertThat(agreement.getTool().getBrand().getName()).isEqualTo("Werner");
        assertThat(agreement.getRentalDays()).isEqualTo(3);
        assertThat(agreement.getCheckoutDate()).isEqualTo(LocalDate.of(2020, Month.JULY, 2));
        assertThat(agreement.getDueDate()).isEqualTo(LocalDate.of(2020, Month.JULY, 5));
        assertThat(agreement.getDailyRentalCharge()).isEqualTo(new BigDecimal("1.99"));
        assertThat(agreement.getChargeDays()).isEqualTo(2);
        assertThat(agreement.getPreDiscountCharge()).isEqualTo(new BigDecimal("3.98"));
        assertThat(agreement.getDiscountPercent()).isEqualTo(10);
        assertThat(agreement.getDiscountAmount()).isEqualTo(new BigDecimal("0.40"));
        assertThat(agreement.getFinalCharge()).isEqualTo(new BigDecimal("3.58"));
    }

    @Test
    public void testCheckout_ScenarioThree() {
        var tool = new Tool("CHNS",
                new ToolType("Chainsaw", new PriceInfo(BigDecimal.valueOf(1.49), true, false, true)),
                new ToolBrand("Stihl"));

        var agreement = checkoutService.checkout(tool, LocalDate.of(2015, Month.JULY, 2), 5, 25);

        assertThat(agreement.getTool().getCode()).isEqualTo("CHNS");
        assertThat(agreement.getTool().getType().getName()).isEqualTo("Chainsaw");
        assertThat(agreement.getTool().getBrand().getName()).isEqualTo("Stihl");
        assertThat(agreement.getRentalDays()).isEqualTo(5);
        assertThat(agreement.getCheckoutDate()).isEqualTo(LocalDate.of(2015, Month.JULY, 2));
        assertThat(agreement.getDueDate()).isEqualTo(LocalDate.of(2015, Month.JULY, 7));
        assertThat(agreement.getDailyRentalCharge()).isEqualTo(new BigDecimal("1.49"));
        assertThat(agreement.getChargeDays()).isEqualTo(3);
        assertThat(agreement.getPreDiscountCharge()).isEqualTo(new BigDecimal("4.47"));
        assertThat(agreement.getDiscountPercent()).isEqualTo(25);
        assertThat(agreement.getDiscountAmount()).isEqualTo(new BigDecimal("1.12"));
        assertThat(agreement.getFinalCharge()).isEqualTo(new BigDecimal("3.35"));
    }

    @Test
    public void testCheckout_ScenarioFour() {
        var tool = new Tool("JAKD",
                new ToolType("Jackhammer", new PriceInfo(BigDecimal.valueOf(2.99), true, false, false)),
                new ToolBrand("DeWalt"));

        var agreement = checkoutService.checkout(tool, LocalDate.of(2015, Month.SEPTEMBER, 3), 6, 0);

        assertThat(agreement.getTool().getCode()).isEqualTo("JAKD");
        assertThat(agreement.getTool().getType().getName()).isEqualTo("Jackhammer");
        assertThat(agreement.getTool().getBrand().getName()).isEqualTo("DeWalt");
        assertThat(agreement.getRentalDays()).isEqualTo(6);
        assertThat(agreement.getCheckoutDate()).isEqualTo(LocalDate.of(2015, Month.SEPTEMBER, 3));
        assertThat(agreement.getDueDate()).isEqualTo(LocalDate.of(2015, Month.SEPTEMBER, 9));
        assertThat(agreement.getDailyRentalCharge()).isEqualTo(new BigDecimal("2.99"));
        assertThat(agreement.getChargeDays()).isEqualTo(3);
        assertThat(agreement.getPreDiscountCharge()).isEqualTo(new BigDecimal("8.97"));
        assertThat(agreement.getDiscountPercent()).isEqualTo(0);
        assertThat(agreement.getDiscountAmount()).isEqualTo(new BigDecimal("0.00"));
        assertThat(agreement.getFinalCharge()).isEqualTo(new BigDecimal("8.97"));
    }

    @Test
    public void testCheckout_ScenarioFive() {
        var tool = new Tool("JAKR",
                new ToolType("Jackhammer", new PriceInfo(BigDecimal.valueOf(2.99), true, false, false)),
                new ToolBrand("Ridgid"));

        var agreement = checkoutService.checkout(tool, LocalDate.of(2015, Month.JULY, 2), 9, 0);

        assertThat(agreement.getTool().getCode()).isEqualTo("JAKR");
        assertThat(agreement.getTool().getType().getName()).isEqualTo("Jackhammer");
        assertThat(agreement.getTool().getBrand().getName()).isEqualTo("Ridgid");
        assertThat(agreement.getRentalDays()).isEqualTo(9);
        assertThat(agreement.getCheckoutDate()).isEqualTo(LocalDate.of(2015, Month.JULY, 2));
        assertThat(agreement.getDueDate()).isEqualTo(LocalDate.of(2015, Month.JULY, 11));
        assertThat(agreement.getDailyRentalCharge()).isEqualTo(new BigDecimal("2.99"));
        assertThat(agreement.getChargeDays()).isEqualTo(5);
        assertThat(agreement.getPreDiscountCharge()).isEqualTo(new BigDecimal("14.95"));
        assertThat(agreement.getDiscountPercent()).isEqualTo(0);
        assertThat(agreement.getDiscountAmount()).isEqualTo(new BigDecimal("0.00"));
        assertThat(agreement.getFinalCharge()).isEqualTo(new BigDecimal("14.95"));
    }

    @Test
    public void testCheckout_ScenarioSix() {
        var tool = new Tool("JAKR",
                new ToolType("Jackhammer", new PriceInfo(BigDecimal.valueOf(2.99), true, false, false)),
                new ToolBrand("Ridgid"));

        var agreement = checkoutService.checkout(tool, LocalDate.of(2020, Month.JULY, 2), 4, 50);

        assertThat(agreement.getTool().getCode()).isEqualTo("JAKR");
        assertThat(agreement.getTool().getType().getName()).isEqualTo("Jackhammer");
        assertThat(agreement.getTool().getBrand().getName()).isEqualTo("Ridgid");
        assertThat(agreement.getRentalDays()).isEqualTo(4);
        assertThat(agreement.getCheckoutDate()).isEqualTo(LocalDate.of(2020, Month.JULY, 2));
        assertThat(agreement.getDueDate()).isEqualTo(LocalDate.of(2020, Month.JULY, 6));
        assertThat(agreement.getDailyRentalCharge()).isEqualTo(new BigDecimal("2.99"));
        assertThat(agreement.getChargeDays()).isEqualTo(1);
        assertThat(agreement.getPreDiscountCharge()).isEqualTo(new BigDecimal("2.99"));
        assertThat(agreement.getDiscountPercent()).isEqualTo(50);
        assertThat(agreement.getDiscountAmount()).isEqualTo(new BigDecimal("1.50"));
        assertThat(agreement.getFinalCharge()).isEqualTo(new BigDecimal("1.49"));
    }
}
