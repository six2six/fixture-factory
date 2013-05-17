package br.com.six2six.fixturefactory.function;

import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.Options;
import com.mdimension.jchronic.utils.Span;

public class ChronicFunction implements AtomicFunction {

    private String dateText;
    
    private Options options;
    
    public ChronicFunction(String text) {
        this.dateText = text;
        this.options = new Options();
    }
    
    public ChronicFunction(String text, Options options) {
        this.dateText = text;
        this.options = options;
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T generateValue() {
        Span span = Chronic.parse(dateText, options);
        return (T) (span != null ? span.getBeginCalendar() : null);
    }

}
