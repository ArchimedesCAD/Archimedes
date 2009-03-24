package br.org.archimedes.trims;

import java.util.Map;

import br.org.archimedes.model.Element;
import br.org.archimedes.trims.interfaces.Trimmer;
import br.org.archimedes.trims.rcp.TrimmerEPLoader;

public class MockTrimmerEPLoader extends TrimmerEPLoader {
	
	private final Map<Class<? extends Element>, Trimmer> trimmerOptions;

	public MockTrimmerEPLoader(Map<Class<? extends Element>, Trimmer> trimmerOptions) {
		this.trimmerOptions = trimmerOptions;
	}
	
	@Override
	public Trimmer get(Class<? extends Element> elementClass) {
		 return trimmerOptions.get(elementClass);
	}

}
