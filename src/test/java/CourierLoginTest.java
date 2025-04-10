import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.example.CourierSteps;
import org.example.NewCourier;
import org.example.CourierLogin;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;

public class CourierLoginTest {

    public static String login = "n1nja";
    public static String password = "1234";
    public static String firstName = "saske";
    public static String wrongPassword = "9876";
    public static String wrongLogin = "ninjaj123a";

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка авторизации курьера с набором валидных данных")
    public void loginCourier() {

        NewCourier courierCreateRequest = new NewCourier(login, password, firstName);
        CourierLogin courierLoginRequest = new CourierLogin(login, password);
        CourierSteps courierSteps = new CourierSteps();

        courierSteps.courierCreate(courierCreateRequest);

        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().statusCode(200);
        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().body("id", instanceOf(Integer.class));

    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка невозможности авторизации без передачи поля login")
    public void loginCourierWithoutLogin() {

        NewCourier courierCreateRequest = new NewCourier(login, password, firstName);
        CourierLogin courierLoginRequest = new CourierLogin(null, password);
        CourierSteps courierSteps = new CourierSteps();

        courierSteps.courierCreate(courierCreateRequest);

        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().statusCode(400);
        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));


    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка невозможности авторизации без передачи поля password")
    public void loginCourierWithoutPassword() {

        NewCourier courierCreateRequest = new NewCourier(login, password, firstName);
        CourierLogin courierLoginRequest = new CourierLogin(login, null);
        CourierSteps courierSteps = new CourierSteps();

        courierSteps.courierCreate(courierCreateRequest);

        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().statusCode(400);
        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));


    }

    @Test
    @DisplayName("Авторизация курьера c несуществующей парой логин-пароль")
    @Description("Проверка невозможности авторизации с несуществующими данными для входа")
    public void courierLoginWithNonExistentCredential () {

        CourierLogin courierLoginRequest = new CourierLogin(login, password);
        CourierSteps courierSteps = new CourierSteps();

        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().statusCode(404);
        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера c неверным паролем")
    @Description("Проверка невозможности авторизации c неверным паролем")
    public void courierLoginWithWrongPassword () {
        NewCourier courierCreateRequest = new NewCourier(login, password, firstName);
        CourierLogin courierLoginRequest = new CourierLogin(login, wrongPassword);
        CourierSteps courierSteps = new CourierSteps();

        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().statusCode(404);
        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера c неверным логином")
    @Description("Проверка невозможности авторизации c неверным логином")
    public void courierLoginWithWrongLogin () {
        NewCourier courierCreateRequest = new NewCourier(login, password, firstName);
        CourierLogin courierLoginRequest = new CourierLogin(wrongLogin, password);
        CourierSteps courierSteps = new CourierSteps();

        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().statusCode(404);
        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void deleteCourier() {
        CourierSteps courierSteps = new CourierSteps();
        CourierLogin validCourierLoginRequest = new CourierLogin(login, password);
        courierSteps.courierDeleteAfterLogin(validCourierLoginRequest);
    }

}
