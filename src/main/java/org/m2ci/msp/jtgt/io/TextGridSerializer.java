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
    private static final String LINE_SEPARATOR = "\n";

    public TextGridSerializer() {
    }

    /******************************************************************************
     ** Importing
     ******************************************************************************/
    public TextGrid fromString(String str_tgt) {
	TextGrid tgt = new TextGrid();
	ArrayList<String> lines = str_tgt.split(LINE_SEPARATOR);
	int i = 0;

	if (! lines.remove(0).equals("File type = \"ooTextFile\"")) {
	    // FIXME: throw an exception that the file is incorrectly formatted
	}

	if (! lines.remove(0).equals("Object class = \"TextGrid\"")) {
	    // FIXME: throw an exception that the file is incorrectly formatted
	}


	if (! lines.get(0).startsWith("xmin")) {

	    // Extract start

	    // Extract end
	} else {
	    // FIXME: throw an exception that short format is not supported yet
	}

	return tgt;
    }

    public ArrayList<Tier> readLongTextGrid(ArrayList<String> lines) {
	ArrayList<Tier> tiers = new ArrayList<Tier>();

	return tiers;
    }

    public Tier readLongIntervalTier(ArrayList<String> lines) {
    }

    public Tier readLongPointTier(ArrayList<String> lines) {
    }

    /******************************************************************************
     ** Exporting
     ******************************************************************************/
    public String toString(TextGrid tgt) {

	String str_tgt = "File type = \"ooTextFile\"" + LINE_SEPARATOR;
	str_tgt += "Object class = \"TextGrid\"" + LINE_SEPARATOR;
	str_tgt += LINE_SEPARATOR;
	str_tgt += "xmin = " + tgt.getStart() + LINE_SEPARATOR;
	str_tgt += "xmax = " + tgt.getEnd() + LINE_SEPARATOR;
	str_tgt += "tiers? <exists>" + LINE_SEPARATOR;

	// Tier export
	ArrayList<Tier> tiers = tgt.getTiers();
	str_tgt += "size = " + tiers.size() + LINE_SEPARATOR;

	str_tgt += "item []:" + LINE_SEPARATOR;
	for (int t=0; t<tiers.size(); t++) {

	    // Get the current tier
	    Tier tier = tiers.get(t);

	    str_tgt += "\titem[" + (t+1) + "]" + LINE_SEPARATOR;

	    if (tier instanceof IntervalTier) {
		str_tgt += "\t\tclass = \"IntervalTier\"" + LINE_SEPARATOR;
	    } else if (tier instanceof PointTier) {
		str_tgt += "\t\tclass = \"TextTier\"" + LINE_SEPARATOR;
	    } else {
		// FIXME: throw an exception
	    }
	    str_tgt += "\t\tname = \"" + tier.getName() +  "\"" + LINE_SEPARATOR;
	    str_tgt += "\t\txmin = " + tier.getStart() + LINE_SEPARATOR;
	    str_tgt += "\t\txmax = " + tier.getEnd() + LINE_SEPARATOR;


	    ArrayList<Annotation> annotations = tier.getAnnotations();
	    str_tgt += "\t\tintervals: size = " + annotations.size() + LINE_SEPARATOR;

	    // Each annotations
	    if (tier instanceof IntervalTier) {
		for (int a=0; a<annotations.size(); a++) {
		    IntervalAnnotation an = (IntervalAnnotation) annotations.get(a);
		    str_tgt += "\t\t\tintervals [" + (a+1) + "]";
		    str_tgt += "\t\t\t\txmin = " + an.getStart() + LINE_SEPARATOR;
		    str_tgt += "\t\t\t\txmax = " + an.getEnd() + LINE_SEPARATOR;
		    str_tgt += "\t\t\t\ttext = " + an.getText() + LINE_SEPARATOR;
		}
	    } else if (tier instanceof PointTier) { //
		for (int a=0; a<annotations.size(); a++) {
		    PointAnnotation an = (PointAnnotation) annotations.get(a);
		    str_tgt += "\t\t\tpoints [" + (a+1) + "]";
		    str_tgt += "\t\t\t\tnumber = " + an.getTime() + LINE_SEPARATOR;
		    str_tgt += "\t\t\t\tmark = " + an.getText() + LINE_SEPARATOR;
		}
	    }
	}

	return str_tgt;
    }



}

/* TextGridSerializer.java ends here */
