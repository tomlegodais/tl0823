package com.tomlegodais.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.google.common.truth.Truth.assertThat;

public class ToolRentalApplicationTest {

    private ByteArrayOutputStream outContent;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testMainApplicationFlow() {
        // Simulate user input
        var input = "LADW\n3\n10\n07/02/20\n";
        var inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);

        // Run the application
        ToolRentalApplication.main(new String[]{});

        // Verify the output
        var expectedOutput = "Tool code: LADW\n" +
                "Tool type: Ladder\n" +
                "Tool brand: Werner\n" +
                "Rental days: 3\n" +
                "Check out date: 07/02/20\n" +
                "Due date: 07/05/20\n" +
                "Daily rental charge: $1.99\n" +
                "Charge days: 2\n" +
                "Pre-discount charge: $3.98\n" +
                "Discount percent: 10%\n" +
                "Discount amount: $0.40\n" +
                "Final charge: $3.58";

        var outContentString = outContent.toString().split("\n", 2)[1].trim();
        assertThat(outContentString).isEqualTo(expectedOutput);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
}
