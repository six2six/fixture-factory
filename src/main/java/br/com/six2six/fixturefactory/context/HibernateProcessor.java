package br.com.six2six.fixturefactory.context;

import org.hibernate.Session;

public class HibernateProcessor implements Processor {

    private Session session;
    
    public HibernateProcessor(Session session) {
        this.session = session;
    }
    
    @Override
    public void execute(Object result) {
        session.save(result);
    }
}
