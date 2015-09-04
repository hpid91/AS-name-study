
public interface PureTrafficContainersBundle extends TrafficContainersBundle, Cloneable{
	/**
	 * Returns the <code>TrafficContainer</code> equivalent Flow.
	 * @return the <code>FlowCharacteristics</code> with the parameters corresponding to the equivalent Flow to every Flows hold in the TrafficDemandBundle.
	 */
	public abstract FlowCharacteristics getAggregatedFlow();

	/**
	 * Returns the TrafficDemandsBundle mean rate. Expressed in bits per second.
	 * It is the mathematical sum of every hold <code>TrafficDemand</code> mean rates.
	 * @return the TrafficDemandsBundle mean rate.
	 */
	public abstract long getTotalMeanRate();

	/**
	 * Returns the <code>TrafficContainer</code> number of flows. This term mitigates different types of flows.
	 * If the object is an instance of <code>TrafficDemandsBundle</code>It is the mathematical sum of every hold <code>TrafficDemand</code> number of flows.
	 * @return the <code>TrafficContainer</code> number of flows.
	 */
	public abstract int getNumberOfFlows();
	
//	public abstract PureTrafficContainersBundle copy();
	
	public abstract String fastToString();

	/* Générer la valeur du pic de trafic par circuit */
	public abstract long getPeakRate();

}
