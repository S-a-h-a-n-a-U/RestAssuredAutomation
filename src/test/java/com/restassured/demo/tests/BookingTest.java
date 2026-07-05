package com.restassured.demo.tests;

import com.restassured.demo.constants.APIConstants;
import com.restassured.demo.models.Booking;
import com.restassured.demo.models.BookingDates;
import com.restassured.demo.tests.common.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import com.restassured.demo.utils.TokenUtils;
import java.util.List;
import com.restassured.demo.utils.JsonReader;

public class BookingTest extends BaseTest {

    @Test
    public void bookingCRUDFlow() {

        List<Booking> bookings = JsonReader.getBookingData();

        String token = TokenUtils.getToken();

        for (Booking booking : bookings) {

            // CREATE

            Response createResponse =

                    given()
                            .contentType(ContentType.JSON)
                            .body(booking)

                            .when()
                            .post(APIConstants.BOOKING);

            createResponse.then().statusCode(200);

            int bookingId = createResponse.jsonPath().getInt("bookingid");

            System.out.println("Created Booking ID : " + bookingId);

            // GET

            Response getResponse =

                    given()

                            .when()

                            .get(APIConstants.BOOKING + "/" + bookingId);

            getResponse.then().statusCode(200);

            Assert.assertEquals(
                    getResponse.jsonPath().getString("firstname"),
                    booking.getFirstname());

            // UPDATE

            booking.setFirstname(booking.getFirstname() + "_Updated");

            Response updateResponse =

                    given()
                            .contentType(ContentType.JSON)
                            .cookie("token", token)
                            .body(booking)

                            .when()

                            .put(APIConstants.BOOKING + "/" + bookingId);

            updateResponse.then().statusCode(200);

            Assert.assertEquals(
                    updateResponse.jsonPath().getString("firstname"),
                    booking.getFirstname());

            // DELETE

            given()
                    .cookie("token", token)

                    .when()

                    .delete(APIConstants.BOOKING + "/" + bookingId)

                    .then()

                    .statusCode(201);

            // VERIFY DELETE

            given()

                    .when()

                    .get(APIConstants.BOOKING + "/" + bookingId)

                    .then()

                    .statusCode(404);

            System.out.println("-----------------------------------");
        }
    }


}