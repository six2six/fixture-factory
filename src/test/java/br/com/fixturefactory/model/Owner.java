package br.com.fixturefactory.model;

public class Owner {

	public String id;
	
	private Inner inner;
	
	public Inner getInner() {
		return inner;
	}

	public void setInner(Inner inner) {
		this.inner = inner;
	}

	public class Inner {
		private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
		public Owner getOwner() {
			return Owner.this;
		}
	}
}
