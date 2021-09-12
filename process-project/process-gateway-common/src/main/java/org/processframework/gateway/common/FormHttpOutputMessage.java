package org.processframework.gateway.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author apple
 */
public class FormHttpOutputMessage implements HttpOutputMessage {

    private HttpHeaders headers = new HttpHeaders();
    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Override
    public HttpHeaders getHeaders() {
        return this.headers;
    }

    @Override
    public OutputStream getBody() throws IOException {
        return this.output;
    }

    public byte[] getInput() throws IOException {
        this.output.flush();
        return this.output.toByteArray();
    }

}