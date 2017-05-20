package ru.lightstar.urlshort.controller.response;

import org.junit.Test;
import ru.lightstar.urlshort.JsonTestHelper;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

/**
 * <code>ErrorResponse</code> class tests.
 *
 * @author Lightstar
 * @since 0.0.1
 */
public class ErrorResponseTest {

    /**
     * <code>ErrorResponse</code> object used in all tests.
     */
    private final ErrorResponse response = new ErrorResponse("testError");

    /**
     * Helper object used to test JSON serialization.
     */
    private final JsonTestHelper jsonTestHelper = new JsonTestHelper();

    /**
     * Test correctness of created response.
     */
    @Test
    public void whenCreateResponseThenAllFieldsInitialized() {
        assertThat(this.response.getError(), is("testError"));
    }

    /**
     * Test correctness of created empty response.
     */
    @Test
    public void whenCreateEmptyResponseThenAllFieldsInitialized() {
        final ErrorResponse response = new ErrorResponse();
        assertThat(response.getError(), isEmptyString());
    }

    /**
     * Test correctness of <code>setError</code> and <code>getError</code> methods.
     */
    @Test
    public void whenSetSuccessThenItChanges() {
        this.response.setError("testError");
        assertThat(this.response.getError(), is("testError"));
    }

    /**
     * Test correctness of serialization to JSON.
     */
    @Test
    public void whenSerializeToJsonThenResult() throws IOException {
        final String json = this.jsonTestHelper.encode(this.response);
        assertThat(this.jsonTestHelper.textField(json, "error"), is("testError"));
    }
}