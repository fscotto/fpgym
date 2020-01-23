package org.gym.fp.fpjava.type;

public interface Result<T> {
    void bind(Effect<T> success, Effect<T> failure);

    static <T> Result<T> failure(String message) {
        return new Failure<>(message);
    }

    static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    class Success<T> implements Result<T> {
        private final T value;

        public Success(T value) {
            this.value = value;
        }

        @Override
        public void bind(Effect<T> success, Effect<T> failure) {
            success.apply(value);
        }
    }

    class Failure<T> implements Result<T> {
        private final String errorMessage;

        public Failure(String msg) {
            this.errorMessage = msg;
        }

        public String getMessage() {
            return this.errorMessage;
        }

        @Override
        public void bind(Effect success, Effect failure) {
            failure.apply(errorMessage);
        }
    }
}
