package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient {
    public final static String APIURL = "api/v1/courier/";
    public final static String Type = "Content-type";
    public final static String APP = "application/json";


    // метод создания курьера
    @Step
    public ValidatableResponse create(Courier courier) {
        return given()
                .header(Type, APP)
                .body(courier)
                .when()
                .post(APIURL)
                .then();
    }

    // метод создания курьера, без логина
    @Step
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

    // метод создания курьера, без пароля
    @Step
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

    // метод для авторизации курьера в системе
    @Step
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

    // метод для авторизации курьера в системе без логина
    @Step
    public ValidatableResponse loginWithoutLogin(Courier courier) {
        String body = "{\"password\":\"" + courier.password + "\"}";
        return given()
                .header(Type, APP)
                .body(body)
                .when()
                .post(APIURL+"login/")
                .then();
    }

    // метод для авторизации курьера в системе без пароля
    @Step
    public ValidatableResponse loginWithoutPassword(Courier courier) {
        String body = "{\"login\":\"" + courier.login + "\"}";
        return given()
                .header(Type, APP)
                .body(body)
                .when()
                .post(APIURL+"login/")
                .then();

    }

    // метод для авторизации незарегистрированного курьера
    @Step
    public ValidatableResponse unregisteredCourierlogin(Courier courier) {
        String body = "{\"login\":\"" + courier.login + "\","
                + "\"password\":\"" + courier.password + "\"}";
        return given()
                .header(Type, APP)
                .body(body)
                .when()
                .post(APIURL+"login/")
                .then();
    }

    // метод для удаления курьера
    @Step
    public void delete(int courierId) {
                 given()
                .header(Type, APP)
                .when()
                .post(APIURL + courierId);

    }


}
