package xyz.nhtzr.mergestreams;

import java.util.Iterator;

public class LookAheadIterator<E> implements Iterator<E> {

    private final Iterator<E> iterator;
    private E peek;

    public LookAheadIterator(Iterator<E> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext() || this.peek != null;
    }

    @Override
    public E next() {
        E next = this.peek != null
                ? this.peek
                : iterator.next();
        this.peek = null;
        return next;
    }

    public E peek() {
        if (this.peek == null) {
            this.peek = iterator.next();
        }
        return this.peek;
    }
}
