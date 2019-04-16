import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.PactVerifications;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;

public class UsersPactTest {
    private Map<String, String> headers = new HashMap<>() {{
        put("Content-Type", "application/json");
    }};

    @Rule
    public PactProviderRuleMk2 mockProvider
            = new PactProviderRuleMk2("api-in-go", "localhost", 8080, this);

    @Pact(provider = "api-in-go", consumer = "java_consumer")
    public RequestResponsePact existingUserFragment(PactDslWithProvider builder) {
        return builder
            .given("a customer with ID 1 exists")
            .uponReceiving("GET request to /users/1")
                .path("/users/1")
                .method("GET")
            .willRespondWith()
                .status(200)
                .headers(this.headers)
                .body("{\"id\":1,\"name\":\"Customer name\",\"birthday\":\"1989-02-11\",\"isActive\":true}")
            .toPact();
    }

    @Pact(provider = "api-in-go", consumer = "java_consumer")
    public RequestResponsePact nonExistinUserFragment(PactDslWithProvider builder) {
        return builder
                .given("a customer with ID 1 exists")
                .uponReceiving("GET request to /users/2")
                    .path("/users/2")
                    .method("GET")
                .willRespondWith()
                    .status(404)
                    .headers(this.headers)
                    .body("")
                .toPact();
    }

    @Test
    @PactVerification(value = "api-in-go", fragment = "existingUserFragment")
    public void existingUserFragmentTest() {
        UserClient userClient = new UserClient(mockProvider.getUrl());

        assertEquals(userClient.get("/users/1"), "{\"id\":1,\"name\":\"Customer name\",\"birthday\":\"1989-02-11\",\"isActive\":true}");
    }

    @Test
    @PactVerification(value = "api-in-go", fragment = "nonExistinUserFragment")
    public void nonExistinUserFragmentTest() {
        UserClient userClient = new UserClient(mockProvider.getUrl());

        assertEquals(userClient.get("/users/2"), "");
    }
}