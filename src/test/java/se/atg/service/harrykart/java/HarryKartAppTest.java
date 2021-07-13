package se.atg.service.harrykart.java;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StreamUtils;
import se.atg.service.harrykart.java.dto.response.HorseRanking;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("java-test")
public class HarryKartAppTest {

    private final static URI harryKartApp = URI.create("http://localhost:8181/java/api/play");

    private static HorseRanking buildHorse(Integer position, String name) {
        return new HorseRanking(position, name);
    }

    private static String xmlInputString(String filePath) throws IOException {
        return StreamUtils.copyToString(new ClassPathResource(filePath).getInputStream(), Charset.defaultCharset());
    }

    @BeforeAll
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("Trying to GET instead of POST should return 405 Method not allowed")
    void useGetOnPostEndpointShouldNotBePossible() {
        when()
                .get(harryKartApp)
        .then()
                .assertThat()
                .statusCode(405);
    }

    @Test
    @DisplayName("The application doesn't know how to play yet")
    void cantPlayYet() {
        given()
                .contentType(ContentType.XML)
        .when()
                .post(harryKartApp)
        .then()
                .assertThat()
                .statusCode(400)
                .and()
                .header("Content-Type", ContentType.JSON.toString());
    }

    @Test
    @DisplayName("Test Play Harry Kart with input_0.xml")
    void playHarryKart_With0() throws IOException {
        // Expected
        List<HorseRanking> expectedRatings = List.of(buildHorse(1, "TIMETOBELUCKY"),
                buildHorse(2, "HERCULES BOKO"),
                buildHorse(3, "CARGO DOOR"));

        // When
        Response response = given()
                .contentType(ContentType.XML)
                .accept(ContentType.JSON)
                .body(xmlInputString("input_0.xml"))
                .when()
                .post(harryKartApp)
                .then()
                .extract()
                .response();

        List<HorseRanking> horseRankings = response.as(new TypeRef<>() {});

        // Status Code
        Assertions.assertEquals(200, response.getStatusCode());

        // Null check
        Assertions.assertNotNull(horseRankings);

        // result size check
        Assertions.assertEquals(3, horseRankings.size());

        // Result check
        Assertions.assertIterableEquals(expectedRatings, horseRankings);
    }

    @Test
    @DisplayName("Test Play Harry Kart with input_1.xml")
    void playHarryKart_With1() throws IOException {
        // Expected
        List<HorseRanking> expectedRatings = List.of(buildHorse(1, "WAIKIKI SILVIO"),
                buildHorse(2, "TIMETOBELUCKY"),
                buildHorse(3, "HERCULES BOKO"));

        // When
        Response response = given()
                .contentType(ContentType.XML)
                .accept(ContentType.JSON)
                .body(xmlInputString("input_1.xml"))
                .when()
                .post(harryKartApp)
                .then()
                .extract()
                .response();

        List<HorseRanking> horseRankings = response.as(new TypeRef<>() {});

        // Status Code
        Assertions.assertEquals(200, response.getStatusCode());

        // Null check
        Assertions.assertNotNull(horseRankings);

        // result size check
        Assertions.assertEquals(3, horseRankings.size());

        // Result check
        Assertions.assertIterableEquals(expectedRatings, horseRankings);
    }

    @Test
    @DisplayName("Test Play Harry Kart with input_2.xml")
    void playHarryKart_With2() throws IOException {
        // Expected
        List<HorseRanking> expectedRatings = List.of(buildHorse(1, "HERCULES BOKO"),
                buildHorse(2, "TIMETOBELUCKY"),
                buildHorse(3, "WAIKIKI SILVIO"));

        // When
        Response response = given()
                .contentType(ContentType.XML)
                .accept(ContentType.JSON)
                .body(xmlInputString("input_2.xml"))
                .when()
                .post(harryKartApp)
                .then()
                .extract()
                .response();

        List<HorseRanking> horseRankings = response.as(new TypeRef<>() {});

        // Status Code
        Assertions.assertEquals(200, response.getStatusCode());

        // Null check
        Assertions.assertNotNull(horseRankings);

        // result size check
        Assertions.assertEquals(3, horseRankings.size());

        // Result check
        Assertions.assertIterableEquals(expectedRatings, horseRankings);
    }
}
