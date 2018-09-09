package com.xgbuils.cartesian;

import java.util.List;
import java.util.stream.StreamSupport;
import java.util.Iterator;
import java.util.NoSuchElementException;

import java.lang.Iterable;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.Arrays.asList;

public class CartesianIteratorTest {
    @Test
    public void emptyProduct() {
        CartesianIterator<Integer> iterator = new CartesianIterator<>(emptyList());

        assertEquals(iteratorToList(iterator), singletonList(emptyList()));
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void noSuchElementExceptionWithEmptyProduct() {
        CartesianIterator<Integer> iterator = new CartesianIterator<>(emptyList());

        iterator.next();
        iterator.next();
    }

    @Test
    public void singleProductOfEmptyList() {
        CartesianIterator<Integer> iterator = new CartesianIterator<>(singletonList(emptyList()));

        assertEquals(iteratorToList(iterator), emptyList());
    }

    @Test
    public void singleProductOfSingletonList() {
        List<Integer> list = singletonList(5);
        CartesianIterator<Integer> iterator = new CartesianIterator<>(singletonList(list));

        assertEquals(iteratorToList(iterator), singletonList(
            singletonList(5)
        ));
    }

    @Test
    public void singleProductOfMultipleItemsList() {
        List<Integer> list = asList(5, 2, 3);
        CartesianIterator<Integer> iterator = new CartesianIterator<>(singletonList(list));

        assertEquals(iteratorToList(iterator), asList(
            singletonList(5),
            singletonList(2),
            singletonList(3)
        ));
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void noSuchElementExceptionWithSingleProductOfZeroItems() {
        CartesianIterator<Integer> iterator = new CartesianIterator<>(singletonList(emptyList()));

        iterator.next();
    }

    @Test
    public void pairProductOfSingletonLists() {
        List<List<Integer>> list = asList(
            singletonList(3),
            singletonList(1)
        );
        CartesianIterator<Integer> iterator = new CartesianIterator<>(list);

        assertEquals(iteratorToList(iterator), singletonList(
            asList(3, 1)
        ));
    }

    @Test
    public void pairProductOfSingletonAndMultipleItemList() {
        List<List<Integer>> list = asList(singletonList(1), asList(2, 3));
        CartesianIterator<Integer> iterator = new CartesianIterator<>(list);

        assertEquals(iteratorToList(iterator), asList(
            asList(1, 2),
            asList(1, 3)
        ));
    }

    @Test
    public void pairProductOfMultipleItemLists() {
        List<List<Integer>> list = asList(asList(1, 2), asList(3, 4, 5));
        CartesianIterator<Integer> iterator = new CartesianIterator<>(list);

        assertEquals(iteratorToList(iterator), asList(
            asList(1, 3),
            asList(1, 4),
            asList(1, 5),
            asList(2, 3),
            asList(2, 4),
            asList(2, 5)
        ));
    }

    @Test
    public void pairProductWithEmptyList() {
        List<List<Integer>> list = asList(emptyList(), asList(5, 6));
        CartesianIterator<Integer> iterator = new CartesianIterator<>(list);

        assertEquals(iteratorToList(iterator), emptyList());
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void noSuchElementExceptionWithPairProductOfOneItem() {
        CartesianIterator<Integer> iterator = new CartesianIterator<>(asList(singletonList(1), singletonList(2)));

        iterator.next();
        iterator.next();
    }

    @Test
    public void tripleProductOfMultipleItemLists() {
        List<List<Integer>> list = asList(asList(1, 2), asList(3, 4), asList(5, 6));
        CartesianIterator<Integer> iterator = new CartesianIterator<>(list);

        assertEquals(iteratorToList(iterator), asList(
            asList(1, 3, 5),
            asList(1, 3, 6),
            asList(1, 4, 5),
            asList(1, 4, 6),
            asList(2, 3, 5),
            asList(2, 3, 6),
            asList(2, 4, 5),
            asList(2, 4, 6)
        ));
    }

    @Test
    public void tripleProductWithEmptyList() {
        List<List<Integer>> list = asList(asList(1, 2), emptyList(), asList(5, 6));
        CartesianIterator<Integer> iterator = new CartesianIterator<>(list);

        assertEquals(iteratorToList(iterator), emptyList());
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void noSuchElementExceptionWithTripleProductOfFourItems() {
        CartesianIterator<Integer> iterator = new CartesianIterator<>(asList(asList(1, 2), asList(3, 4), singletonList(5)));

        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
   }


    private List<List<Integer>> iteratorToList(CartesianIterator<Integer> iterator) {
        Iterable<List<Integer>> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false).collect(toList());
    }
}
