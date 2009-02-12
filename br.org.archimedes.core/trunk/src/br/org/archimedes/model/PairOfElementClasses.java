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

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((element == null) ? 0 : element.hashCode());
		result = result + ((otherElement == null) ? 0 : otherElement.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PairOfElementClasses other = (PairOfElementClasses) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element) && !element.equals(other.otherElement))
			return false;
		if (otherElement == null) {
			if (other.otherElement != null)
				return false;
		} else if (!otherElement.equals(other.otherElement) && !otherElement.equals(other.element))
			return false;
		if (element.equals(otherElement) && !other.element.equals(other.otherElement))
			return false;
		return true;
	}

	
}
