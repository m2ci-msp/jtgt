package org.m2ci.msp.jtgt.annotation;

import org.m2ci.msp.jtgt.Annotation;

/**
 *
 *
 * @author <a href="mailto:slemaguer@coli.uni-saarland.de">Sébastien Le Maguer</a>
 */
public class IntervalAnnotation extends Annotation {
    /**
     * Constructor of an interval annotation
     *
     * @param start the start of the annotation
     * @param end the end of the annotation
     * @param text the annotation label
     */
    public IntervalAnnotation(double start, double end, String text) {
        super(start, end, text);
    }
}


/* IntervalAnnotation.java ends here */
