package br.com.six2six.fixturefactory.function;

import org.hibernate.Session;


public interface RelationFunction extends Function {

	<T> T generateValue(Session session);
	
	<T> T generateValue(Object owner);
	
	<T> T generateValue(Object owner, Session session);
	
}
