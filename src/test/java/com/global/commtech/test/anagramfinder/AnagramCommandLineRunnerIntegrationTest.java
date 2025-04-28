package com.global.commtech.test.anagramfinder;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest(args = {"src/test/resources/example1.txt"})
@ExtendWith(OutputCaptureExtension.class)
class AnagramCommandLineRunnerIntegrationTest {

    @Autowired
    AnagramCommandLineRunner anagramCommandLineRunner;

    @Test
    void shouldFindAnagrams(final CapturedOutput capturedOutput) {
        assertAnagramGroupIsPrinted(capturedOutput.getOut(), "abc", "bac", "cba");
        assertAnagramGroupIsPrinted(capturedOutput.getOut(), "fun", "fun", "unf");
        assertAnagramGroupIsPrinted(capturedOutput.getOut(), "hello");
    }

    private void assertAnagramGroupIsPrinted(String output, String... expectedAnagrams) {
        Predicate<List<String>> test = words ->
                words.size() == expectedAnagrams.length
                        && Arrays.stream(expectedAnagrams).allMatch(words::contains);

        boolean found = Arrays.stream(output.split("\\R"))
                .map(line -> line.split(","))
                .map(Arrays::asList)
                .anyMatch(test);

        assertThat(found).as("Expected to find group: " + String.join(",", expectedAnagrams)).isTrue();
    }

}