package br.org.archimedes.io.dxf;

import java.io.IOException;
import java.io.OutputStream;

import org.kabeja.dxf.DXFCircle;
import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFLine;
import org.kabeja.dxf.DXFPolyline;
import org.kabeja.dxf.DXFVertex;
import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.helpers.Point;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.ParserBuilder;

import br.org.archimedes.interfaces.Exporter;
import br.org.archimedes.model.Drawing;

public class DXFExporter implements Exporter {

	public DXFExporter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exportDrawing(Drawing drawing, OutputStream output)
			throws IOException {
		// TODO Auto-generated method stub

	}

}
