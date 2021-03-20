package com.example.demo;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestContTask1_Tests {
    @Autowired
    TestRestTemplate template1;
    public static GenericContainer<?> devapp = new GenericContainer("devapp")
            .withExposedPorts(8080);
    @Autowired
    TestRestTemplate template2;
    public static GenericContainer<?> prodapp = new GenericContainer("prodapp")
            .withExposedPorts(8081);


    @BeforeAll
    public static void setUp() {
        devapp.start();
        prodapp.start();
    }

    @Test
    void testDevApp_getBody() {
        ResponseEntity<String> actual = template1.getForEntity("http://localhost:" + devapp.getMappedPort(8080) + "/profile", String.class);
        String expected = "Current profile is dev";
        Assert.assertEquals(expected, actual.getBody());
    }

    @Test
    void testDevApp_getStatusCode() {
        ResponseEntity<String> actual = template1.getForEntity("http://localhost:" + devapp.getMappedPort(8080) + "/profile", String.class);
        Assert.assertEquals(200, actual.getStatusCodeValue());
    }

    @Test
    void testProdApp_getBody() {
        ResponseEntity<String> actual = template2.getForEntity("http://localhost:" + prodapp.getMappedPort(8081) + "/profile", String.class);
        String expected = "Current profile is production";
        Assert.assertEquals(expected, actual.getBody());
    }

    @Test
    void testProdApp_getStatusCode() {
        ResponseEntity<String> actual = template2.getForEntity("http://localhost:" + prodapp.getMappedPort(8081) + "/profile", String.class);
        Assert.assertEquals(200, actual.getStatusCodeValue());
    }

}
