package com.restassured.demo.tests;

import com.restassured.demo.constants.APIConstants;
import com.restassured.demo.models.Booking;
import com.restassured.demo.tests.common.BaseTest;
import com.restassured.demo.utils.JsonReader;
import com.restassured.demo.utils.OutputFormatter;
import com.restassured.demo.utils.TokenUtils;
import com.restassured.demo.utils.UpdateUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BookingTest extends BaseTest {

    private int passed = 0;
    private int failed = 0;
    private int totalApiCalls = 0;

    @Test
    public void bookingCRUDFlow() {

        long startTime = System.currentTimeMillis();

        List<Booking> bookings = JsonReader.getBookingData();

        String token = TokenUtils.getToken();

        int bookingNumber = 1;

        for (Booking booking : bookings) {

            OutputFormatter.printBookingHeader(
                    bookingNumber,
                    booking.getFirstname() + " " + booking.getLastname());

            Response createResponse = createBooking(booking);

            int bookingId =
                    createResponse.jsonPath().getInt("bookingid");

            getBookingBeforeUpdate(bookingId);

            UpdateUtils.UpdateResult updateResult =
                    updateBooking(bookingId, booking, token);

            getBookingAfterUpdate(bookingId);

            deleteBooking(bookingId, token);

            verifyDelete(bookingId);

            bookingNumber++;

        }

        long endTime = System.currentTimeMillis();

        OutputFormatter.printSummary(
                bookings.size(),
                totalApiCalls,
                passed,
                failed,
                endTime - startTime);

    }

    // ===========================================================
    // CREATE BOOKING
    // ===========================================================

    private Response createBooking(Booking booking) {

        OutputFormatter.printStep("STEP 1 : CREATE BOOKING");

        Response response =

                given()

                        .contentType(ContentType.JSON)

                        .body(booking)

                        .when()

                        .post(APIConstants.BOOKING);

        OutputFormatter.printApiDetails(
                "POST " + APIConstants.BOOKING,
                200,
                response);

        response.then().statusCode(200);

        totalApiCalls++;
        passed++;

        System.out.println("Booking ID : "
                + response.jsonPath().getInt("bookingid"));

        System.out.println();

        System.out.println(response.asPrettyString());

        return response;

    }

    // ===========================================================
    // GET BEFORE UPDATE
    // ===========================================================

    private void getBookingBeforeUpdate(int bookingId) {

        OutputFormatter.printStep(
                "STEP 2 : GET BOOKING (Before Update)");

        Response response =

                given()

                        .when()

                        .get(APIConstants.BOOKING + "/" + bookingId);

        OutputFormatter.printApiDetails(

                "GET " + APIConstants.BOOKING + "/" + bookingId,

                200,

                response);

        response.then().statusCode(200);

        totalApiCalls++;
        passed++;

        OutputFormatter.printBooking(response);

    }
    // ===========================================================
    // UPDATE BOOKING
    // ===========================================================

    private UpdateUtils.UpdateResult updateBooking(int bookingId,
                                                   Booking booking,
                                                   String token) {

        OutputFormatter.printStep("STEP 3 : UPDATE BOOKING");

        UpdateUtils.UpdateResult updateResult =
                UpdateUtils.updateBooking(booking);

        Response response =

                given()

                        .contentType(ContentType.JSON)

                        .cookie("token", token)

                        .body(booking)

                        .when()

                        .put(APIConstants.BOOKING + "/" + bookingId);

        OutputFormatter.printApiDetails(

                "PUT " + APIConstants.BOOKING + "/" + bookingId,

                200,

                response);

        response.then().statusCode(200);

        totalApiCalls++;
        passed++;

        OutputFormatter.printUpdatedField(

                updateResult.getFieldName(),

                updateResult.getOldValue(),

                updateResult.getNewValue());

        Assert.assertEquals(

                response.jsonPath().getString(
                        convertField(updateResult.getFieldName())),

                String.valueOf(updateResult.getNewValue()));

        return updateResult;

    }

    // ===========================================================
    // GET AFTER UPDATE
    // ===========================================================

    private void getBookingAfterUpdate(int bookingId) {

        OutputFormatter.printStep(
                "STEP 4 : GET BOOKING (After Update)");

        Response response =

                given()

                        .when()

                        .get(APIConstants.BOOKING + "/" + bookingId);

        OutputFormatter.printApiDetails(

                "GET " + APIConstants.BOOKING + "/" + bookingId,

                200,

                response);

        response.then().statusCode(200);

        totalApiCalls++;
        passed++;

        System.out.println("Updated Booking Details");

        System.out.println();

        OutputFormatter.printBooking(response);

    }

    // ===========================================================
    // FIELD NAME CONVERTER
    // ===========================================================

    private String convertField(String field) {

        switch (field) {

            case "Firstname":
                return "firstname";

            case "Lastname":
                return "lastname";

            case "Total Price":
                return "totalprice";

            case "Deposit Paid":
                return "depositpaid";

            case "Check-In Date":
                return "bookingdates.checkin";

            case "Check-Out Date":
                return "bookingdates.checkout";

            case "Additional Needs":
                return "additionalneeds";

            default:
                return "";

        }

    }
    // ===========================================================
    // DELETE BOOKING
    // ===========================================================

    private void deleteBooking(int bookingId, String token) {

        OutputFormatter.printStep("STEP 5 : DELETE BOOKING");

        Response response =

                given()

                        .cookie("token", token)

                        .when()

                        .delete(APIConstants.BOOKING + "/" + bookingId);

        OutputFormatter.printApiDetails(

                "DELETE " + APIConstants.BOOKING + "/" + bookingId,

                201,

                response);

        response.then().statusCode(201);

        totalApiCalls++;
        passed++;

        OutputFormatter.printDeleteSuccess();

    }

    // ===========================================================
    // VERIFY DELETE
    // ===========================================================

    private void verifyDelete(int bookingId) {

        OutputFormatter.printStep("STEP 6 : VERIFY DELETE");

        Response response =

                given()

                        .when()

                        .get(APIConstants.BOOKING + "/" + bookingId);

        OutputFormatter.printApiDetails(

                "GET " + APIConstants.BOOKING + "/" + bookingId,

                404,

                response);

        response.then().statusCode(404);

        totalApiCalls++;
        passed++;

        OutputFormatter.printVerifyDelete();

    }

}