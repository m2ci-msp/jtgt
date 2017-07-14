package org.m2ci.msp.jtgt.annotation;

import org.m2ci.msp.jtgt.Annotation;

/**
 * Interval annotation wrapper class
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



    /**
     * Check if a given object is equal to the current annotation
     *
     * @param o the given object
     * @return true if equality, false else
     */
    @Override
    public boolean equals(Object o) {
	if (o == null)
	    return false;

	if (! (o instanceof IntervalAnnotation))
	    return false;

	return super.equals(o);
    }
}


/* IntervalAnnotation.java ends here */
