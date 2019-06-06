package xyz.nhtzr.mergestreams;

import java.util.*;
import java.util.stream.Stream;

public class MergingIterator<T> implements Iterator<T> {

    private final PriorityQueue<LookAheadIterator<T>> queue;

    public MergingIterator(PriorityQueue<LookAheadIterator<T>> queue) {
        this.queue = queue;
    }

    @SafeVarargs
    public static <T> MergingIterator<T> fromStreams(
            Comparator<? super T> comparator,
            Stream<T>... streams
    ) {
        return fromStreams(Arrays.stream(streams), comparator);
    }

    public static <T> MergingIterator<T> fromStreams(
            Stream<Stream<T>> streams,
            Comparator<? super T> comparator
    ) {
        PriorityQueue<LookAheadIterator<T>> queue = new PriorityQueue<>(
                (l, r) -> comparator.compare(l.peek(), r.peek()));
        streams
                .map(Stream::iterator)
                .map(LookAheadIterator::new)
                .forEach(queue::add);

        return new MergingIterator<>(queue);
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public T next() {
        final LookAheadIterator<T> nextIt = queue.poll();
        if (nextIt == null) {
            throw new NoSuchElementException();
        }
        final T next = nextIt.next();
        if (nextIt.hasNext()) {
            queue.add(nextIt);
        }
        return next;
    }

}
