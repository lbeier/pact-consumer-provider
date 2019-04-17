import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ItemsPactTest {
    private Map<String, String> headers = new HashMap<>() {{
        put("Content-Type", "application/json");
    }};

    @Rule
    public PactProviderRuleMk2 mockProvider
            = new PactProviderRuleMk2("api-in-go", "localhost", 8080, this);

    @Pact(provider = "api-in-go", consumer = "java_consumer")
    public RequestResponsePact existingItemFragment(PactDslWithProvider builder) {
        return builder
                .given("an item with ID_00001 exists")
                .uponReceiving("GET request to /items/ID_00001")
                .path("/items/ID_00001")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(this.headers)
                .body("{\"sku\": \"ID_00001\", \"title\": \"Nice product\", \"quantityInStock\": 100}")
                .toPact();
    }

    @Pact(provider = "api-in-go", consumer = "java_consumer")
    public RequestResponsePact nonExistinItemFragment(PactDslWithProvider builder) {
        return builder
                .given("an item with ID_00002 doesn't exist")
                .uponReceiving("GET request to /items/ID_00002")
                .path("/items/ID_00002")
                .method("GET")
                .willRespondWith()
                .status(404)
                .headers(this.headers)
                .body("")
                .toPact();
    }

    @Test
    @PactVerification(value = "api-in-go", fragment = "existingItemFragment")
    public void existingItemFragmentTest() {
        ItemClient client = new ItemClient(mockProvider.getUrl());

        assertEquals(client.get("/items/ID_00001"), "{\"sku\":\"ID_00001\",\"title\":\"Nice product\",\"quantityInStock\":100}");
    }

    @Test
    @PactVerification(value = "api-in-go", fragment = "nonExistinItemFragment")
    public void nonExistinItemFragmentTest() {
        ItemClient client = new ItemClient(mockProvider.getUrl());

        assertEquals(client.get("/items/ID_00002"), "");
    }
}