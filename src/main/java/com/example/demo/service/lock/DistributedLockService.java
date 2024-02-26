package com.example.demo.service.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class DistributedLockService {
    private static final long DEFAULT_RETRY_TIME = 2000L;
    private final ValueOperations<String, String> valueOperations;

    public <T> LockExecutionResult<T> lock(final String key,
                                           final int howLongShouldLockBeAcquiredSeconds,
                                           final int lockTimeoutSeconds,
                                           final Callable<T> task) {
        boolean lockAcquired = tryToGetLock(key, lockTimeoutSeconds, howLongShouldLockBeAcquiredSeconds);
        if (!lockAcquired) {
            log.error("Failed to acquire lock for key '{}'", key);
            return LockExecutionResult.lockNotAcquired();
        }

        log.info("Successfully acquired lock for key '{}'", key);
        try {
            T taskResult = task.call();
            return LockExecutionResult.buildLockAcquiredResult(taskResult);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return LockExecutionResult.buildLockAcquiredWithException(e);
        } finally {
            releaseLock(key);
        }
    }

    public boolean tryToGetLock(final String lockKey,
                                final int lockTimeoutSeconds,
                                final int howLongShouldLockBeAcquiredSeconds) {
        final long tryToGetLockTimeout = TimeUnit.SECONDS.toMillis(howLongShouldLockBeAcquiredSeconds);

        final long startTimestamp = System.currentTimeMillis();
        while (true) {
            log.info("Trying to get the lock with key '{}'", lockKey);
            final Boolean lockAcquired = valueOperations.setIfAbsent(lockKey, lockKey, lockTimeoutSeconds, TimeUnit.SECONDS);
            if (Boolean.TRUE.equals(lockAcquired)) {
                return true;
            }
            sleep(DEFAULT_RETRY_TIME);

            if (System.currentTimeMillis() - startTimestamp > tryToGetLockTimeout) {
                return false;
            }
        }
    }

    public void releaseLock(final String key) {
        log.info("Release lock");
        valueOperations.getOperations().delete(key);
    }

    private static void sleep(final long sleepTimeMillis) {
        try {
            Thread.sleep(sleepTimeMillis);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(e.getMessage(), e);
        }
    }
}
