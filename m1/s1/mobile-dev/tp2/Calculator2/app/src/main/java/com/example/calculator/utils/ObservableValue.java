package com.example.calculator.utils;

public class ObservableValue<T> {
    private T value;
    private Runnable onChange;

    public ObservableValue(T value, Runnable onChange) {
        this.value = value;
        this.onChange = onChange;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        if (onChange != null) {
            onChange.run();
        }
    }
}