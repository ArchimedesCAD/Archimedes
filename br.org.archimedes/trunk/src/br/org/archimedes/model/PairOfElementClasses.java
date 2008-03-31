package br.org.archimedes.model;

public class PairOfElementClasses {

	private Class<? extends Element> element;
	
	private Class<? extends Element> otherElement;
	
	public PairOfElementClasses(Class<? extends Element> element, Class<? extends Element> otherElement) {
		this.element = element;
		this.otherElement = otherElement;
	}

	public Class<? extends Element> getElement() {
		return element;
	}

	public Class<? extends Element> getOtherElement() {
		return otherElement;
	}
}
