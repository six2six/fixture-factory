package br.com.six2six.fixturefactory.model;

import java.io.Serializable;

public class RouteId implements Serializable {

	private static final long serialVersionUID = 4124693687758213917L;
	
	private Long value;
	private Long seq;
	
	public RouteId(Long value, Long seq) {
		this.value = value;
		this.seq = seq;
	}

    public Long getValue() {
		return value;
	}
    
    public Long getSeq() {
        return seq;
    }
}
