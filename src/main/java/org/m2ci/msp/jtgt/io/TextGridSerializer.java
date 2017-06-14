package org.m2ci.msp.jtgt.io;

import org.m2ci.msp.jtgt.*;
import org.m2ci.msp.jtgt.tier.*;
import org.m2ci.msp.jtgt.annotation.*;

import java.io.File;
import java.util.ArrayList;

/**
 *
 *
 * @author <a href="mailto:slemaguer@coli.uni-saarland.de">Sébastien Le Maguer</a>
 */
public class TextGridSerializer
{

    public TextGridSerializer() {
    }

    /******************************************************************************
     ** Importing
     ******************************************************************************/
    public TextGrid fromString(String str_tgt) {
	TextGrid tgt = new TextGrid();

	return tgt;
    }


    /******************************************************************************
     ** Exporting
     ******************************************************************************/
    public String toString(TextGrid tgt) {

	// Header
	String str_tgt = "File type = \"ooTextFile\"" + "\n";
	str_tgt += "Object class = \"TextGrid\"" + "\n";
	str_tgt += "\n";
	str_tgt += "xmin = " + tgt.getStart() + "\n";
	str_tgt += "xmax = " + tgt.getEnd() + "\n";
	str_tgt += "tiers? <exists>" + "\n";

	// Tier export
	ArrayList<Tier> tiers = tgt.getTiers();
	str_tgt += "size = " + tiers.size() + "\n";

	str_tgt += "item []:" + "\n";
	for (int t=0; t<tiers.size(); t++) {

	    // Get the current tier
	    Tier tier = tiers.get(t);

	    str_tgt += "\titem[" + (t+1) + "]" + "\n";

	    if (tier instanceof IntervalTier) {
		str_tgt += "\t\tclass = \"IntervalTier\"" + "\n";
	    } else if (tier instanceof PointTier) {
		str_tgt += "\t\tclass = \"TextTier\"" + "\n";
	    } else {
		// FIXME: throw an exception
	    }
	    str_tgt += "\t\tname = \"" + tier.getName() +  "\"" + "\n";
	    str_tgt += "\t\txmin = " + tier.getStart() + "\n";
	    str_tgt += "\t\txmax = " + tier.getEnd() + "\n";


	    ArrayList<Annotation> annotations = tier.getAnnotations();
	    str_tgt += "\t\tintervals: size = " + annotations.size() + "\n";

	    // Each annotations
	    if (tier instanceof IntervalTier) {
		for (int a=0; a<annotations.size(); a++) {
		    IntervalAnnotation an = (IntervalAnnotation) annotations.get(a);
		    str_tgt += "\t\t\tintervals [" + (a+1) + "]";
		    str_tgt += "\t\t\t\txmin = " + an.getStart() + "\n";
		    str_tgt += "\t\t\t\txmax = " + an.getEnd() + "\n";
		    str_tgt += "\t\t\t\ttext = " + an.getText() + "\n";
		}
	    } else if (tier instanceof PointTier) { //
		for (int a=0; a<annotations.size(); a++) {
		    PointAnnotation an = (PointAnnotation) annotations.get(a);
		    str_tgt += "\t\t\tpoints [" + (a+1) + "]";
		    str_tgt += "\t\t\t\tnumber = " + an.getTime() + "\n";
		    str_tgt += "\t\t\t\tmark = " + an.getText() + "\n";
		}
	    }
	}

	return str_tgt;
    }



}

/* TextGridSerializer.java ends here */
