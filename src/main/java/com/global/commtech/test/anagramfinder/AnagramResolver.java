package com.global.commtech.test.anagramfinder;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Given a file of words,
 */
public interface AnagramResolver {

    void resolve(Path anagramFile) throws IOException;

}
