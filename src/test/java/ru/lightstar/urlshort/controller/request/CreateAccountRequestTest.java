package ru.lightstar.urlshort.controller.request;

import org.junit.Test;
import ru.lightstar.urlshort.JsonTestHelper;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

/**
 * <code>CreateAccountRequest</code> class tests.
 *
 * @author Lightstar
 * @since 0.0.1
 */
public class CreateAccountRequestTest {

    /**
     * <code>CreateAccountRequest</code> object used in all tests.
     */
    private final CreateAccountRequest request = new CreateAccountRequest();

    /**
     * Test correctness of created empty request.
     */
    @Test
    public void whenCreateRequestThenAllFieldsInitialized() {
        assertThat(this.request.getAccountId(), isEmptyString());
    }

    /**
     * Test correctness of <code>setAccountId</code> and <code>getAccountId</code> methods.
     */
    @Test
    public void whenSetAccountIdThenItChanges() {
        this.request.setAccountId("test");
        assertThat(this.request.getAccountId(), is("test"));
    }

    /**
     * Test correctness of deserialization from JSON.
     */
    @Test
    public void whenDeserializeFromJsonThenResult() throws IOException {
        final String json = "{\"AccountId\":\"test\"}";
        final CreateAccountRequest request = new JsonTestHelper().decode(json, CreateAccountRequest.class);
        assertThat(request.getAccountId(), is("test"));
    }
}