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
    private static final Pattern PROPERTY_PATTERN = Pattern.compile("^[ \t]*(\\p{Alnum}+)[ \t]*=[ \t]*\"?([^\"]*)\"?");
    private static final Pattern TIER_PATTERN = Pattern.compile("^[ \t]*item[ \t]*\\[[0-9]+\\][ \t]*:.*");
    private static final Pattern INTERVALS_PATTERN = Pattern.compile("^[ \t]*intervals[ \t]*:[ \t]*size[ \t]*=[ \t]*([0-9]+)");
    private static final Pattern INTERVAL_ITEM_PATTERN = Pattern.compile("^[ \t]*intervals[ \t]*\\[[0-9]+\\][ \t]*:.*");
    private static final Pattern POINT_PATTERN = Pattern.compile("^[ \t]*points[ \t]*:[ \t]*size[ \t]*=[ \t]*([0-9.]*)");

    public TextGridSerializer() {
    }

    /******************************************************************************
     ** Importing
     ******************************************************************************/
    public TextGrid fromString(String str_tgt) throws TextGridIOException {
	TextGrid tgt = new TextGrid();
	ArrayList<String> lines = new ArrayList<String>(Arrays.asList(str_tgt.split(LINE_SEPARATOR +"+")));
	int i = 0;

	String tmp_line = lines.remove(0);
	if (! tmp_line.equals("File type = \"ooTextFile\"")) {
	    throw new TextGridIOException("Header is not correctly formatted (" + tmp_line + ")");
	}

	tmp_line = lines.remove(0);
	if (! tmp_line.equals("Object class = \"TextGrid\"")) {
	    throw new TextGridIOException("Header is not correctly formatted (" + tmp_line + ")");
	}


	if (lines.get(0).startsWith("xmin")) {
	    // Extract start
	    String tmp = lines.remove(0);
	    Matcher m = PROPERTY_PATTERN.matcher(tmp);
	    if (!m.find()) {
		throw new TextGridIOException("start line is not correctly formatted");
	    }
	    double xmin = Double.parseDouble(m.group(2));

	    // Extract end
	    tmp = lines.remove(0);
	    m = PROPERTY_PATTERN.matcher(tmp);
	    if (!m.find()) {
		throw new TextGridIOException("end line is not correctly formatted");
	    }
	    double xmax = Double.parseDouble(m.group(2));

	    // remove FIXME: why this line ?
	    lines.remove(0);

	    // Nb of tiers
	    tmp = lines.remove(0);
	    m = PROPERTY_PATTERN.matcher(tmp);
	    if (!m.find()) {
		throw new TextGridIOException("nb_tiers line is not correctly formatted");
	    }
	    int nb_tiers = Integer.parseInt(m.group(2));

	    // Header announcing items
	    lines.remove(0);

	    ArrayList<Tier> tiers = readLongTextGrid(lines);
	    if (tiers.size() != nb_tiers) {
		throw new TextGridIOException("Inconsistency between the number of tiers parsed (" + tiers.size() + ") and the expected number of tiers (" + nb_tiers + ")");
	    }

	    tgt = new TextGrid(null, xmin, xmax, tiers);

	} else {
	    throw new TextGridIOException("Short format not supported yet or invalid line: \"" + lines.get(0) + "\"");
	}

	return tgt;
    }

    public ArrayList<Tier> readLongTextGrid(List<String> lines) throws TextGridIOException {
	ArrayList<Tier> tiers = new ArrayList<Tier>();
	Tier t;
	Matcher m = TIER_PATTERN.matcher(lines.get(0));
	while (m.find()) {
	    lines.remove(0);

	    // Class should be the next line !
	    m = PROPERTY_PATTERN.matcher(lines.remove(0));
	    if (m.find() && ("class".equals(m.group(1)))) {
		if ("IntervalTier".equals(m.group(2))) {
		    t = readLongIntervalTier(lines);
		} else if ("TextTier".equals(m.group(2))) {
		    t = readLongPointTier(lines);
		} else {
		    throw new TextGridIOException("Unknown class of tier");
		}
	    } else {
		throw new TextGridIOException("The class of the tier should be defined here");
	    }
	    tiers.add(t);


	    if (lines.size() == 0)
		break;

	    m = TIER_PATTERN.matcher(lines.get(0));
	}

	// Class is forced to be the first information for the tier !
	return tiers;
    }

    public Tier readLongIntervalTier(List<String> lines) throws TextGridIOException {
	double start = -1;
	double end = -1;
	String name = null;
	String type = null;

	// Tier header
	Matcher m = INTERVALS_PATTERN.matcher(lines.get(0));
	while (! m.find()) {

	    String line = lines.get(0);
	    m = PROPERTY_PATTERN.matcher(line);
	    if (m.find()) {
		if (m.group(1).equals("name")) {
		    name = m.group(2);
		} else if (m.group(1).equals("xmin")) {
		    start = Double.parseDouble(m.group(2));
		} else if (m.group(1).equals("xmax")) {
		    end = Double.parseDouble(m.group(2));
		} else {
		    throw new TextGridIOException("Property " + m.group(1) + " is unknown for a tier");
		}
	    } else {
		 m = INTERVALS_PATTERN.matcher(line);
		 if (! m.find())
		     throw new TextGridIOException("A property is expected here: " + line);
	    }

	    lines.remove(0);
	    m = INTERVALS_PATTERN.matcher(lines.get(0));
	}



	ArrayList<Annotation> annotations = new ArrayList<Annotation>();
	lines.remove(0);
	m = INTERVAL_ITEM_PATTERN.matcher(lines.get(0));
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
		    throw new TextGridIOException("Property " + m.group(1) + " is unknown for an annotation");
		}

		if (lines.size() == 0)
		    break;

		m = PROPERTY_PATTERN.matcher(lines.get(0));
	    }

	    Annotation annotation = new IntervalAnnotation(start_an, end_an, text);
	    annotations.add(annotation);

	    if (lines.size() == 0)
		break;

	    m = INTERVAL_ITEM_PATTERN.matcher(lines.get(0));
	}

	return new IntervalTier(name, start, end, annotations);
    }

    public Tier readLongPointTier(List<String> lines) throws TextGridIOException {
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
		    throw new TextGridIOException("Property " + m.group(1) + " is unknown for a tier");
		}
	    } else {
		throw new TextGridIOException("Expecting a property here");
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
		    throw new TextGridIOException("Property " + m.group(1) + " is unknown for an annotation");
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
    public String toString(TextGrid tgt) throws TextGridIOException {

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

	    str_tgt += "\titem [" + (t+1) + "]:" + LINE_SEPARATOR;

	    if (tier instanceof IntervalTier) {
		str_tgt += "\t\tclass = \"IntervalTier\"" + LINE_SEPARATOR;
	    } else if (tier instanceof PointTier) {
		str_tgt += "\t\tclass = \"TextTier\"" + LINE_SEPARATOR;
	    } else {
		throw new TextGridIOException(tier.getClass().toString() + " serialization is not supported");
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
		    str_tgt += "\t\t\tintervals [" + (a+1) + "]:" + LINE_SEPARATOR;
		    str_tgt += "\t\t\t\txmin = " + an.getStart() + LINE_SEPARATOR;
		    str_tgt += "\t\t\t\txmax = " + an.getEnd() + LINE_SEPARATOR;
		    str_tgt += "\t\t\t\ttext = \"" + an.getText() + "\"" + LINE_SEPARATOR;
		}
	    } else if (tier instanceof PointTier) { //
		for (int a=0; a<annotations.size(); a++) {
		    PointAnnotation an = (PointAnnotation) annotations.get(a);
		    str_tgt += "\t\t\tpoints [" + (a+1) + "]:" + LINE_SEPARATOR;
		    str_tgt += "\t\t\t\tnumber = " + an.getTime() + LINE_SEPARATOR;
		    str_tgt += "\t\t\t\tmark = \"" + an.getText() + "\"" + LINE_SEPARATOR;
		}
	    }
	}

	return str_tgt;
    }
}

/* TextGridSerializer.java ends here */
