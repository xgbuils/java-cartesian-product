package com.xgbuils.cartesian;

import java.util.List;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.testng.Assert.assertEquals;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.Arrays.asList;

public class CartesianProductStreamTest {
    private CartesianProductStream cartesianProductStream;

    @BeforeMethod
    public void setUp() {
        cartesianProductStream = new CartesianProductStream();
    }

    @Test
    public void empty() {
        List<List<Integer>> list = emptyList();

        assertEquals(cartesianProductStream.of(list).collect(toList()), singletonList(emptyList()));
    }

    @Test
    public void singleProduct() {
        List<List<Integer>> list = singletonList(asList(1, 2, 3));

        assertEquals(cartesianProductStream.of(list).collect(toList()), asList(
            singletonList(1),
            singletonList(2),
            singletonList(3)
        ));
    }

    @Test
    public void multipleProduct() {
        List<List<Integer>> list = asList(
            asList(1, 2, 3),
            asList(3, 4, 5)
        );

        assertEquals(cartesianProductStream.of(list).collect(toList()), asList(
            asList(1, 3),
            asList(1, 4),
            asList(1, 5),
            asList(2, 3),
            asList(2, 4),
            asList(2, 5),
            asList(3, 3),
            asList(3, 4),
            asList(3, 5)
        ));
    }
}
