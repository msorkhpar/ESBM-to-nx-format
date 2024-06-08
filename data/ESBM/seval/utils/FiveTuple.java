package edu.nju.ws.seval.utils;

public class FiveTuple<A,B,C,D,E> extends FourTuple<A,B,C,D> {
	private E fifth;
	public FiveTuple(A a, B b, C c,D d,E e){
		super(a,b,c,d);
		this.fifth = e;
	}
	
	public E getFifth(){
		return this.fifth;
	}
	
	public void setFifth(E e){
		this.fifth = e;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fifth == null) ? 0 : fifth.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		FiveTuple fiveTuple = (FiveTuple) obj;
		if (this.getFirst() == null) {
			if (fiveTuple.getFirst() != null)
				return false;
		} else if (!this.getFirst().equals(fiveTuple.getFirst()))
			return false;
		if (this.getSecond() == null) {
			if (fiveTuple.getSecond() != null)
				return false;
		} else if (!this.getSecond().equals(fiveTuple.getSecond()))
			return false;
		if (this.getThird() == null) {
			if (fiveTuple.getThird() != null)
				return false;
		} else if (!this.getThird().equals(fiveTuple.getThird())){
			return false;
		}else if (!this.getFourth().equals(fiveTuple.getFourth())){
			return false;
		}
		if (this.fifth == null) {
			if (fiveTuple.fifth != null)
				return false;
		} else if (!this.fifth.equals(fiveTuple.fifth))
			return false;
		
		return true;
	}
	
}
