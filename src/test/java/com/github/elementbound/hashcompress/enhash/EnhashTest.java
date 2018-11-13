package com.github.elementbound.hashcompress.enhash;

import com.github.elementbound.hashcompress.enhash.hash.Enhash;
import org.apache.commons.codec.digest.DigestUtils;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

public class EnhashTest {
    @Mock
    private DigestUtils digestUtils;

    private Enhash enhash;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);

        enhash = new Enhash(digestUtils);
    }

    @Test
    public void shouldPassThroughDigestUtils() {
        // Given
        byte[] input = "foo".getBytes();
        byte[] expected = "bar".getBytes();

        given(digestUtils.digest(input))
                .willReturn(expected);

        // When
        byte[] actual = enhash.consume(input);

        // Then
        assertThat(actual, is(expected));
    }
}