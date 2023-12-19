package com.example.demo.service;

import com.example.demo.entity.AirPort;
import com.example.demo.repository.AirPortReposiroty;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirPortService {
    private final ValueOperations<String, AirPort> valueOperations;
    private final AirPortReposiroty airPortReposiroty;


    @PostConstruct
    public void postConstruct() {
        List<AirPort> airPorts = List.of(new AirPort("1", "abc"),
                new AirPort("2", "abc"),
                new AirPort("3", "abc"));
        airPortReposiroty.saveAll(airPorts);
    }

    public AirPort getAirPort(String code) {
        AirPort airPort = valueOperations.get(code);
        if (airPort != null) {
            log.info("run into get redis");
            return airPort;
        }
        airPort = airPortReposiroty.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Cann not find airport with code : " + code));
        log.info("run into get db");
        valueOperations.set(code, airPort, 5, TimeUnit.SECONDS);
        return airPort;
    }
}
