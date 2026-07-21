package dev.enes.ulak.common;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;

import java.security.SecureRandom;
import java.util.UUID;

public final class UuidV7 {

    private static final NoArgGenerator GENERATOR = Generators.timeBasedEpochGenerator();

    private UuidV7() {}
    public static UUID generate() {
        return GENERATOR.generate();
    }


}
