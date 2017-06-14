package org.m2ci.msp.jtgt.tier;

import java.util.ArrayList;

import org.m2ci.msp.jtgt.Tier;
import org.m2ci.msp.jtgt.Annotation;


/**
 * A helper class to represent a point tier in the textgrid
 *
 * @author <a href="mailto:slemaguer@coli.uni-saarland.de">Sébastien Le Maguer</a>
 */
public class PointTier extends Tier
{


    /*********************************************************************************************
     ** Constructors
     *********************************************************************************************/

    /**
     * Constructor of an empty tier
     *
     * @param name name of the tier
     */
    public PointTier(String name)
    {
	super(name);
    }

    /**
     * Constructor of pre-filled tier
     *
     * @param name the name of the tier
     * @param start the start time of the tier
     * @param end the end time of the tier
     * @param annotations the list of annotations
     */
    public PointTier(String name, double start, double end, ArrayList<Annotation> annotations) {
	super(name, start, end, annotations);
    }
}

/* PointTier.java ends here */
