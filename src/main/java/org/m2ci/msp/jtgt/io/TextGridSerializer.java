package org.m2ci.msp.jtgt.io;

import org.m2ci.msp.jtgt.*;
import org.m2ci.msp.jtgt.tier.*;
import org.m2ci.msp.jtgt.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 *
 * @author <a href="mailto:slemaguer@coli.uni-saarland.de">Sébastien Le Maguer</a>
 */
public class TextGridSerializer
{
    private static final String LINE_SEPARATOR = "\n";
    private static final Pattern PROPERTY_PATTERN = Pattern.compile("^[\t]*([^ ]*)[^ \t]*=[^ \t]*\"?([^ \t]*)\"?[ \t]*$");
    private static final Pattern TIER_PATTERN = Pattern.compile("^[\t]*item[^ \t]*=");
    private static final Pattern INTERVAL_PATTERN = Pattern.compile("^[\t]*intervals[^ \t]*:[^ \t]*size[^ \t]*=[^ \t]*([0-9.]*)");
    private static final Pattern POINT_PATTERN = Pattern.compile("^[\t]*points ");

    public TextGridSerializer() {
    }

    /******************************************************************************
     ** Importing
     ******************************************************************************/
    public TextGrid fromString(String str_tgt) {
	TextGrid tgt = new TextGrid();
	List<String> lines = Arrays.asList(str_tgt.split(LINE_SEPARATOR));
	int i = 0;

	if (! lines.remove(0).equals("File type = \"ooTextFile\"")) {
	    // FIXME: throw an exception that the file is incorrectly formatted
	}

	if (! lines.remove(0).equals("Object class = \"TextGrid\"")) {
	    // FIXME: throw an exception that the file is incorrectly formatted
	}


	if (! lines.get(0).startsWith("xmin")) {
	    // Extract start
	    String tmp = lines.remove(0);
	    Matcher m = PROPERTY_PATTERN.matcher(tmp);
	    if (!m.find()) {
		// FIXME: throws an exception
	    }
	    double xmin = Double.parseDouble(m.group(2));

	    // Extract end
	    tmp = lines.remove(0);
	    m = PROPERTY_PATTERN.matcher(tmp);
	    if (!m.find()) {
		// FIXME: throws an exception
	    }
	    double xmax = Double.parseDouble(m.group(2));

	    // remove FIXME: why this line ?
	    lines.remove(0);

	    // Nb of tiers
	    tmp = lines.remove(0);
	    m = PROPERTY_PATTERN.matcher(tmp);
	    if (!m.find()) {
		// FIXME: throws an exception
	    }
	    int nb_tiers = Integer.parseInt(m.group(2));

	    // Header announcing items
	    lines.remove(0);

	    ArrayList<Tier> tiers = readLongTextGrid(lines);
	    if (tiers.size() != nb_tiers) {
		// FIXME: throw an exception indicating the inconsistency
	    }

	    tgt = new TextGrid(null, xmin, xmax, tiers);

	} else {
	    // FIXME: throw an exception that short format is not supported yet
	}

	return tgt;
    }

    public ArrayList<Tier> readLongTextGrid(List<String> lines) {
	ArrayList<Tier> tiers = new ArrayList<Tier>();


	Matcher m = TIER_PATTERN.matcher(lines.get(0));
	while (m.find()) {
	    lines.remove(0);

	    // Class should be the next line !
	    m = PROPERTY_PATTERN.matcher(lines.remove(0));
	    if (m.find() && ("class".equals(m.group(1)))) {
		if ("IntervalTier".equals(m.group(2))) {
		    readLongIntervalTier(lines);
		} else if ("IntervalTier".equals(m.group(2))) {
		    readLongPointTier(lines);
		} else {
		    // FIXME: throw exception
		}
	    } else {
		// FIXME: throw Exception
	    }

	    m = TIER_PATTERN.matcher(lines.get(0));
	}

	// Class is forced to be the first information for the tier !
	return tiers;
    }

    public Tier readLongIntervalTier(List<String> lines) {
	double start = -1;
	double end = -1;
	String name = null;
	String type = null;

	// Tier header
	Matcher m = INTERVAL_PATTERN.matcher(lines.get(0));
	while (! m.find()) {
	    lines.remove(0);

	    m = PROPERTY_PATTERN.matcher(lines.remove(0));
	    if (m.find()) {
		if (m.group(1).equals("name")) {
		    name = m.group(2);
		} else if (m.group(1).equals("xmin")) {
		    start = Double.parseDouble(m.group(2));
		} else if (m.group(1).equals("xmax")) {
		    end = Double.parseDouble(m.group(2));
		} else {
		    // FIXME: throw an Exception
		}
	    } else {
		// FIXME: throw an exception
	    }

	    m = INTERVAL_PATTERN.matcher(lines.get(0));
	}



	ArrayList<Annotation> annotations = new ArrayList<Annotation>();
	while (m.find()) {
	    lines.remove(0);
	    double start_an = -1;
	    double end_an = -1;
	    String text = null;

	    m = PROPERTY_PATTERN.matcher(lines.get(0));
	    while (m.find()) {
		lines.remove(0);
		if (m.group(1).equals("text")) {
		    text = m.group(2);
		} else if (m.group(1).equals("xmin")) {
		    start_an = Double.parseDouble(m.group(2));
		} else if (m.group(1).equals("xmax")) {
		    end_an = Double.parseDouble(m.group(2));
		} else {
		    // FIXME: throw an Exception
		}

		m = PROPERTY_PATTERN.matcher(lines.get(0));
	    }

	    Annotation annotation = new IntervalAnnotation(start_an, end_an, text);
	    annotations.add(annotation);
	    m = INTERVAL_PATTERN.matcher(lines.get(0));
	}

	return new IntervalTier(name, start, end, annotations);
    }

    public Tier readLongPointTier(List<String> lines) {
		double start = -1;
	double end = -1;
	String name = null;
	String type = null;

	// Tier header
	Matcher m = POINT_PATTERN.matcher(lines.get(0));
	while (! m.find()) {
	    lines.remove(0);

	    m = PROPERTY_PATTERN.matcher(lines.remove(0));
	    if (m.find()) {
		if (m.group(1).equals("name")) {
		    name = m.group(2);
		} else if (m.group(1).equals("xmin")) {
		    start = Double.parseDouble(m.group(2));
		} else if (m.group(1).equals("xmax")) {
		    end = Double.parseDouble(m.group(2));
		} else {
		    // FIXME: throw an Exception
		}
	    } else {
		// FIXME: throw an exception
	    }

	    m = POINT_PATTERN.matcher(lines.get(0));
	}



	ArrayList<Annotation> annotations = new ArrayList<Annotation>();
	while (m.find()) {
	    lines.remove(0);
	    double time = -1;
	    String text = null;

	    m = PROPERTY_PATTERN.matcher(lines.get(0));
	    while (m.find()) {
		lines.remove(0);
		if (m.group(1).equals("mark")) {
		    text = m.group(2);
		} else if (m.group(1).equals("number")) {
		    time = Double.parseDouble(m.group(2));
		} else {
		    // FIXME: throw an Exception
		}

		m = PROPERTY_PATTERN.matcher(lines.get(0));
	    }

	    Annotation annotation = new PointAnnotation(time, text);
	    annotations.add(annotation);
	    m = POINT_PATTERN.matcher(lines.get(0));
	}

	return new PointTier(name, start, end, annotations);
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
