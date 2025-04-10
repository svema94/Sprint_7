package java;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import java.Endpoints.*;

public class CourierSteps {

    public static RequestSpecification requestSpecification() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(URL);
    }

    @Step("Создание нового курьера")
    public ValidatableResponse courierCreate(NewCourier courierCreateRequest) {
        return requestSpecification()
                .body(courierCreateRequest)
                .post(NEW_COURIER)
                .then();
    }

    @Step("Логин курьера")
    public ValidatableResponse courierLogin(CourierLogin courierLoginRequest) {
        return requestSpecification()
                .body(courierLoginRequest)
                .when()
                .post(LOGIN_COURIER)
                .then();
    }

    @Step("Удаление курьера")
    public void courierDelete(int courierId) {
        requestSpecification()
                .when()
                .delete(DELETE_COURIER + courierId)
                .then();
    }

    @Step("Логин курьера, получение ID, удаление курьера")
    public void courierDeleteAfterLogin(CourierLogin courierDeleteAfterLogin) {
        Response response = courierLogin(courierDeleteAfterLogin)
                .extract().response();
        CourierLoginResponse courierLoginResponse = response.as(CourierLoginResponse.class);
        int courierId = courierLoginResponse.getId();
        courierDelete(courierId);
    }
}
