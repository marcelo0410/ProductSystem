package com.mstudio.test;

import com.mstudio.util.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationTest {

    private Validation validation;

    @BeforeEach
    void setUp() throws Exception {
        validation = new Validation();
    }

    @Test
    void testRegularExpressionValidation() {
        assertEquals(true, Validation.checkString("\\d", "3"), "3 is a digit");
    }
}
