package br.org.archimedes.extend;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.extend.interfaces.Extender;
import br.org.archimedes.extend.rcp.ExtenderEPLoader;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class ExtendManager implements
		br.org.archimedes.interfaces.ExtendManager {
	
	private static final Extender NULL_EXTENDER = new NullExtender();
	private ExtenderEPLoader loader;
	
	public ExtendManager() {
		loader = new ExtenderEPLoader();
	}
	
	public ExtendManager (ExtenderEPLoader loader) {
    	this.loader = loader;
    }
	
	public void extend(Element element,
			Collection<Element> references, Point click)
			throws NullArgumentException {
		getExtenderFor(element).extend(element, references, click);
	}
	
	private Extender getExtenderFor(Element element) {
		Class<? extends Element> elementClass = element.getClass();
        Extender extender = loader.get(elementClass);
        return extender == null ? NULL_EXTENDER : extender;
	}

}
