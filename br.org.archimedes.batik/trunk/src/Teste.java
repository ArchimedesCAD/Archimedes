import org.apache.batik.svggen.font.Font;
import org.apache.batik.svggen.font.Glyph;
import org.apache.batik.svggen.font.Point;

public class Teste {
	public static void main(String[] args) {
		Font f = Font.create("fonts/Vera.ttf");

		for (int j = 'a'; j <= 'z' /*f.getNumGlyphs();*/; j++) {

			Glyph g = f.getGlyph(j);
			if (g != null) {
				System.out.println("Glyph: " + (char)j);				
				for (int i = 0; i < g.getPointCount(); i++) {
					Point p = g.getPoint(i);
					System.out.print("\t(" + p.x + "," + p.y + ")");
				}
				System.out.println();
			}
		}
	}
}
