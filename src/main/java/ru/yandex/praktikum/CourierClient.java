package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierClient {
    public final static String APIURL = "api/v1/courier/";
    public final static String Type = "Content-type";
    public final static String APP = "application/json";

    @Step("Запрос на регистрацию курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .header(Type, APP)
                .body(courier)
                .when()
                .post(APIURL)
                .then();
    }

    @Step("Запрос на регистрацию курьера без логина")
    public ValidatableResponse createWithOutLogin(Courier courier){
        String body = "{\"password\":\"" + courier.password + "\","
                + "\"firstName\":\"" + courier.firstName + "\"}";
        return given()
               .header(Type, APP)
               .body(body)
               .when()
               .post(APIURL)
               .then();
    }

    @Step("Запрос на регистрацию курьера без пароля")
    public ValidatableResponse createWithOutPassword(Courier courier){
        String body = "{\"login\":\"" + courier.login + "\","
                + "\"firstName\":\"" + courier.firstName + "\"}";
        return given()
                .header(Type, APP)
                .body(body)
                .when()
                .post(APIURL)
                .then();
    }

    @Step("Запрос на авторизацию курьера")
    public int login(CourierData courierData) {
        return given()
                .header(Type, APP)
                .body(courierData)
                .when()
                .post(APIURL+"login/")
                .then()
                .extract()
                .path("id");
    }

    @Step("Запрос на авторизацию курьера без логина")
    public ValidatableResponse authorizationWithoutLogin(Courier courier) {
        String body = "{\"password\":\"" + courier.password + "\"}";
        return given()
                .header(Type, APP)
                .body(body)
                .when()
                .post(APIURL+"login/")
                .then();
    }

    @Step("Запрос на авторизацию курьера без пароля")
    public ValidatableResponse authorizationWithoutPassword(Courier courier) {
        String body = "{\"login\":\"" + courier.login + "\"}";
        return given()
                .header(Type, APP)
                .body(body)
                .when()
                .post(APIURL+"login/")
                .then();

    }

    @Step("Запрос на авторизацию незарегистрированного курьера")
    public Response authorizationUnregisteredCourier(CourierData courierData) {
        return (Response) given()
                .header(Type, APP)
                .body(courierData)
                .when()
                .post(APIURL+"login/");
    }

    @Step("Запрос на удаление курьера")
    public void delete(int courierId) {
        if (courierId != 0) {
                 given()
                .header(Type, APP)
                .when()
                .post(APIURL + courierId);}

    }


}
