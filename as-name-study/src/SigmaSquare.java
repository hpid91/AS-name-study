

public class SigmaSquare implements VariationParameter {

	private static final long serialVersionUID = 394845512055031376L;
	private double sigmaSquare;
	
	public SigmaSquare(double sigmaSquare) {
		this.sigmaSquare = sigmaSquare;
	}

	public double getStandardDeviation(long meanrate) {
		return Math.sqrt(sigmaSquare);
	}
	
	public double getss(){
		return sigmaSquare;
	}
	
	@Override
	public SigmaSquare clone() {
		SigmaSquare obj = null;
		try {
			obj = (SigmaSquare) super.clone();
		} catch (CloneNotSupportedException e) {
			// Ceci ne devrait jamais arriver (on implémente Cloneable)
			throw new RuntimeException(e);
		}
		return obj;
	}
	
	@Deprecated
	public VariationParameter copy() {
		return clone();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof VariationParameter){
			SigmaSquare castedO= (SigmaSquare)obj;
			return sigmaSquare==castedO.sigmaSquare;
		}else return false;
	}
	
	@Override
	public int hashCode() {
		long a = Double.doubleToLongBits(sigmaSquare);
		return (int)(a^(a>>>32));
	}

	@Override
	public String toString() {
		return "[" + this.getClass().getName() + ": (Sg=" +Math.sqrt(sigmaSquare)+ ")]";
	}

	public int compareTo(VariationParameter o) {
		double ssOther;
		if(o==null)
			return 1;
		try{
			ssOther=((SigmaSquare)o).sigmaSquare;
		} catch (ClassCastException cce){
			ssOther=(o).getStandardDeviation(0);
			ssOther*=ssOther;
		}
		return (new Double(this.sigmaSquare)).compareTo(new Double(ssOther));
	}
}
