/**
 * This file was created on 2007/06/16, 16:07:27, by nitao. It is part of
 * br.org.archimedes.text.tests on the br.org.archimedes.text.tests project.
 */

package br.org.archimedes.intersector.linetext;

import org.apache.batik.svggen.font.Font;
import org.apache.batik.svggen.font.Glyph;
import org.apache.batik.svggen.font.table.GlyphDescription;

/**
 * Belongs to package br.org.archimedes.text.tests.
 * 
 * @author nitao
 */
public class MockFont extends Font {

    private int fontAdvanceWidth;


    public MockFont (String path, int fontAdvanceWidth) {

        this.fontAdvanceWidth = fontAdvanceWidth;
        this.read(path);
    }

    @Override
    public Glyph getGlyph (int i) {

        return new MockGlyph(new MockGlyphDescription(), (short) 0,
                fontAdvanceWidth);
    }


    private class MockGlyphDescription implements GlyphDescription {

        public int getContourCount () {

            return 0;
        }

        public int getEndPtOfContours (int i) {

            return 0;
        }

        public byte getFlags (int i) {

            return 0;
        }

        public int getPointCount () {

            return 0;
        }

        public short getXCoordinate (int i) {

            return 0;
        }

        public short getXMaximum () {

            return 0;
        }

        public short getXMinimum () {

            return 0;
        }

        public short getYCoordinate (int i) {

            return 0;
        }

        public short getYMaximum () {

            return 0;
        }

        public short getYMinimum () {

            return 0;
        }

        public boolean isComposite () {

            return false;
        }

    }

    private class MockGlyph extends Glyph {

        public MockGlyph (GlyphDescription gd, short lsb, int advance) {

            super(gd, lsb, advance);
        }
    }
}
