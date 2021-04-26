package com.example.demo;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface SourceGenerator {
    static List<Integer> generateList(int size) {
        return IntStream.range(1, size)
                .boxed().collect(Collectors.toList());
    }
    static Flux<Integer> generateFlux(int size) {
        Flux<Integer> sequence =  Flux.generate(() -> 1, (state, sink) -> {
           sink.next(state);
           return state + 1;
        });
        return sequence.take(size);
    }
}
