package org.m2ci.msp.jtgt.io;

import java.io.InputStream;
import java.util.Scanner;
import org.m2ci.msp.jtgt.TextGrid;
import org.m2ci.msp.jtgt.io.XWaveLabelSerializer;
import org.m2ci.msp.jtgt.io.TextGridSerializer;
import org.m2ci.msp.jtgt.io.TextGridIOException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.*;

/**
 *
 *
 * @author <a href="mailto:slemaguer@coli.uni-saarland.de">Sébastien Le Maguer</a>
 */
public class XWaveLabelSerializerTest {


    @Test
    public void testExportXWaveLabel() throws TextGridIOException, IOException {
        String input_resource_name = "arctic_a0001.TextGrid";
        InputStream input = this.getClass().getResourceAsStream(input_resource_name);
        String string_input = new Scanner(input, "UTF-8").useDelimiter("\\A").next();

        // Render the input
        TextGridSerializer tgs = new TextGridSerializer();
        TextGrid tg = tgs.fromString(string_input);

        XWaveLabelSerializer xwave_ser = new XWaveLabelSerializer();
        String xlab_pred = xwave_ser.toString(tg, "phones");

        // Load the checked version
        String validated_resource_name = "arctic_a0001.xlab";
        InputStream validated = this.getClass().getResourceAsStream(validated_resource_name);
        String xlab_validated = new Scanner(validated, "UTF-8").useDelimiter("\\A").next();

        Assert.assertEquals(xlab_pred, xlab_validated);
    }


    @Test
    public void testImportXWaveLabel() throws TextGridIOException, IOException {
        String input_resource_name = "arctic_a0001.xlab";
        InputStream input = this.getClass().getResourceAsStream(input_resource_name);
        String string_input = new Scanner(input, "UTF-8").useDelimiter("\\A").next();


        XWaveLabelSerializer xwave_ser = new XWaveLabelSerializer();
	TextGrid tg_predicted= xwave_ser.fromString(string_input);

        // Load the checked version
        String validated_resource_name = "arctic_a0001.TextGrid";
        InputStream validated = this.getClass().getResourceAsStream(validated_resource_name);
        String string_validated = new Scanner(validated, "UTF-8").useDelimiter("\\A").next();

        // Render the textgrid
        TextGridSerializer tgs = new TextGridSerializer();
        TextGrid tg_validated = tgs.fromString(string_validated);


        Assert.assertEquals(tg_predicted, tg_validated);
    }
}


/* XWaveLabelSerializerTest.java ends here */
