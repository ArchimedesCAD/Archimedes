package br.org.archimedes.trims;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.trims.interfaces.Trimmer;

public class TrimManagerTest extends Tester{
	@Test
	public void testeReturnProprioElementoParaUmTrimmerNaoExistente() throws Exception {
		
		MockTrimmerEPLoader trimmerEPLoader = new MockTrimmerEPLoader(Collections.EMPTY_MAP);
		TrimManager manager = new TrimManager(trimmerEPLoader);
		Line line = new Line(1.0, 0.0, -1.0, 0.0);
		
		Collection<Element> collection = manager.getTrimOf(line, Collections.EMPTY_LIST, new Point(0.0, 0.0));
		assertCollectionTheSame(Collections.singleton(line), collection);
		
	}
	
	@Test
	public void testeReturnTrimmerMockadoeChamarTrimparaOTrimmerMockado() throws Exception {
		
/*		Trimmer trimmerMockado = new Trimmer() {

			public Collection<Element> trim(Element element,
					Collection<Element> references, Point click)
					throws NullArgumentException {
				// TODO Auto-generated method stub
				return null;
			}
			
		}
		
		MockTrimmerEPLoader trimmerEPLoader = new MockTrimmerEPLoader(Collections.singletonMap(Line.class, trimmerMockado));
		TrimManager manager = new TrimManager(trimmerEPLoader);
		Line line = new Line(1.0, 0.0, -1.0, 0.0);
*/
	}
}
