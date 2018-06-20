package org.m2ci.msp.jtgt;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * TextGrid annotation wrapper class
 *
 * @author <a href="mailto:slemaguer@coli.uni-saarland.de">Sébastien Le Maguer</a>
 */
public abstract class Annotation {
    /** The start of the annotation */
    private double _start;

    /** The end of the annotation */
    private double _end;

    /** The text of the annotation */
    private String _text;

    /**
     * Constructor of an annotation
     *
     * @param start the start of the annotation
     * @param end the end of the annotation
     * @param text the annotation label
     */
    protected Annotation(double start, double end, String text) {
        setStart(start);
        setEnd(end);
        setText(text);
    }

    /**
     * Get the start of the annotation
     *
     * @return the start of the annotation
     */
    public double getStart() {
        return _start;
    }

    /**
     * Get the end of the annotation
     *
     * @return the end of the annotation
     */
    public double getEnd() {
        return _end;
    }


    /**
     * Get the label of the annotation
     *
     * @return the label of the annotation
     */
    public String getText() {
        return _text;
    }

    /**
     * Define the start of the annotation
     *
     * @param start the new start
     */
    public void setStart(double start) {
        _start = start;
    }

    /**
     * Define the end of the annotation
     *
     * @param end the new end
     */
    public void setEnd(double end) {
        _end = end;
    }

    /**
     * Define the label of the annotation
     *
     * @param text the new label
     */
    public void setText(String text) {
        _text = text;
    }

    /**
     * Check if a given object is equal to the current annotation
     *
     * @param o the given object
     * @return true if equality, false else
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof Annotation)) {
            return false;
        }

        return ((getStart() == ((Annotation) o).getStart()) &&
                (getEnd() == ((Annotation) o).getEnd())  &&
                getText().equals(((Annotation) o).getText()));
    }

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
    }
}


/* Annotation.java ends here */
