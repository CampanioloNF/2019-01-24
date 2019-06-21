package it.polito.tdp.extflightdelays.model;

public class StateAndNumber implements Comparable<StateAndNumber>{

	private String source;
	private String dest;
	private int numV;

	public StateAndNumber(String source, String dest, int numV) {
		super();
		this.source = source;
		this.dest = dest;
		this.numV = numV;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dest == null) ? 0 : dest.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
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
		StateAndNumber other = (StateAndNumber) obj;
		if (dest == null) {
			if (other.dest != null)
				return false;
		} else if (!dest.equals(other.dest))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}

	public String getSource() {
		return source;
	}

	public String getDest() {
		return dest;
	}

	public int getNumV() {
		return numV;
	}

	@Override
	public int compareTo(StateAndNumber o) {
		// TODO Auto-generated method stub
		return o.numV-this.numV;
	}
	
	
}
