import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.CourierSteps;
import org.example.NewCourier;
import org.example.CourierLogin;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class NewCourierTest {
    public static String login = "pikachu";
    public static String password = "1234";
    public static String firstName = "Пикачу";


    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяем, что курьера можно создать с валидными данными")
    public void createNewCourier() {

        NewCourier courierCreateRequest = new NewCourier(login, password, firstName);
        CourierLogin courierLoginRequest = new CourierLogin(login, password);
        CourierSteps courierSteps = new CourierSteps();

        courierSteps.courierCreate(courierCreateRequest)
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));

        courierSteps.courierDeleteAfterLogin(courierLoginRequest);
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Попытка создать двух курьеров с одинаковым набором данных. Создание второго курьера должно провалиться")
    public void createTwoIdenticalCouriers() {

        NewCourier courierCreateRequest = new NewCourier(login, password, firstName);
        CourierLogin courierLoginRequest = new CourierLogin(login, password);
        CourierSteps courierSteps = new CourierSteps();

        courierSteps.courierCreate(courierCreateRequest)
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));

        courierSteps.courierCreate(courierCreateRequest)
                .statusCode(409)
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        courierSteps.courierDeleteAfterLogin(courierLoginRequest);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Попытка создать курьера без передачи поля login. Создание курьера должно провалиться")
    public void createCourierWithoutLogin() {
        NewCourier courierCreateRequest = new NewCourier(null, password, firstName);
        CourierSteps courierSteps = new CourierSteps();

        courierSteps.courierCreate(courierCreateRequest)
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Попытка создать курьера без передачи поля password. Создание курьера должно провалиться")
    public void createCourierWithoutPassword() {
        NewCourier courierCreateRequest = new NewCourier(login, null, firstName);
        CourierSteps courierSteps = new CourierSteps();

        courierSteps.courierCreate(courierCreateRequest)
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
