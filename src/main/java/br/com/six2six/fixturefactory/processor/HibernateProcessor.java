package br.com.six2six.fixturefactory.processor;

import javax.persistence.Embeddable;

import org.hibernate.Session;

public class HibernateProcessor implements Processor {

    private Session session;
    
    public HibernateProcessor(Session session) {
        this.session = session;
    }
    
    @Override
    public void execute(Object result) {
        if(result.getClass().isAnnotationPresent(Embeddable.class)) return;
    	session.save(result);
    }
}
