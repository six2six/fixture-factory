package br.com.six2six.fixturefactory.function;

import org.hibernate.Session;

public interface AtomicFunction extends Function {

	<T> T generateValue();
	
	<T> T generateValue(Session session);

}
