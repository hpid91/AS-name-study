

import java.util.Iterator;

/**
 * <p>This interface defines a general framework for objects containing traffic.<br>
 * Actually there are two sizes/granularity of objects containing traffic. Elementary ones, which classes implement <code>TrafficContainer</code> Interface.
 * Classes implementing <code>TrafficContainersBundle</code> are bundles of elementary traffic containers.<br><br>
 * As this Interface defines that TrafficContainer can be added/removed to TrafficContainersBundle, there are actually compatibility rules that are bringing relations between diferent implementations of each of these Interfaces. 
 * <p align = "center"> <Table border = 1 width = 80%>
 * <tr><td width = "50%" align = "center"><b>TrafficContainersBundle</b></td><td width = "50%" align = "center"><b>TrafficContainer</b></td></tr>
 * <tr><td width = "50%" align = "center">HugeFlowsBundle</td><td width = "50%" align = "center">FlowCharacteristics</td></tr>
 * <tr><td width = "50%" align = "center">TrafficDemandsBundle</td><td width = "50%" align = "center">TrafficDemand</td></tr>
 * <tr><td width = "50%" align = "center">fab.circuits.CircuitsBundle</td><td width = "50%" align = "center">fab.circuits.Circuit</td></tr>
 * <tr><td width = "50%" align = "center">fab.circuits.NestableCircuitsBundle</td><td width = "50%" align = "center">fab.circuits.Circuit</td></tr>
 * <tr><td width = "50%" align = "center">fab.traffic.matrix.NestedTrafficContainersBundle</td><td width = "50%" align = "center">Any</td></tr>
 * <tr><td width = "50%" align = "center">UndefinedTraffic</td><td width = "50%" align = "center">None</td></tr>
 * </Table></p></p>
 * <p><Table width="450"><tr><td width="75"><b>Project:</b></td><td>Forwarding Adjacency Builder - Core</td><tr>
 * <tr><td width="75"><b>Copyright:</b></td><td>Copyright (c) 2002-2010</td><tr>
 * <tr><td width="75"><b>Company:</b></td><td>Alcatel-Lucent France - Bell Labs - R&I</td><tr></Table>
 * @author Pierre Peloso (pierre.peloso@alcatel-lucent.fr)
 * @see TrafficContainer
 */
public interface TrafficContainersBundle {

	 /**
	  * This fields is just used for a signaling errors. Its text clarifies which error.
	  */
	 public final static String TYPE_ERROR_MSG = "Attempt of mixing incompatible kinds of TrafficContainersBundle";

	 /**
	  * Used to return all the traffic hold in this instance. Actually, this method generally returns this. In some cases the TrafficContainersBundle itself is containing other TrafficContainersBundle.
	  * @return All the traffic held in this object 
	  * @see PureTrafficContainersBundle
	  */
	 public abstract PureTrafficContainersBundle getAllTraffic();

	 /**
	  * Used to know how many TrafficContainers are hold in this instance.
	  * @return the number of TrafficContainers hold.
	  */
	 public abstract int size();
 }