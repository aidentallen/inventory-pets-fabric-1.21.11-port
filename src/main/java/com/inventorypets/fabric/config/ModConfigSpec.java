package com.inventorypets.fabric.config;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Small value facade used while preserving the original configuration call sites.
 * Persistence is handled by the Fabric config loader rather than this builder.
 */
public final class ModConfigSpec {
    private final Map<String, ConfigValue<?>> values;

    private ModConfigSpec(Map<String, ConfigValue<?>> values) {
        this.values = Map.copyOf(values);
    }

    public Map<String, ConfigValue<?>> values() {
        return values;
    }

    public static class ConfigValue<T> {
        private T value;

        public ConfigValue(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }

        public void set(T value) {
            this.value = value;
        }
    }

    public static final class BooleanValue extends ConfigValue<Boolean> {
        public BooleanValue(boolean value) {
            super(value);
        }
    }

    public static final class IntValue extends ConfigValue<Integer> {
        private final int minimum;
        private final int maximum;

        public IntValue(int value, int minimum, int maximum) {
            super(value);
            this.minimum = minimum;
            this.maximum = maximum;
        }

        @Override
        public void set(Integer value) {
            super.set(Math.max(minimum, Math.min(maximum, value)));
        }
    }

    public static final class Builder {
        private final Map<String, ConfigValue<?>> values = new LinkedHashMap<>();

        public Builder comment(String ignored) {
            return this;
        }

        public Builder push(String ignored) {
            return this;
        }

        public Builder pop() {
            return this;
        }

        public Builder gameRestart() {
            return this;
        }

        public BooleanValue define(String key, boolean defaultValue) {
            BooleanValue value = new BooleanValue(defaultValue);
            values.put(key, value);
            return value;
        }

        @SuppressWarnings("unchecked")
        public <T> ConfigValue<T> define(String key, T defaultValue) {
            ConfigValue<T> value = new ConfigValue<>(defaultValue);
            values.put(key, value);
            return value;
        }

        public IntValue defineInRange(String key, int defaultValue, int minimum, int maximum) {
            IntValue value = new IntValue(defaultValue, minimum, maximum);
            values.put(key, value);
            return value;
        }

        public ModConfigSpec tryBuildTag() {
            return new ModConfigSpec(values);
        }
    }
}
