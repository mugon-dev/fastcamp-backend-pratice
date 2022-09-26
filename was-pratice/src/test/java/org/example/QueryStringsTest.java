package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringsTest {

    @Test
    void namecreateTest() {
        QueryStrings queryStrings = new QueryStrings("operand1=11&operand2=55");
        assertThat(queryStrings).isNotNull();
      }
}
