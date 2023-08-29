package com.tomlegodais.shared.service;

import com.tomlegodais.shared.model.RentalAgreement;
import com.tomlegodais.shared.model.Tool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckoutService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");

    public RentalAgreement checkout(Tool tool, LocalDate checkoutDate, int rentalDays, int discountPercent) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental days must be greater than or equal to 1");
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100");
        }

        var agreement = new RentalAgreement();
        agreement.setTool(tool);
        agreement.setCheckoutDate(checkoutDate);
        agreement.setRentalDays(rentalDays);
        agreement.setDailyRentalCharge(tool.getType().getPriceInfo().getDailyCharge());

        var dueDate = checkoutDate.plusDays(rentalDays);
        var chargeDays = this.calculateChargeDays(tool, checkoutDate, dueDate);
        var prediscountCharge = this.calculatePrediscountCharge(tool, chargeDays);
        var discountAmount = this.calculateDiscountAmount(prediscountCharge, discountPercent);
        var finalCharge = prediscountCharge.subtract(discountAmount);

        agreement.setDueDate(dueDate);
        agreement.setChargeDays(chargeDays);
        agreement.setPreDiscountCharge(prediscountCharge);
        agreement.setDiscountPercent(discountPercent);
        agreement.setDiscountAmount(discountAmount);
        agreement.setFinalCharge(finalCharge);
        return agreement;
    }

    private int calculateChargeDays(Tool tool, LocalDate checkoutDate, LocalDate dueDate) {
        return this.getDatesBetween(checkoutDate, dueDate)
                .stream()
                .mapToInt(date -> this.isChargeable(tool, date) ? 1 : 0)
                .sum();
    }

    private List<LocalDate> getDatesBetween(LocalDate d1, LocalDate d2) {
        return Stream.iterate(d1.plusDays(1), date -> !date.isAfter(d2), date -> date.plusDays(1))
                .collect(Collectors.toList());
    }

    private boolean isChargeable(Tool tool, LocalDate date) {
        var dayOfWeek = date.getDayOfWeek();
        var priceInfo = tool.getType().getPriceInfo();
        if (this.isHoliday(date)) {
            return priceInfo.isHolidayCharge();
        }

        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY
                ? priceInfo.isWeekendCharge()
                : priceInfo.isWeekdayCharge();
    }

    private boolean isHoliday(LocalDate date) {
        if (this.isIndependenceDay(date)) {
            return true;
        }

        if (this.isFirstWeekOfSeptember(date)) {
            return date.getDayOfWeek() == DayOfWeek.MONDAY;
        }

        return false;
    }

    private boolean isIndependenceDay(LocalDate date) {
        if (date.getMonth() != Month.JULY) {
            return false;
        }

        var dayOfMonth = date.getDayOfMonth();
        var dayOfWeek = date.getDayOfWeek();
        if (dayOfMonth == 4 && dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
            return true;
        }

        if (dayOfWeek == DayOfWeek.FRIDAY && dayOfMonth == 3) {
            return true;
        }

        return dayOfWeek == DayOfWeek.MONDAY && dayOfMonth == 5;
    }

    private boolean isFirstWeekOfSeptember(LocalDate date) {
        return date.getMonth() == Month.SEPTEMBER && date.getDayOfMonth() <= 7;
    }

    private BigDecimal calculatePrediscountCharge(Tool tool, int chargeDays) {
        return tool.getType().getPriceInfo().getDailyCharge()
                .multiply(BigDecimal.valueOf(chargeDays))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDiscountAmount(BigDecimal prediscountCharge, int discountPercent) {
        return prediscountCharge.multiply(BigDecimal.valueOf(discountPercent))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public String generatePlainText(RentalAgreement agreement) {
        return "Tool code: " + agreement.getTool().getCode() + "\n" +
                "Tool type: " + agreement.getTool().getType().getName() + "\n" +
                "Tool brand: " + agreement.getTool().getBrand().getName() + "\n" +
                "Rental days: " + agreement.getRentalDays() + "\n" +
                "Check out date: " + agreement.getCheckoutDate().format(DATE_FORMATTER) + "\n" +
                "Due date: " + agreement.getDueDate().format(DATE_FORMATTER) + "\n" +
                "Daily rental charge: $" + agreement.getDailyRentalCharge() + "\n" +
                "Charge days: " + agreement.getChargeDays() + "\n" +
                "Pre-discount charge: $" + agreement.getPreDiscountCharge() + "\n" +
                "Discount percent: " + agreement.getDiscountPercent() + "%\n" +
                "Discount amount: $" + agreement.getDiscountAmount() + "\n" +
                "Final charge: $" + agreement.getFinalCharge();
    }
}
