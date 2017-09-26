package org.m2ci.msp.jtgt.io;

import org.m2ci.msp.jtgt.*;
import org.m2ci.msp.jtgt.tier.*;
import org.m2ci.msp.jtgt.annotation.*;
import java.io.IOException;


import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.MatchResult;

/**
 *
 *
 * @author <a href="mailto:slemaguer@coli.uni-saarland.de">Sébastien Le Maguer</a>
 */
public class XWaveLabelSerializer {
    /** A line separator wrapper */
    private static final String DEFAULT_LINE_SEPARATOR = "\n";

    /** Line separator regexp */
    private static final String LINE_SEPARATOR_PATTERN =  "[\\n\\r]+";

    /** The header end indicator */
    private static final Pattern HEADER_SEP = Pattern.compile("^#.*");

    /** Element parsing regexp */
    private static final Pattern ELT_PATTERN = Pattern.compile("^[ \t]*([0-9.]+)[ \t]*-?[0-9]+[ \t]*([^ \t]*)[ \t]*$");

    /**
     * Default constructor
     *
     */
    public XWaveLabelSerializer() {
    }

    /******************************************************************************
     ** Importing
     ******************************************************************************/
    /**
     * Load the string formatted XWave label file into the textgrid. Not suspported for noaw
     *
     * @param str_xwave the xwave label in a string format
     * @return the loaded textgrid object
     * @throws TextGridIOException if a problem occurs. See message for more explanation
     * @throws UnsupportedOperationException as this is not supported yet
     */
    public TextGrid fromString(String str_xwave) throws TextGridIOException {
        TextGrid tg = new TextGrid();
        String[] lines = str_xwave.split(LINE_SEPARATOR_PATTERN);

        IntervalTier the_tier = new IntervalTier("phones");

        // Start is 0 by convention
        tg.setStart(0);
        the_tier.setStart(0);
        double start = 0.0;

        boolean header_end = false;
        for (String line : lines) {
            if (! header_end) {
                Matcher m = HEADER_SEP.matcher(line);
                if (m.find()) {
                    header_end = true;
                }
            } else {
                Matcher m = ELT_PATTERN.matcher(line);
                if (m.find()) {
                    double end = Double.parseDouble(m.group(1));
                    the_tier.addAnnotation(new IntervalAnnotation(start, end, m.group(2)));
                    start = end;
                } else {
                    throw new TextGridIOException("This line should not be in the content part: " + line);
                }

            }
        }

        // Last end value is save in the start variable;
        tg.setEnd(start);

        if (the_tier.getAnnotations().size() == 0) {
            throw new TextGridIOException("No annotations found !");
        }

        tg.addTier(the_tier);

        return tg;
    }

    /******************************************************************************
     ** Exporting
     ******************************************************************************/
    /**
     * Export a given tier to an XWave formatted label file.
     *
     * @param tgt the textgrid
     * @param tier_name the name of the tier we want to export
     * @return the XWave formatted label file
     * @throws TextGridIOException if a problem occurs. See message for more explanation
     */
    public String toString(TextGrid tgt, String tier_name) throws TextGridIOException {

        String str_xwav_lab = "separator ;" + DEFAULT_LINE_SEPARATOR;
        str_xwav_lab += "nfields 1" + DEFAULT_LINE_SEPARATOR;
        str_xwav_lab += "#" + DEFAULT_LINE_SEPARATOR;

        // Find the accurate tier to export
        ArrayList<Tier> tiers = tgt.getTiers();
        Tier tier = null;
        int t = 0;
        boolean found = false;
        while ((t < tiers.size()) && (! found)) {
            tier = tiers.get(t);
            if (tier_name.equals(tier.getName())) {
                found = true;
            }

            t++;
        }

        if (! found) {
            throw new TextGridIOException("Cannot find the tier \"" + tier_name + "in the textgrid\"");
        }

        // Each annotations
        if (tier instanceof IntervalTier) {
            ArrayList<Annotation> annotations = tier.getAnnotations();
            for (int a = 0; a < annotations.size(); a++) {
                IntervalAnnotation an = (IntervalAnnotation) annotations.get(a);
                str_xwav_lab += "\t" + an.getEnd() + " -1 " + an.getText() + DEFAULT_LINE_SEPARATOR;
            }
        } else {
            throw new TextGridIOException("Don't know how to serialize anaything execpt an IntervalTier");
        }

        return str_xwav_lab;
    }
}

/* XWaveLabelSerializer.java ends here */
