package com.xgbuils.cartesian;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.NoSuchElementException;

import java.lang.Integer;
import java.lang.Boolean;
import java.lang.System;

import static java.util.stream.Collectors.toList;

public class CartesianIterator<T> implements Iterator<List<T>> {
    private final List<? extends Iterable<? extends T>> iterables;
    private List<Iterator<? extends T>> iterators;
    private boolean goOn;
    private List<T> currentList;
    private int index  = 0;
    private Iterator<? extends T> iterator;
    private int size;

    public CartesianIterator(List<? extends Iterable<? extends T>> iterables) {
        this.iterables = iterables;
        iterators = iterables.stream()
            .map(iterable -> iterable.iterator())
            .collect(toList());
        goOn = iterators.stream()
            .allMatch(iterator -> iterator.hasNext());
        size = iterables.size();
        if (size > 0) {
            index = size - 1;
            iterator = iterators.get(index);
        }        
    }

    public boolean hasNext() {
        if (!goOn || size == 0) {
            return goOn;
        }
        while(index > 0 && !iterator.hasNext()) {
            --index;
            iterator = iterators.get(index);
        }
        goOn = index > 0 || iterator.hasNext();
        return goOn;
    }

    public List<T> next() {
        if (goOn && Objects.isNull(currentList)) {
            currentList = firstIteration();
            goOn = size > 0;
            return new ArrayList<>(currentList);
        }
        while (hasNext()) {
            currentList.set(index, iterator.next());
            ++index;
            if (index < size) {
                iterator = iterables.get(index).iterator();
                iterators.set(index, iterator);
            } else {
                --index;
                return new ArrayList<>(currentList);
            }
        }
        throw new NoSuchElementException();
    }

    private List<T> firstIteration() {
        return iterators.stream()
            .map(iterator -> iterator.next())
            .collect(toList());
    }
}
