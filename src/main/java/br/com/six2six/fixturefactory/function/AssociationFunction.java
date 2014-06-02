package br.com.six2six.fixturefactory.function;

import br.com.six2six.fixturefactory.util.Chainable;

public interface AssociationFunction extends AtomicFunction, RelationFunction, Chainable {

	Function targetAttribute(String targetAttribute);
	
}
