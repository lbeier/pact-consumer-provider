import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactFolder;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponseInteraction;
import au.com.dius.pact.model.RequestResponsePact;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@PactFolder("/pacts")
public class JavaConsumerExample {

    private Map<String, String> headers = new HashMap<>() {{
        put("Content-Type", "application/json");
    }};

    @Rule
    public PactProviderRuleMk2 mockProvider
            = new PactProviderRuleMk2("api-in-go", "localhost", 8080, this);

    @Pact(consumer = "java_consumer")
    public RequestResponsePact generateUsersPact(PactDslWithProvider builder) {
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

//                .given("a customer with ID 2 doesnt exists")
//                .uponReceiving("GET request to /users/2")
//                    .path("/users/2")
//                    .method("GET")
//                .willRespondWith()
//                    .status(404)
//                    .headers(this.headers)
//                .toPact();
    }

//    @Pact(consumer = "java_consumer")
//    public RequestResponsePact generateProductsPact(PactDslWithProvider builder) {
//        return builder
//                .given("there is an item with id ID_00001")
//                .uponReceiving("a GET to /items/ID_00001")
//                    .path("/items/ID_00001")
//                    .method("GET")
//                .willRespondWith()
//                    .status(200)
//                    .headers(this.headers)
//                .body("{sku:'ID_00001',title:'Nice product',quantityInStock:100,}")
//
//                .given("there is an item with id ID_00002")
//                .uponReceiving("a GET to /items/ID_00002")
//                    .path("/items/ID_00002")
//                    .method("GET")
//                .willRespondWith()
//                    .status(404)
//                    .headers(this.headers)
//                .toPact();
//    }

    @Test
    @PactVerification("api-in-go")
    public void testUsers() {
        UserClient userClient = new UserClient(mockProvider.getUrl());

        String firstUserBody = userClient.get("/users/1");
        assertEquals(firstUserBody, containsString("Customer name"));

        String secondUserBody = userClient.get("/users/2");
        assertEquals(secondUserBody, containsString(""));
    }
}
