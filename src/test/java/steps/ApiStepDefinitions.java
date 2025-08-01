package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiStepDefinitions {
    private String baseUrl;
    private Response response;

    @Given("the backend is running")
    public void the_backend_is_running() {
        baseUrl = "https://reqres.in/api";
    }

    @When("I send a POST to \\/login with username {string} and password {string}")
    public void i_send_a_post_to_login_with_username_and_password(String name, String password) {
        String requestBody = "{\n" +
                "    \"email\": \"" + name + "\",\n" +
                "    \"password\": \"" + password + "\"\n" +
                "}";

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseUrl + "/login");
    }


    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @When("I send a POST to \\/todos with text {string}")
    public void i_send_a_post_to_todos_with_text(String todoText) {
        String todosBase = "https://jsonplaceholder.typicode.com";
        String requestBody = "{ \"title\": \"" + todoText + "\", \"completed\": false }";

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(todosBase + "/todos");
    }

    @When("I send a GET request to \\/todos")
    public void i_send_a_get_request_to_todos() {
        String todosBase = "https://jsonplaceholder.typicode.com";

        response = RestAssured.given()
                .when()
                .get(todosBase + "/todos");
    }

    @Then("the response should contain {string}")
    public void the_response_should_contain(String expectedText) {
        String body = response.getBody().asString();
        assertThat(body, containsString(expectedText));
    }
}
