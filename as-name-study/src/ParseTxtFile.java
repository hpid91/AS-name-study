import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class ParseTxtFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader bufferReader; // To read the ases.txt file
		String line; // Current read line
		PrintWriter writer; // To write in a new file

		HashMap<String, AS> ases = new HashMap<String, AS>(50000, .75f); // To
																			// store
																			// the
																			// ASes

		try {
			// Open the buffer to read ases.txt
			bufferReader = new BufferedReader(new FileReader("ases.txt"));
			/**
			 * READ the lines of ases.txt
			 */
			while ((line = bufferReader.readLine()) != null) {
				String id = "";
				String name = "";
				String desc = "";

				/**
				 * Parse line
				 */
				StringTokenizer st = new StringTokenizer(line, "#");
				if (st.hasMoreElements())
					id = st.nextToken();
				if (st.hasMoreElements())
					name = st.nextToken();
				if (st.hasMoreElements())
					desc = st.nextToken();

				/**
				 * CREATE an AS and add to the Map of ASes
				 */

				AS as = new AS(id, name, desc, "");
				ases.put(id, as);

			}

			// Close the reader
			bufferReader.close();

			/**
			 * GET STATISTICS
			 */
			int size = ases.size();
			int univ = countUniversity(ases);
			int nat = countNational(ases);
			int telco = countTelecom(ases);
			int ent = countEnterprise(ases);

			double avgTime = 0;
			for (int i = 0; i < 50; i++) {
				long time = System.currentTimeMillis();
				showLexic(ases);
				time = System.currentTimeMillis() - time;
				avgTime += time;
			}
			avgTime /= 50;
			System.out
					.println("---------------------------- Runtime --------------------------");
			System.out.println("Time= " + avgTime + " ms");
			System.out
					.println("---------------------------- ASes --------------------------");
			System.out.println("Total=" + size);
			System.out.println("Universities=" + univ + " ("
					+ (((double) univ / size) * 100) + " %)");
			System.out.println("National=" + nat + " ("
					+ (((double) nat / size) * 100) + " %)");
			System.out.println("Telecom=" + telco + " ("
					+ (((double) telco / size) * 100) + " %)");
			System.out.println("Enterprise=" + ent + " ("
					+ (((double) ent / size) * 100) + " %)");

			/**
			 * WRITE STATISTICS in asesStat.txt
			 */
			/*
			 * writer = new PrintWriter(new File ("asesStat.txt"));
			 * writer.println
			 * ("---------------------------- ASes --------------------------");
			 * writer.println("Total=" + size); writer.println("Universities=" +
			 * univ + " (" + (((double) univ / size) * 100) + " %)");
			 * writer.println("National=" + nat + " (" + (((double) nat / size)
			 * * 100) + " %)"); writer.println("Telecom=" + telco + " (" +
			 * (((double) telco / size) * 100) + " %)");
			 * writer.println("Enterprise=" + ent + " (" + (((double) ent /
			 * size) * 100) + " %)"); writer.close();
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void showLexic(Map<String, AS> ases) {
		Map<String, Integer> lexic = sortHashMap(parseDesc(ases));

		double avg = 0;
		double stdDev = 0;
		for (Integer temp2 : lexic.values()) {
			avg += temp2;
			stdDev += (temp2 * temp2);
		}
		/*
		 * int size = lexic.size(); avg = avg / size; stdDev = (stdDev / size) -
		 * (avg * avg); stdDev = Math.sqrt(stdDev); for (Entry<String, Integer>
		 * entry: lexic.entrySet()) { System.out.println(entry.getKey() + " : "
		 * + entry.getValue()); } System.out.println("#occurences : " + size);
		 * System.out.println("Average occurences : " + avg);
		 * System.out.println("Standard deviation : " + stdDev);
		 */
	}

	public static Map<String, Integer> parseDesc(Map<String, AS> ases) {
		Map<String, Integer> lexic = new HashMap<String, Integer>();
		for (Entry<String, AS> entry : ases.entrySet()) {
			String desc = entry.getValue().getDescription();
			StringTokenizer st = new StringTokenizer(desc, " ");
			while (st.hasMoreTokens()) {
				String temp = st.nextToken();
				temp = temp.replace(" ", "");
				temp = temp.replace(".", "");
				temp = temp.replace(",", "");
				if (temp.length() > 1 && !temp.equals("for")
						&& !temp.equals("and") && !temp.equals("the")
						&& !temp.equals("of")) {
					if (lexic.containsKey(temp)) {
						lexic.put(temp, lexic.get(temp) + 1);
					} else
						lexic.put(temp, 1);
				}
			}
		}
		return lexic;
	}

	private static Map<String, Integer> sortHashMap(Map<String, Integer> input) {
		Map<String, Integer> tempMap = new HashMap<String, Integer>();
		for (String wsState : input.keySet()) {
			tempMap.put(wsState, input.get(wsState));
		}

		List<String> mapKeys = new ArrayList<String>(tempMap.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(tempMap.values());
		HashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		TreeSet<Integer> sortedSet = new TreeSet<Integer>(mapValues);
		Object[] sortedArray = sortedSet.toArray();
		int size = sortedArray.length;
		for (int i = 0; i < size; i++) {
			sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])),
					(Integer) sortedArray[i]);
		}
		return sortedMap;
	}

	public static int countUniversity(Map<String, AS> ases) {
		int count = 0;
		for (Entry<String, AS> entry : ases.entrySet()) {
			if (entry.getValue().getDescription().indexOf("universit") != -1
					|| entry.getValue().getDescription().indexOf("research") != -1
					|| entry.getValue().getDescription().indexOf("academic") != -1
					|| entry.getValue().getDescription().indexOf("lab") != -1
					|| entry.getValue().getDescription().indexOf("inria") != -1
					|| entry.getValue().getDescription().indexOf("ecole") != -1
					|| entry.getValue().getDescription().indexOf("school") != -1
					|| entry.getValue().getDescription().indexOf("educ") != -1
					|| entry.getValue().getDescription().indexOf("college") != -1
					|| entry.getValue().getDescription().indexOf("renater") != -1
					|| entry.getValue().getDescription().indexOf("institut") != -1) {

				entry.getValue().setType("UNIVERSITY");
				count++;

			}
		}
		return count;
	}

	public static int countTelecom(Map<String, AS> ases) {
		int count = 0;
		for (Entry<String, AS> entry : ases.entrySet()) {
			if (entry.getValue().getDescription().indexOf("telecom") != -1
					|| entry.getValue().getDescription().indexOf("orange") != -1
					|| entry.getValue().getDescription().indexOf("at&t") != -1
					|| entry.getValue().getDescription().indexOf("telefon") != -1
					|| entry.getValue().getDescription().indexOf("sprint") != -1
					|| entry.getValue().getDescription().indexOf("level3") != -1
					|| entry.getValue().getDescription()
							.indexOf("communication") != -1
					|| entry.getValue().getDescription().indexOf("network") != -1
					|| entry.getValue().getDescription().indexOf("telekom") != -1
					|| entry.getValue().getDescription().indexOf("telefon") != -1
					|| entry.getValue().getDescription().indexOf("verizon") != -1) {

				entry.getValue().setType("TELECOM");
				count++;

			}
		}
		return count;
	}

	public static int countNational(Map<String, AS> ases) {
		int count = 0;
		for (Entry<String, AS> entry : ases.entrySet()) {
			if (entry.getValue().getDescription().indexOf("nation") != -1
					|| entry.getValue().getDescription().indexOf("regional") != -1
					|| entry.getValue().getDescription().indexOf("federal") != -1
					|| entry.getValue().getDescription().indexOf("state") != -1
					|| entry.getValue().getDescription().indexOf("commission") != -1
					|| entry.getValue().getDescription()
							.indexOf("administration") != -1
					|| entry.getValue().getDescription().indexOf("agency") != -1
					|| entry.getValue().getDescription().indexOf("ministry") != -1
					|| entry.getValue().getDescription().indexOf("agentia") != -1
					|| entry.getValue().getDescription().indexOf("dod") != -1
					|| entry.getValue().getDescription().indexOf("usaisc") != -1
					|| entry.getValue().getDescription().indexOf(" nato ") != -1) {

				entry.getValue().setType("NATIONAL");
				count++;

			}
		}
		return count;
	}

	public static int countEnterprise(Map<String, AS> ases) {
		int count = 0;
		for (Entry<String, AS> entry : ases.entrySet()) {
			if (entry.getValue().getDescription().indexOf("enterprise") != -1
					|| entry.getValue().getDescription().indexOf("gmbh") != -1
					|| entry.getValue().getDescription().indexOf("sa") != -1
					|| entry.getValue().getDescription().indexOf("corporation") != -1
					|| entry.getValue().getDescription().indexOf("bank") != -1
					|| entry.getValue().getDescription().indexOf("llc") != -1
					|| entry.getValue().getDescription().indexOf("ltd") != -1
					|| entry.getValue().getDescription().indexOf("s.r.l") != -1
					|| entry.getValue().getDescription().indexOf("srl") != -1
					|| entry.getValue().getDescription().indexOf("ojsc") != -1
					|| entry.getValue().getDescription().indexOf("cjsc") != -1
					|| entry.getValue().getDescription().indexOf("pty") != -1
					|| entry.getValue().getDescription().indexOf("d.o.o.") != -1
					|| entry.getValue().getDescription().indexOf("inc.") != -1) {

				entry.getValue().setType("ENTERPRISE");
				count++;

			}
		}
		return count;
	}
}
