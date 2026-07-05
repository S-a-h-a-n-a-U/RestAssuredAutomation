package com.restassured.demo.utils;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class TokenUtils {

    public static String getToken() {

        String token =

                given()
                        .contentType(ContentType.JSON)

                        .body("""
                                {
                                  "username":"admin",
                                  "password":"password123"
                                }
                                """)

                        .when()
                        .post("/auth")

                        .then()
                        .statusCode(200)
                        .extract()
                        .path("token");

        return token;

    }

}