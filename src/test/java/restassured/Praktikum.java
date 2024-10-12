package restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Praktikum {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";
    }

    @Test
    public void registrationAndAuth() {
        // Составили email
        Random random = new Random();
        String email = "something" + random.nextInt(10000000) + "@yandex.ru";
        // составь json, используя переменную email
        String json = "{\"email\": \"" + email + "\", \"password\": \"123456\"}";
        // POST запрос на регистрацию signup
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(json)
                .when()
                .post("/api/signup")
                .then().log().all()
                .statusCode(HttpURLConnection.HTTP_CREATED);
        // POST запрос на авторизацию signin с теми же параметрами
        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(json)
                .when()
                .post("/api/signin");
        response.then().log().all()
                // проверь, что пришедший в ответ токен не пустой
                .assertThat().body("token", notNullValue())
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
        // Попытка зарегистрироваться с теми же параметрами ещё раз
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(json)
                .when()
                .post("/api/signup")
                .then().log().all()
                .statusCode(HttpURLConnection.HTTP_CONFLICT);
    }

}
