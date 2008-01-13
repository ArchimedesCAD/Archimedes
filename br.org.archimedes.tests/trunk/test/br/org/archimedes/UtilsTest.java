package br.org.archimedes;


import org.junit.Assert;
import org.junit.Test;

/*
 * Created on 06/04/2006
 */

/**
 * Belongs to package .
 * 
 * @author marivb
 */
public class UtilsTest {

    @Test
    public void testIsPoint () {

        Assert.assertFalse("Should be false for null argument", Utils.isPoint(null));
        Assert.assertFalse("Should be false for \"\"", Utils.isPoint(""));
        Assert.assertFalse("Should be false for \"This is not a point.\"", Utils
                .isPoint("This is not a point."));
        Assert.assertTrue("Should be true for \"0;0\"", Utils.isPoint("0;0"));
        Assert.assertTrue("Should be true for \"1.0;1.1\"", Utils.isPoint("1.0;1.1"));
        Assert.assertTrue("Should be true for \"1,0;1,0\"", Utils.isPoint("1,0;1,0"));
        Assert.assertTrue("Should be true for \"1,0;1.0\"", Utils.isPoint("1,0;1.0"));
        Assert.assertTrue("Should be true for \"0 ; 0\"", Utils.isPoint("0 ; 0"));
        Assert.assertTrue("Should be true for \"  1.0 ;1.1\"", Utils
                .isPoint("  1.0 ;1.1"));
        Assert.assertTrue("Should be true for \"0 ;0  \"", Utils.isPoint("0 ;0  "));
        Assert.assertTrue("Should be true for \"  1.0 ; 1.1  \"", Utils
                .isPoint("  1.0 ; 1.1  "));
    }

    @Test
    public void testIsDouble () {

        Assert.assertFalse("The string null should return false", Utils.isDouble(null));

        Assert.assertTrue("Should be true to \"0\"", Utils.isDouble("0"));
        Assert.assertTrue("Should be true to \"-0,0\"", Utils.isDouble("-0,0"));
        Assert.assertTrue("Should be true to \"+0.0\"", Utils.isDouble("+0.0"));
        Assert.assertTrue("Should be true to \"-1,0\"", Utils.isDouble("-1,0"));
        Assert.assertTrue("Should be true to \"1.0\"", Utils.isDouble("1.0"));
        Assert.assertTrue("Should be true to \"+1,213\"", Utils.isDouble("+1,213"));

        Assert.assertFalse("Should be false to \"mamae\"", Utils.isDouble("mamae"));
        Assert.assertFalse("Should be false to \"5.0;6.1\"", Utils.isDouble("5.0;6.1"));
        Assert.assertFalse("Should be false to \"\"", Utils.isDouble(""));
    }
}
