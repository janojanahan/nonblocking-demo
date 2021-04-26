package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class NonBlockingFluxController {
    private Flux<Integer> getData() {
        return Flux.defer(() -> SourceGenerator.generateFlux(10000)
                .delaySubscription(Duration.ofMillis(200)));
    }

    private Flux<OutContainer> service() {
        return getData()
                .map(i -> new OutContainer(i, i*2));
    }

    @GetMapping("/fluxcall")
    public Flux<OutContainer> getFlux(){
        return service();
    }
}
