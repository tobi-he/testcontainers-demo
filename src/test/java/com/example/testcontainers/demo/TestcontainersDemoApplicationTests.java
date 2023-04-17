package com.example.testcontainers.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest
class TestcontainersDemoApplicationTests {
	// copied from here: https://www.atomicjar.com/2022/08/integration-testing-for-spring-boot-with-testcontainers/
	@Test
	void test() throws IOException, InterruptedException {
		try (var container = new GenericContainer<>("nginx").withExposedPorts(80)) {
			container.start();
			var client = HttpClient.newHttpClient();
			var uri = "http://" + container.getHost() + ":" + container.getFirstMappedPort();

			var request = HttpRequest.newBuilder(URI.create(uri)).GET().build();
			var response = client.send(request, HttpResponse.BodyHandlers.ofString());

			Assertions.assertTrue(response.body().contains("Thank you for using nginx."));
		}
	}

}
