package com.inventorypets.fabric.pet;

public record PetDefinition(String id, String configSuffix, String tooltipStem, int tooltipLines, int cooldownTicks) {
    public String foodConfigKey() {
        return configSuffix.isEmpty() ? "" : "food" + configSuffix;
    }

    public String disabledConfigKey() {
        return configSuffix.isEmpty() ? "" : "disable" + configSuffix;
    }
}
