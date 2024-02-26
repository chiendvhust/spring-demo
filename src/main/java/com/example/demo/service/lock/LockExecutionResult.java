package com.example.demo.service.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LockExecutionResult<T> {
    private final boolean lockAcquired;
    private final T resultIfLockAcquired;
    private final Exception exception;

    public static <T> LockExecutionResult<T> buildLockAcquiredResult(final T result) {
        return new LockExecutionResult<>(true, result, null);
    }

    public static <T> LockExecutionResult<T> buildLockAcquiredWithException(final Exception e) {
        return new LockExecutionResult<>(true, null, e);
    }

    public static <T> LockExecutionResult<T> lockNotAcquired() {
        return new LockExecutionResult<>(false, null, null);
    }

    public boolean hasException() {
        return exception != null;
    }
}
