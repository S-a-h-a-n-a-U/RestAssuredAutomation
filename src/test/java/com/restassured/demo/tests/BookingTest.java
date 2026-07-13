package com.restassured.demo.tests;

import com.restassured.demo.constants.APIConstants;
import com.restassured.demo.models.Booking;
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

            System.out.println("\n========== CREATE BOOKING ==========");
            System.out.println("Status Code : " + createResponse.getStatusCode());
            System.out.println("Booking ID : " + bookingId);
            System.out.println("Response:");
            System.out.println(createResponse.asPrettyString());

            // GET

            Response getResponse =

                    given()

                            .when()

                            .get(APIConstants.BOOKING + "/" + bookingId);

            getResponse.then().statusCode(200);

            System.out.println("\n========== GET BOOKING ==========");
            System.out.println("Status Code : " + getResponse.getStatusCode());
            System.out.println("Response:");
            System.out.println(getResponse.asPrettyString());

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

            System.out.println("\n========== UPDATE BOOKING ==========");
            System.out.println("Status Code : " + updateResponse.getStatusCode());
            System.out.println("Updated Response:");
            System.out.println(updateResponse.asPrettyString());

            Assert.assertEquals(
                    updateResponse.jsonPath().getString("firstname"),
                    booking.getFirstname());

            // DELETE

            Response deleteResponse =

                    given()
                            .cookie("token", token)

                            .when()

                            .delete(APIConstants.BOOKING + "/" + bookingId);

            deleteResponse.then().statusCode(201);

            System.out.println("\n========== DELETE BOOKING ==========");
            System.out.println("Status Code : " + deleteResponse.getStatusCode());
            System.out.println("Booking Deleted Successfully");

            // VERIFY DELETE

            Response verifyDeleteResponse =

                    given()

                            .when()

                            .get(APIConstants.BOOKING + "/" + bookingId);

            verifyDeleteResponse.then().statusCode(404);

            System.out.println("\n========== VERIFY DELETE ==========");
            System.out.println("Status Code : " + verifyDeleteResponse.getStatusCode());
            System.out.println("Booking Not Found");
            System.out.println("Delete Verification Passed");

            System.out.println("\n==============================================================");
        }
    }
}