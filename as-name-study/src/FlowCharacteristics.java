

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.w3c.dom.Element;

/**
 * <p>Title: Forwarding Adjacency Builder</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002-2005</p>
 * <p>Company: ALCATEL CIT - CTO / R&I</p>
 * @author Martin Vigoureux
 * @version 2.0
 */

public class FlowCharacteristics implements Comparable<FlowCharacteristics>{
	
	private static HashMap<String,FlowCharacteristics> flowTypesMap=new HashMap<String,FlowCharacteristics>() ;
	private static HashMap<FlowCharacteristics,FlowCharacteristics> _flowTypesRepository = null;//<FlowCharacteristics,FlowCharacteristics>
	private static HashSet<FlowCharacteristics> _smallFlows;
	private long meanRate;
	@SuppressWarnings("unused")
	private String _type;
//	private double _standardDeviation;
	private VariationParameter _variationParameter;

	@SuppressWarnings("unused")
	private FlowCharacteristics() {/*prevents default constructor being public*/}

	/**
	 * Creates a <code>FlowCharacteristics</code> instance by assigning
	 * the specified values to the corresponding attributes.
	 * <br>These attributes are of use when computing the equivalent capacity
	 * based on Guérin formulae.
	 * @param meanRate The mean rate characteristic. Expressed in bits per second.
	 * @param standardDeviation The standard deviation characteristic.
	 */
	public FlowCharacteristics(long meanRate, double standardDeviation) {
		this.meanRate = meanRate;
		setSigma(standardDeviation);
	}

	private void setSigma(double standardDeviation) {
		_variationParameter = new SigmaSquare(standardDeviation*standardDeviation);
	}

	public FlowCharacteristics(long meanRate) {
		this(meanRate,null);
	}

	public FlowCharacteristics(long meanRate, VariationParameter variationParameter) {
		this.meanRate = meanRate;
		_variationParameter = variationParameter;
	}

	public int compareTo(FlowCharacteristics o) {
		//if (!(o instanceof FlowCharacteristics)) throw new ClassCastException("Try to compare a "+o.getClass().getName()+" object to a FlowCharacteristics one.");
		//FlowCharacteristics oFC = (FlowCharacteristics)o;
		Long mr1 = new Long(this.meanRate);
		Long mr2 = new Long(o.meanRate);
		int resMR = mr1.compareTo(mr2);
		int resVP;
		int res;
		if (this._variationParameter==null) {
			if (o._variationParameter==null)
				return resMR;
			else
				resVP = -1;
		} else
			resVP = this._variationParameter.compareTo(o._variationParameter);
		res = resMR*resVP;
		if (res==0) {
			if (resVP==0)
				try {
					if (this._variationParameter.equals(o._variationParameter))
						return resMR;
				} catch (NullPointerException e) {
					return resMR;
				}
			if (resMR==0)
				return resVP;
			return 0;
		} else {
			if (res<0)
				return 0;
			else return resMR;
		}
	}

	/**
	 * Returns the flow mean rate. Expressed in bits per second.
	 * @return the flow mean rate.
	 */
	public long getMeanRate() {
		return meanRate;
	}

	public VariationParameter getVariationP() {
		return _variationParameter;
	}

	/**
	 * Returns the flow standard deviation (a.k.a. sigma).
	 * @return the flow standard deviation.
	 */
	public double getStandardDeviation() {
		if (_variationParameter==null)
			return 0d;
		else
			return _variationParameter.getStandardDeviation(this.meanRate);
	}

	public FlowCharacteristics getAggregatedFlow(){
		return this;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * <br>Two instances of <code>FlowCharacteristics</code> are
	 * equal iff their corresponding attributes are equal.
	 * <br>Overrides <code>java.lang.Object.equals(Object obj)</code>
	 * @param obj The reference object with which to compare.
	 * @return <code>true</code> if this object is the same as the <code>anObject</code> argument; <code>false</code> otherwise.
	 * @see <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/lang/Object.html#equals(java.lang.Object)">Object.equals(Object obj)</a>
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj==this)
			return true;
		
		FlowCharacteristics fc;

		if(obj == null || !(obj instanceof FlowCharacteristics))
			return false;
		fc = (FlowCharacteristics)obj;
		if(this.meanRate == fc.meanRate && (_variationParameter==null?fc._variationParameter==null:_variationParameter.equals(fc._variationParameter))/*
        && this._pRatio == fc._pRatio && this._pScheme.equals(fc._pScheme)*/)
			return true;
		return false;
	}

	/**
	 * Returns a hash code value for the object.
	 * <br>Overrides <code>java.lang.Object.hashCode()</code>
	 * @return a hash code value for this object.
	 * @see <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/lang/Object.html#hashCode()">Object.hashCode()</a>
	 */
	@Override
	public int hashCode() {

		long mr = Double.doubleToLongBits(meanRate);
		if (_variationParameter!=null)
			return (int)(mr^(mr>>>32)) ^ _variationParameter.hashCode();
		else return (int)(mr^(mr>>>32));
	}

	@Override
	public FlowCharacteristics clone() {

		if(_flowTypesRepository.containsKey(this))
			return this;
		else {
			FlowCharacteristics fc = null;
			try {
				fc = (FlowCharacteristics) super.clone();
				fc.meanRate=this.meanRate;
				if(_variationParameter != null)
					fc._variationParameter = _variationParameter.clone();
			} catch (CloneNotSupportedException e) {
				// Ceci ne devrait jamais arriver (on implémente Cloneable)
				throw new RuntimeException(e);
			}
			return fc;
		}
	}
}