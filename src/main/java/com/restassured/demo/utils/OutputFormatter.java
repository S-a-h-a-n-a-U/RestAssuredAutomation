package com.restassured.demo.utils;

import io.restassured.response.Response;

public class OutputFormatter {

    public static void printBookingHeader(int bookingNumber,
                                          String customerName) {

        System.out.println();
        System.out.println("==============================================================");
        System.out.println("                 BOOKING EXECUTION #" + bookingNumber);
        System.out.println("                 CUSTOMER : " + customerName);
        System.out.println("==============================================================");
    }

    public static void printStep(String title) {

        System.out.println();
        System.out.println("--------------------------------------------------------------");
        System.out.println(title);
        System.out.println("--------------------------------------------------------------");
    }

    public static void printApiDetails(String endpoint,
                                       int expectedStatus,
                                       Response response) {

        if (response.getStatusCode() == expectedStatus) {
            System.out.printf("%-22s : PASS%n", "Result");
        } else {
            System.out.printf("%-22s : FAIL%n", "Result");
        }

        System.out.println();
    }

    public static void printBooking(Response response) {

        System.out.printf("%-22s : %s%n",
                "Firstname",
                response.jsonPath().getString("firstname"));

        System.out.printf("%-22s : %s%n",
                "Lastname",
                response.jsonPath().getString("lastname"));

        System.out.printf("%-22s : %d%n",
                "Total Price",
                response.jsonPath().getInt("totalprice"));

        System.out.printf("%-22s : %s%n",
                "Deposit Paid",
                response.jsonPath().getBoolean("depositpaid"));

        System.out.printf("%-22s : %s%n",
                "Check-In",
                response.jsonPath().getString("bookingdates.checkin"));

        System.out.printf("%-22s : %s%n",
                "Check-Out",
                response.jsonPath().getString("bookingdates.checkout"));

        System.out.printf("%-22s : %s%n",
                "Additional Needs",
                response.jsonPath().getString("additionalneeds"));

        System.out.println();
    }

    public static void printUpdatedField(String field,
                                         Object oldValue,
                                         Object newValue) {

        System.out.println();
        System.out.println("Updated Field : " + field);
        System.out.println("Old Value     : " + oldValue);
        System.out.println("New Value     : " + newValue);
        System.out.println("----------------------------------------------");
        System.out.println();
    }

    public static void printDeleteSuccess() {

        System.out.println();
        System.out.println("Booking Deleted Successfully.");
    }

    public static void printVerifyDelete() {

        System.out.println();
        System.out.println("Booking Not Found.");
        System.out.println("Delete Verification Passed.");
    }

    public static void printSummary(int bookings,
                                    int apiCalls,
                                    int passed,
                                    int failed,
                                    long executionTime) {

        System.out.println();
        System.out.println("==============================================================");
        System.out.println("                    EXECUTION SUMMARY");
        System.out.println("==============================================================");

        System.out.printf("%-25s : %d%n", "Bookings Executed", bookings);
        System.out.printf("%-25s : %d%n", "Total API Calls", apiCalls);
        System.out.printf("%-25s : %d%n", "Passed", passed);
        System.out.printf("%-25s : %d%n", "Failed", failed);
        System.out.printf("%-25s : %d ms%n", "Execution Time", executionTime);

        System.out.println("==============================================================");
    }
}