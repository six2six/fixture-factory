package br.com.six2six.fixturefactory.function;


public interface AssociationFunction extends AtomicFunction, RelationFunction, Chainable {

	Function targetAttribute(String targetAttribute);
	
}
