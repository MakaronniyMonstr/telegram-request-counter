package com.vesko.dynamicconfiguration.util;

import lombok.experimental.UtilityClass;
import org.springframework.boot.ApplicationArguments;

import java.util.List;
import java.util.Optional;

@UtilityClass
public class ArgumentReaderUtils {
    public static Optional<String> readSingleArgument(ApplicationArguments args, String name) {
        List<String> optionValues = args.getOptionValues(name);
        if (optionValues == null || optionValues.isEmpty()) {
            return Optional.empty();
        }
        if (optionValues.size() > 1) {
            throw new IllegalArgumentException("Program argument " + name + " must be single value");
        }
        return Optional.of(optionValues.get(0));
    }

    public static String readRequiredSingleArgument(ApplicationArguments args, String name) {
        return readSingleArgument(args, name)
                .orElseThrow(() -> new IllegalArgumentException(name + " argument is required"));
    }
}
