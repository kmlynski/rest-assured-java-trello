package board;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;


public class BoardTest {

    private final String key = "";
    private final String token = "";

   /* @Test
    void createNewBoard() {
        Response response = given()
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("name", "My board")
                .contentType("application/json")
                .when()
                .post("https://api.trello.com/1/boards/")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("My board", json.get("name"));

        String boardId = json.get("id");

        given()
                .queryParam("key", key)
                .queryParam("token", token)
                .contentType("application/json")
                .when()
                .delete("https://api.trello.com/1/boards/" + boardId)
                .then()
                .statusCode(200);


    }

    @Test
    void createBoardWithEmptyBoardName() {
        given()
                .queryParam("key", key)
                .queryParam("token", token)
                .contentType("application/json")
                .when()
                .post("https://api.trello.com/1/boards/")
                .then()
                .statusCode(400)
                .extract()
                .response();

    }

    @Test
    void createBoardWithoutDefaultList() {
        Response response = given()
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("name", "board without default list")
                .queryParam("defaultLists", false)
                .contentType("application/json")
                .when()
                .post("https://api.trello.com/1/boards/")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        String boardId = json.get("id");

        Response responseGet = given()
                .queryParam("key", key)
                .queryParam("token", token)
                .contentType(ContentType.JSON)
                .when()
                .get("https://api.trello.com/1/boards/" + boardId + "/lists")
                .then().statusCode(200)
                .extract()
                .response();

        JsonPath jsonGet = responseGet.jsonPath();
        List<String> idList = jsonGet.getList("id");
        assertTrue(idList.isEmpty());
        assertEquals("board without default list", json.get("name"));

        given()
                .queryParam("key", key)
                .queryParam("token", token)
                .contentType("application/json")
                .when()
                .delete("https://api.trello.com/1/boards/" + boardId)
                .then()
                .statusCode(200);
    }*/

    @Test
    void test(){

        Response response = given()
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("name", "My board")
                .contentType(ContentType.JSON)
                .when()
                .post("https://api.trello.com/1/boards/")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        String boardId = json.get("id");


        Response responseGet = given()
                .queryParam("key", key)
                .queryParam("token", token)
                .contentType(ContentType.JSON)
                .when()
                .get("https://api.trello.com/1/boards/" + boardId + "/lists")
                .then().statusCode(200)
                .extract()
                .response();
        JsonPath jsonGet = responseGet.jsonPath();

        List<String> namesList = jsonGet.getList("name");
        Assertions.assertThat(namesList).hasSize(3).contains("Do zrobienia","W trakcie","Zrobione");
        Assertions.assertThat(json.getString("name")).isEqualTo("My board");

        given()
                .queryParam("key", key)
                .queryParam("token", token)
                .contentType("application/json")
                .when()
                .delete("https://api.trello.com/1/boards/" + boardId)
                .then()
                .statusCode(200);

    }
}
