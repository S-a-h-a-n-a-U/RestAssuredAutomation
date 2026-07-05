package com.restassured.demo.tests.common;

import com.restassured.demo.constants.APIConstants;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = APIConstants.BASE_URL;

    }
}