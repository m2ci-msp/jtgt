package org.m2ci.msp.jtgt;

import java.util.ArrayList;

/**
 * A helper class to represent a tier in the textgrid
 *
 * @author <a href="mailto:slemaguer@coli.uni-saarland.de">Sébastien Le Maguer</a>
 */
public abstract class Tier {
    /** List of annotations */
    private ArrayList<Annotation> _annotations;

    /** Start of the tier */
    private double _start;

    /** End of the tier */
    private double _end;

    /** Name of the tier */
    private String _name;


    /*********************************************************************************************
     ** Constructors
     *********************************************************************************************/

    /**
     * Constructor of an empty tier
     *
     * @param name name of the tier
     */
    protected Tier(String name) {
        setStart(-1);
        setEnd(-1);
        setName(name);
        setAnnotations(new ArrayList<Annotation>());
    }

    /**
     * Constructor of pre-filled tier
     *
     * @param name the name of the tier
     * @param start the start time of the tier
     * @param end the end time of the tier
     * @param annotations the list of annotations
     */
    protected Tier(String name, double start, double end, ArrayList<Annotation> annotations) {
        setName(name);
        setStart(start);
        setEnd(end);
        setAnnotations(annotations);
    }

    /*********************************************************************************************
     ** Accessors
     *********************************************************************************************/

    /**
     * Get the start time of the tier
     *
     * @return the start time of the tier
     */
    public double getStart() {
        return _start;
    }

    /**
     * Get the end time of the tier
     *
     * @return the end time of the tier
     */
    public double getEnd() {
        return _end;
    }

    /**
     * Get the name of the tier
     *
     * @return the name of the tier
     */
    public String getName() {
        return _name;
    }

    /**
     * Get the list of annotations composing the tier
     *
     * @return the list of annotations composing the tier
     */
    public ArrayList<Annotation> getAnnotations() {
        return _annotations;
    }

    /**
     * Set the start time of the tier
     *
     * @param start the start time of the tier
     */
    public void setStart(double start) {
        this._start = start;
    }

    /**
     * Set the end time of the tier
     *
     * @param end the end time of the tier
     */
    public void setEnd(double end) {
        this._end = end;
    }

    /**
     * Set the name of the tier
     *
     * @param name the name of the tier
     */
    public void setName(String name) {
        this._name = name;
    }

    /**
     * Set the annotations composing the tier
     *
     * @param annotations the list of annotations composing the tiers
     */
    public void setAnnotations(ArrayList<Annotation> annotations) {
        this._annotations = annotations;
    }

    /**
     * Add an annotation at the end of the annotation list composing the tier
     *
     * @param annotation the annotation to add
     */
    public void addAnnotation(Annotation annotation) {
        _annotations.add(annotation);


        // Adapt the end time if adding the annotation implies a change
        if (getEnd() < annotation.getEnd()) {
            setEnd(annotation.getEnd());
        }
    }

    /**
     * Add a list of annotations at the end of the annotation list composing the tier
     *
     * @param annotations the list of annotations to add
     */
    public void addAnnotations(ArrayList<Annotation> annotations) {
        Annotation end_it = null;
        for (Annotation an : annotations) {
            end_it = an;
            _annotations.add(an);
        }

        // Adapt the end time if adding the annotations implies a change
        if ((end_it != null) && (getEnd() < end_it.getEnd())) {
            setEnd(end_it.getEnd());
        }
    }


    /**
     * Check if a given object is equal to the current tier
     *
     * @param o the given object
     * @return true if equality, false else
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof Tier)) {
            return false;
        }

        return ((getStart() == ((Tier) o).getStart()) &&
                (getEnd() == ((Tier) o).getEnd())  &&
                getName().equals(((Tier) o).getName()) &&
                getAnnotations().equals(((Tier) o).getAnnotations()));
    }
}

/* Tier.java ends here */
