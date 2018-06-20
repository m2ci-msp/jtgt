package org.m2ci.msp.jtgt;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.m2ci.msp.jtgt.annotation.IntervalAnnotation;
import org.m2ci.msp.jtgt.tier.IntervalTier;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TextGridTest {

    @Test
    public void testToString() throws IOException {
        // create TextGrid
        IntervalAnnotation foo = new IntervalAnnotation(0, 0.5, "foo");
        IntervalAnnotation bar = new IntervalAnnotation(0.5, 1, "bar");
        IntervalTier tier = new IntervalTier("foobar");
        tier.addAnnotation(foo);
        tier.addAnnotation(bar);
        tier.setStart(foo.getStart());
        tier.setEnd(bar.getEnd());
        TextGrid tg = new TextGrid();
        tg.addTier(tier);
        tg.setStart(tier.getStart());
        tg.setEnd(tier.getEnd());
        // compare as JSON
        String actualStr = tg.toString();
        String expectedStr = IOUtils.resourceToString("/org/m2ci/msp/jtgt/foobar.json", StandardCharsets.UTF_8);
        Map actual = new JSONObject(actualStr).toMap();
        Map expected = new JSONObject(expectedStr).toMap();
        Assert.assertEquals(actual, expected);
    }
}
