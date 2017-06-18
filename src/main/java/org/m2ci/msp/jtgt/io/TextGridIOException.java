package org.m2ci.msp.jtgt.io;

import java.io.IOException;

/**
 * A specific exception for TextGrid IO errors
 *
 * @author <a href="mailto:slemaguer@coli.uni-saarland.de">Sébastien Le Maguer</a>
 */
public class TextGridIOException extends IOException {

    /**
     * Default Constructor
     *
     * @param message the associated message with the exception
     */
    public TextGridIOException(String message) {
        super(message);
    }


    /**
     * Verbose constructor
     *
     * @param message the associated message with the exception
     * @param filename the filename of the input causing the IO exception
     * @param line the problematic line
     */
    public TextGridIOException(String message, String filename, int line) {
        super(filename + "(" + line + "): " + message);
    }
}


/* TextGridIOException.java ends here */
