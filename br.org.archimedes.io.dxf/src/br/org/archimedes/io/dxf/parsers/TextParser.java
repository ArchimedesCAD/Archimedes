package br.org.archimedes.io.dxf.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFText;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.text.Text;

public class TextParser extends ElementParser{
	
	@Override
	public Collection<Element> parse(DXFLayer layer) throws NullArgumentException, InvalidArgumentException {
		
		Collection<Element> archimedesTexts = new ArrayList<Element>();
		List<DXFText> dxfTexts = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_TEXT);
		
		if(dxfTexts != null) {
			for (DXFText dxfText : dxfTexts) {
				String contentText = dxfText.getText();
		  		org.kabeja.dxf.helpers.Point insertPoint = dxfText.getInsertPoint();
		  		double size = dxfText.getHeight();
		  			
		  		Point p1 = new Point(insertPoint.getX(), insertPoint.getY());
		  		
				Text text = new Text(contentText,p1,size);
							
				archimedesTexts.add(text);
			}
		}
		return archimedesTexts;
	}		
}
