package edu.nju.ws.seval.utils;

public class FourTuple<A,B,C,D> extends ThreeTuple<A,B,C> {
	private D fourth;
	public FourTuple(A a, B b, C c,D d){
		super(a,b,c);
		this.fourth = d;
	}
	
	public D getFourth(){
		return this.fourth;
	}
	
	public void setFourth(D d){
		this.fourth =d;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fourth == null) ? 0 : fourth.hashCode());
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
		
		FourTuple fourTuple = (FourTuple) obj;
		if (this.getFirst() == null) {
			if (fourTuple.getFirst() != null)
				return false;
		} else if (!this.getFirst().equals(fourTuple.getFirst()))
			return false;
		if (this.getSecond() == null) {
			if (fourTuple.getSecond() != null)
				return false;
		} else if (!this.getSecond().equals(fourTuple.getSecond()))
			return false;
		if (this.getThird() == null) {
			if (fourTuple.getThird() != null)
				return false;
		} else if (!this.getThird().equals(fourTuple.getThird()))
			return false;
		if (this.fourth == null) {
			if (fourTuple.fourth != null)
				return false;
		} else if (!this.fourth.equals(fourTuple.fourth))
			return false;
		
		return true;
	}
	
}
