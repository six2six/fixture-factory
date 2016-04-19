package br.com.six2six.fixturefactory.transformer;

import com.google.protobuf.ProtocolMessageEnum;

public class ProtoEnumTransformer implements Transformer {

    @Override
    public <T> T transform(Object value, Class<T> type) {
        if(value == null) return null;

        ProtocolMessageEnum protoEnum = (ProtocolMessageEnum) value;

        return (T) (Integer)protoEnum.getNumber();
    }

    @Override
    public boolean accepts(Object value, Class<?> type) {
        return ProtocolMessageEnum.class.isAssignableFrom(value.getClass());
    }

}
