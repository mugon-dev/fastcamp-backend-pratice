package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * HttpRequest
 *  - RequestLine (GET /calculate?operand1=11&operand2=55 HTTP/1.1)
 *      - HttpMethod
 *      - path
 *      - queryString
 *  - Header
 *  - Body
 */
public class RequestLineTest {
    @Test
    void namecreate() {
        RequestLine requestLine = new RequestLine("GET /calculate?operand1=11&operand2=55 HTTP/1.1");
        assertThat(requestLine).isNotNull();
        assertThat(requestLine).isEqualTo(new RequestLine("GET", "/calculate", "operand1=11&operand2=55"));
      }
}
