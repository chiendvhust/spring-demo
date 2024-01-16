package com.example.demo.service;

import com.example.demo.service.lock.DistributedLocker;
import com.example.demo.service.lock.LockExecutionResult;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {
	private final DistributedLocker locker;

	@PostConstruct
	private void setup() {
		CompletableFuture.runAsync(() -> runTask("1", 3000));
		CompletableFuture.runAsync(() -> runTask("2", 1000));
		CompletableFuture.runAsync(() -> runTask("3", 100));
	}

	private void runTask(final String taskNumber, final long sleep) {
		log.info("Running task : '{}'", taskNumber);

		LockExecutionResult<String> result = locker.lock("some-key", 5, 6, () -> {
			log.info("Sleeping for '{}' ms", sleep);
			Thread.sleep(sleep);
			log.info("Executing task '{}'", taskNumber);
			return taskNumber;
		});

		log.info("Task result : '{}' -> exception : '{}'", result.getResultIfLockAcquired(), result.hasException());
	}
}
