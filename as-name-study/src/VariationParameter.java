

import java.io.Serializable;

public interface VariationParameter extends Comparable<VariationParameter> , Cloneable, Serializable {
	
	public double getStandardDeviation(long meanrate);
	public int hashCode();
	public boolean equals(Object obj);
	public VariationParameter clone();
	@Deprecated
	public VariationParameter copy();
	public String toString();
}
