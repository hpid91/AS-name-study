import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class ParseTxtFile2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader bufferReader; // To read the ases.txt file
		String line; // Current read line
		PrintWriter writer; // To write in a new file
		
		HashMap<String, AS> ases = new HashMap<String, AS>( 50000, .75f); // To store the ASes

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
				if (st.hasMoreElements())  id = st.nextToken();
				if (st.hasMoreElements()) name = st.nextToken();
				if (st.hasMoreElements()) desc = st.nextToken();
				
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
			for (int i= 0; i < 50; i++) {
				long time = System.currentTimeMillis();
				showLexic(ases);
				time = System.currentTimeMillis() - time;
				avgTime+=time;
			}
			avgTime/=50;
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
			/*writer = new PrintWriter(new File ("asesStat.txt"));
			writer.println("---------------------------- ASes --------------------------");
			writer.println("Total=" + size);
			writer.println("Universities=" + univ + " ("
					+ (((double) univ / size) * 100) + " %)");
			writer.println("National=" + nat + " ("
					+ (((double) nat / size) * 100) + " %)");
			writer.println("Telecom=" + telco + " ("
					+ (((double) telco / size) * 100) + " %)");
			writer.println("Enterprise=" + ent + " ("
					+ (((double) ent / size) * 100) + " %)");
			writer.close();
			*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void showLexic(HashMap<String, AS> ases) {
		HashMap<String, Integer> lexic = sortHashMap(parseDesc(ases));

		double avg = 0;
		double stdDev = 0;
		for (String temp : lexic.keySet()) {
			avg += lexic.get(temp);
			stdDev += (lexic.get(temp) * lexic.get(temp));
			// System.out.println(temp + " : " + lexic.get(temp));
		}
		/*int size = lexic.size();
		avg = avg / size;
		stdDev = (stdDev / size) - (avg * avg);
		stdDev = Math.sqrt(stdDev);
		for (String temp : lexic.keySet()) {
			 System.out.println(temp + " : "
					+ lexic.get(temp));
		}
		System.out.println("#occurences : " + size);
		System.out.println("Average occurences : " + avg);
		System.out.println("Standard deviation : " + stdDev);*/
	}

	public static HashMap<String, Integer> parseDesc(HashMap<String, AS> ases) {
		HashMap<String, Integer> lexic = new HashMap<String, Integer>();
		for (String id : ases.keySet()) {
			String desc = ases.get(id).getDescription();
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
					} else lexic.put(temp, 1);
				}
			}
		}
		return lexic;
	}

	private static HashMap<String, Integer> sortHashMap(
			HashMap<String, Integer> input) {
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

	public static int countUniversity(HashMap<String, AS> ases) {
		int count = 0;
		for (String id : ases.keySet()) {
			if (ases.get(id).getDescription().indexOf("universit") != -1
					|| ases.get(id).getDescription().indexOf("research") != -1
					|| ases.get(id).getDescription().indexOf("academic") != -1
					|| ases.get(id).getDescription().indexOf("lab") != -1
					|| ases.get(id).getDescription().indexOf("inria") != -1
					|| ases.get(id).getDescription().indexOf("ecole") != -1
					|| ases.get(id).getDescription().indexOf("school") != -1
					|| ases.get(id).getDescription().indexOf("educ") != -1
					|| ases.get(id).getDescription().indexOf("college") != -1
					|| ases.get(id).getDescription().indexOf("renater") != -1
					|| ases.get(id).getDescription().indexOf("institut") != -1) {

				ases.get(id).setType("UNIVERSITY");
				count++;

			}
		}
		return count;
	}

	public static int countTelecom(HashMap<String, AS> ases) {
		int count = 0;
		for (String id : ases.keySet()) {
			if (ases.get(id).getDescription().indexOf("telecom") != -1
					|| ases.get(id).getDescription().indexOf("orange") != -1
					|| ases.get(id).getDescription().indexOf("at&t") != -1
					|| ases.get(id).getDescription().indexOf("telefon") != -1
					|| ases.get(id).getDescription().indexOf("sprint") != -1
					|| ases.get(id).getDescription().indexOf("level3") != -1
					|| ases.get(id).getDescription().indexOf("communication") != -1
					|| ases.get(id).getDescription().indexOf("network") != -1
					|| ases.get(id).getDescription().indexOf("telekom") != -1
					|| ases.get(id).getDescription().indexOf("telefon") != -1
					|| ases.get(id).getDescription().indexOf("verizon") != -1) {

				ases.get(id).setType("TELECOM");
				count++;

			}
		}
		return count;
	}

	public static int countNational(HashMap<String, AS> ases) {
		int count = 0;
		for (String id : ases.keySet()) {
			if (ases.get(id).getDescription().indexOf("nation") != -1
					|| ases.get(id).getDescription().indexOf("regional") != -1
					|| ases.get(id).getDescription().indexOf("federal") != -1
					|| ases.get(id).getDescription().indexOf("state") != -1
					|| ases.get(id).getDescription().indexOf("commission") != -1
					|| ases.get(id).getDescription().indexOf("administration") != -1
					|| ases.get(id).getDescription().indexOf("agency") != -1
					|| ases.get(id).getDescription().indexOf("ministry") != -1
					|| ases.get(id).getDescription().indexOf("agentia") != -1
					|| ases.get(id).getDescription().indexOf("dod") != -1
					|| ases.get(id).getDescription().indexOf("usaisc") != -1
					|| ases.get(id).getDescription().indexOf(" nato ") != -1) {

				ases.get(id).setType("NATIONAL");
				count++;

			}
		}
		return count;
	}

	public static int countEnterprise(HashMap<String, AS> ases) {
		int count = 0;
		for (String id : ases.keySet()) {
			if (ases.get(id).getDescription().indexOf("enterprise") != -1
					|| ases.get(id).getDescription().indexOf("gmbh") != -1
					|| ases.get(id).getDescription().indexOf("sa") != -1
					|| ases.get(id).getDescription().indexOf("corporation") != -1
					|| ases.get(id).getDescription().indexOf("bank") != -1
					|| ases.get(id).getDescription().indexOf("llc") != -1
					|| ases.get(id).getDescription().indexOf("ltd") != -1
					|| ases.get(id).getDescription().indexOf("s.r.l") != -1
					|| ases.get(id).getDescription().indexOf("srl") != -1
					|| ases.get(id).getDescription().indexOf("ojsc") != -1
					|| ases.get(id).getDescription().indexOf("cjsc") != -1
					|| ases.get(id).getDescription().indexOf("pty") != -1
					|| ases.get(id).getDescription().indexOf("d.o.o.") != -1
					|| ases.get(id).getDescription().indexOf("inc.") != -1) {

				ases.get(id).setType("ENTERPRISE");
				count++;

			}
		}
		return count;
	}
}
