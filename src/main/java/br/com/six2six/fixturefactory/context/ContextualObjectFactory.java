package br.com.six2six.fixturefactory.context;

import br.com.six2six.fixturefactory.ObjectFactory;
import br.com.six2six.fixturefactory.TemplateHolder;

public class ContextualObjectFactory extends ObjectFactory {

    private Processor processor;

    public ContextualObjectFactory(TemplateHolder templateHolder, Processor processor) {
        super(templateHolder);
        this.processor = processor;
    }

    public <T> T gimme(String label) {
        T result = super.gimme(label);
        processor.execute(result);
        
        return result;
    }
}
