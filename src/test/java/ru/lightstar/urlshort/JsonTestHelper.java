package ru.lightstar.urlshort;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Class used to help testing JSON.
 *
 * @author Lightstar
 * @since 0.0.1
 */
public class JsonTestHelper {

    /**
     * Get object from JSON string.
     *
     * @param json JSON representation of object.
     * @param cls object's class.
     * @param <T> object to decode.
     * @return object to decode.
     * @throws IOException shouldn't be thrown.
     */
    public <T> T decode(final String json, final Class<T> cls) throws IOException {
        return new ObjectMapper().readValue(json, cls);
    }

    /**
     * Get JSON representation of object.
     *
     * @param object encoded object.
     * @return JSON representation of object.
     * @throws IOException shouldn't be thrown.
     */
    public String encode(final Object object) throws IOException {
        final Writer writer = new StringWriter();
        new ObjectMapper().writeValue(writer, object);
        return writer.toString();
    }

    /**
     * Get boolean value of JSON object field.
     *
     * @param json JSON object as string.
     * @param field field name.
     * @return field value.
     * @throws IOException shouldn't be thrown.
     */
    public boolean booleanField(final String json, final String field) throws IOException {
        return this.field(json, field).asBoolean();
    }

    /**
     * Get text value of JSON object field.
     *
     * @param json JSON object as string.
     * @param field field name.
     * @return field value.
     * @throws IOException shouldn't be thrown.
     */
    public String textField(final String json, final String field) throws IOException {
        return this.field(json, field).asText();
    }

    /**
     * Check if some field exists in JSON object.
     *
     * @param json JSON object as string.
     * @param field field name.
     * @return <code>true</code> if field exists and <code>false</code> otherwise.
     * @throws IOException shouldn't be thrown.
     */
    public boolean hasField(final String json, final String field) throws IOException {
        return new ObjectMapper().readTree(json).has(field);
    }

    /**
     * Get JSON object field value.
     *
     * @param json JSON object as string.
     * @param field field name.
     * @return field value.
     * @throws IOException shouldn't be thrown.
     */
    private JsonNode field(final String json, final String field) throws IOException {
        return new ObjectMapper().readTree(json).findValue(field);
    }
}