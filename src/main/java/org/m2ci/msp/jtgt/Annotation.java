package org.m2ci.msp.jtgt;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.JSONObject;

/**
 * TextGrid annotation wrapper class
 *
 * @author <a href="mailto:slemaguer@coli.uni-saarland.de">Sébastien Le Maguer</a>
 */
public abstract class Annotation {
    /** The start of the annotation */
    private double start;

    /** The end of the annotation */
    private double end;

    /** The text of the annotation */
    private String text;

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
        return start;
    }

    /**
     * Get the end of the annotation
     *
     * @return the end of the annotation
     */
    public double getEnd() {
        return end;
    }


    /**
     * Get the label of the annotation
     *
     * @return the label of the annotation
     */
    public String getText() {
        return text;
    }

    /**
     * Define the start of the annotation
     *
     * @param start the new start
     */
    public void setStart(double start) {
        this.start = start;
    }

    /**
     * Define the end of the annotation
     *
     * @param end the new end
     */
    public void setEnd(double end) {
        this.end = end;
    }

    /**
     * Define the label of the annotation
     *
     * @param text the new label
     */
    public void setText(String text) {
        this.text = text;
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
        String jsonStr = new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE).toString();
        return new JSONObject(jsonStr).toString(4);
    }
}


/* Annotation.java ends here */
