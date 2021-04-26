package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class NonBlockingMonoController {

    private Mono<List<Integer>> getData() {
        return Mono.defer(() -> Mono.just(SourceGenerator.generateList(10000)))
                .delaySubscription(Duration.ofMillis(200));
    }

    private Mono<List<OutContainer>> serviceLayer() {
        return getData().map(l -> l.stream()
                .map(i -> new OutContainer(i, i * 2))
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/nonblockingmono")
    public Mono<List<OutContainer>> callNonBlockingMono() {
        return serviceLayer();
    }
}
