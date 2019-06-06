package xyz.nhtzr.mergestreams;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Comparator.*;
import static java.util.Spliterator.ORDERED;

public class Main {

    public static void main(String[] args) {
        List<Bitacora> concat = Stream.of(
                generateBitacoraStream(1),
                generateBitacoraStream(2),
                generateBitacoraStream(3),
                generateBitacoraStream(4),
                generateBitacoraStream(5),
                generateBitacoraStream(6),
                generateBitacoraStream(7))
                .flatMap(i -> i)
                .collect(Collectors.toList());


        Comparator<Bitacora> comparator = nullsLast(comparing(
                i -> i.getFechaInicio().getTime(),
                naturalOrder()));
        List<Bitacora> merged = mergeStreams(
                comparator,
                generateBitacoraStream(1),
                generateBitacoraStream(2),
                generateBitacoraStream(3),
                generateBitacoraStream(4),
                generateBitacoraStream(5),
                generateBitacoraStream(6),
                generateBitacoraStream(7))
                .collect(Collectors.toList());


        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        System.out.println("====== Merged: (By FechaInicio) ");
        System.out.println(gson.toJson(merged));
        System.out.println("====== Concat: ");
        System.out.println(gson.toJson(concat));
    }

    @SafeVarargs
    private static Stream<Bitacora> mergeStreams(
            Comparator<Bitacora> comparator,
            Stream<Bitacora>... streams) {
        MergingIterator<Bitacora> iterator = MergingIterator.fromStreams(comparator, streams);
        Spliterator<Bitacora> spliterator = Spliterators.spliteratorUnknownSize(iterator, ORDERED);
        return StreamSupport.stream(spliterator, false);
    }

    private static Stream<Bitacora> generateBitacoraStream(final int seed) {
        return Stream
                .iterate(seed, i1 -> i1 + seed)
                .map(i -> new Bitacora(i, seed, daysAfter(i)))
                .limit(5);
    }

    private static Date daysAfter(Integer i) {
        Calendar calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, i);
        return calendar.getTime();
    }

}
