import java.util.Iterator;

import fab.circuits.Circuit;

public class Circuit implements Comparable<Circuit>{

	// Attributs intrinsèques
	private PureTrafficContainersBundle nestedDemands;
	public PureTrafficContainersBundle getNestedDemands() {
		return nestedDemands;
	}

	// Attributs de mémoire cache
	private FlowCharacteristics _lastUsedFlowForIsFull;
	private FlowCharacteristics _lastUsedFlowForFillRatio;
	private boolean _lastIsFullState;
	private double _lastFillRatio;

	@SuppressWarnings("unused")
	private Circuit() {/*prevents default constructor being public*/}

	public Circuit(PureTrafficContainersBundle nested){
		nestedDemands = nested;
	}

/*	@SuppressWarnings("unused")
	private Circuit(CircuitsBundle father){
		this(father,TCBfactory.getTDC());
	} */

	public double getFillingRatio() {
		if (this.nestedDemands==null || this.nestedDemands.getTotalMeanRate()==0 )
			return 0;
		else {
			FlowCharacteristics aggFlow=getAggFlow();

			if (aggFlow ==_lastUsedFlowForFillRatio) // which is more restrictive than .equals but is used intently
				return _lastFillRatio;
			else {
				_lastUsedFlowForFillRatio=aggFlow;
				if (isFull())
					return _lastFillRatio=1;
				else
					_lastFillRatio=1/EqBwFactory.getEqBwCalculator().maxFlowNumber(aggFlow,this.getCharacteristics());
			}
		}
	}

	public FlowCharacteristics getAggFlow(){	//old : private
		return nestedDemands.getAggregatedFlow();
	}
	
	public boolean isFull(){
		FlowCharacteristics aggFlow = this.getAggFlow();
		if (aggFlow == _lastUsedFlowForIsFull) // which is more restrictive than .equals but is used intently
			return _lastIsFullState;
		else {
			_lastUsedFlowForIsFull = aggFlow;
			if (aggFlow.getVariationP()==null)
				return _lastIsFullState = this.getCharacteristics().getWcCapacity()<=aggFlow.getMeanRate();
			EqBw_Calculator calc = EqBwFactory.getEqBwCalculator();
			for (Iterator<FlowCharacteristics> isf = FlowCharacteristics.getSmallFlows().iterator(); isf.hasNext();){
				if (calc.canFitIntoCircuit(aggFlow, isf.next(), this.getCharacteristics()))
					return _lastIsFullState = false;
			}
			return _lastIsFullState = true;
		}
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		else
			try {
				return nestedDemands.equals(((Circuit)obj).nestedDemands);
			} catch (ClassCastException e) {
				return false;
			}		
	}

	public int compareTo(Circuit arg0) {
		return new Double(this.getFillingRatio()).compareTo(arg0.getFillingRatio());
	}
}
