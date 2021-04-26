package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BlockingController {
    private List<Integer> getData(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return SourceGenerator.generateList(10000);
    }

    private List<OutContainer> serviceLayer(){
        return getData().stream()
                .map(i -> new OutContainer(i, i*2))
                .collect(Collectors.toList());
    }
    @GetMapping("/blockingcall")
    public List<OutContainer> getBlocking() {
        return serviceLayer();
    }
}
