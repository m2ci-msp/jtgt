package org.m2ci.msp.jtgt.io;

import java.io.InputStream;
import java.util.Scanner;
import org.m2ci.msp.jtgt.TextGrid;
import org.m2ci.msp.jtgt.io.TextGridSerializer;
import org.m2ci.msp.jtgt.io.TextGridIOException;

import org.testng.Assert;
import org.testng.annotations.*;

/**
 *
 *
 * @author <a href="mailto:slemaguer@coli.uni-saarland.de">Sébastien Le Maguer</a>
 */
public class TextGridSerializerTest
{
    @Test
    public void testLoadingTextGrid() throws TextGridIOException {
	String testResourceName = "tg1.TextGrid";
        InputStream input = this.getClass().getResourceAsStream(testResourceName);
	String string_tg = new Scanner(input,"UTF-8").useDelimiter("\\A").next();
        System.out.println(string_tg);

	TextGridSerializer tgs = new TextGridSerializer();
	TextGrid tg = tgs.fromString(string_tg);
    }


}


/* TextGridSerializerTest.java ends here */
