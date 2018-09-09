package com.xgbuils.cartesian;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class CartesianProductStream<T> {
    public Stream<List<T>> of(List<Iterable<T>> list) {
        Iterable<List<T>> iterable = () -> new CartesianIterator<T>(list);
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
