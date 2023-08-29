package com.tomlegodais.app;

import com.tomlegodais.app.repository.DummyToolRepository;
import com.tomlegodais.app.util.SafeFunction;
import com.tomlegodais.shared.model.Tool;
import com.tomlegodais.shared.service.CheckoutService;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;

public class ToolRentalApplication {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var app = new ToolRentalApplication(scanner);
        var tool = app.prompt(
                "Please enter tool rental code: ",
                DummyToolRepository::findByCode,
                Objects::nonNull,
                "The provided tool code was not found, please try again.");

        var rentalDayCount = app.prompt(
                "Please enter number of rental days: ",
                Integer::parseInt,
                value -> value >= 1,
                "You must rent the tool for at least one day, please try again.");

        var discountPercent = app.prompt(
                "Please enter discount percentage: ",
                Integer::parseInt,
                value -> value >= 0 && value <= 100,
                "Discount percentage must be between 0 and 100, please try again.");

        var checkoutDate = app.prompt(
                "Please enter checkout date (MM/dd/yy): ",
                SafeFunction.of(s -> LocalDate.parse(s, Constants.DATE_FORMATTER)),
                Objects::nonNull,
                "Checkout date must be in the format MM/dd/yy, please try again.");

        app.checkout(tool, rentalDayCount, discountPercent, checkoutDate);
    }

    private final Scanner scanner;
    private final CheckoutService service = new CheckoutService();

    public ToolRentalApplication(Scanner scanner) {
        this.scanner = scanner;
    }

    private <T> T prompt(String message, SafeFunction<String, T> parser, Predicate<T> validator, String validationMessage) {
        T result;
        do {
            System.out.print(message);
            result = parser.apply(scanner.nextLine());

            if (result == null || !validator.test(result)) {
                result = null;
                System.out.println(validationMessage);
            }
        } while (result == null);
        return result;
    }

    private void checkout(Tool tool, int rentalDayCount, int discountPercent, LocalDate checkoutDate) {
        var agreement = service.checkout(tool, checkoutDate, rentalDayCount, discountPercent);
        var plainText = service.generatePlainText(agreement);

        System.out.println();
        System.out.println(plainText);
    }
}
