package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;


@RunWith(Parameterized.class)
public class CreateOrderTest{
    @Before
    public void setUp() {
        RestAssured.baseURI = BaseUri.getBaseUri();
    }

    private final String color;
    private final int unexpectedNumber;

    public CreateOrderTest(String color, int unexpectedNumber){
        this.color = color;
        this.unexpectedNumber = unexpectedNumber;
    };

    @Parameterized.Parameters
    public static Object[][] checkColorCombinations() {
        return new Object[][] {
                {",\"color\":[\"BLACK\"]", 0},
                {",\"color\":[\"GREY\"]", 0},
                {",\"color\":[\"BLACK\", \"GRAY\"]", 0},
                {"", 0},
        };
    }


    @Test
    @DisplayName("Параметризованный тест на создание заказа")
    public void createOrderParameterizedTest(){

     String body = "{\"firstName\":\"Nikita\","
                + "\"lastName\":\"Ryazanskiy\","
                + "\"address\":\"Ryazan-city\","
                + "\"metroStation\":\"4\","
                + "\"phone\":\"+7 111 222 33 44\","
                + "\"rentTime\":\"12\","
                + "\"deliveryDate\":\"2022-02-01\","
                + "\"comment\":\"RED GOES FASTER!\""
                + color +"}";

        int returnedTrackNumber = given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post("/api/v1/orders")
                .then()
                .extract()
                .path("track");

        assertNotEquals(unexpectedNumber, returnedTrackNumber);
    }
}