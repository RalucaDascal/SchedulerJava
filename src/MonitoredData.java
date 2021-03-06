import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MonitoredData {
	private String startTime;
	private String endTime;
	private String activityLabel;
	private static FileWriter out1;
	private static FileWriter out2;
	private static FileWriter output3;
	static int leavingC = 0;
	static int toiletingC = 0;
	static int showeringC = 0;
	static int sleepingC = 0;
	static int breakfastC = 0;
	static int lunchC = 0;
	static int dinnerC = 0;
	static int snackC = 0;
	static int spareC = 0;
	static int groomingC = 0;

	public MonitoredData(String startTime, String endTime, String activityLabel) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.activityLabel = activityLabel;
	}

	public static ArrayList<MonitoredData> readData() throws IOException {
		ArrayList<MonitoredData> data = new ArrayList<MonitoredData>();
		String filename = "Activities.txt";
		Path path = Paths.get(filename);
		Stream<String> stream = Files.lines(path);
		ArrayList<String> lista = (ArrayList<String>) stream.collect(Collectors.toList());
		for (int i = 0; i < lista.size(); i++) {
			String[] sir = lista.get(i).split("		");
			String[] sir1=sir[2].split(" ");
			String[] sir2=sir1[0].split("	");
			MonitoredData newActivity = new MonitoredData(sir[0], sir[1], sir2[0]);
			data.add(newActivity);
		}

		return data;

	}

	/*
	 * public static int countDays(ArrayList<MonitoredData> data) { String day =
	 * data.get(0).getStartTime().substring(0, 10); int count = 1; for
	 * (MonitoredData item : data) { if
	 * (!day.equals(item.getStartTime().substring(0, 10))) {
	 * 
	 * count = count + 1; day = item.getStartTime().substring(0, 10); } if
	 * (!day.equals(item.getEndTime().substring(0, 10))) {
	 * 
	 * count = count + 1; day = item.getEndTime().substring(0, 10); }
	 * 
	 * } return count; }
	 */
	public static int countDays(ArrayList<MonitoredData> data) {
		ArrayList<String> date = new ArrayList<String>();
		for (int i = 0; i < data.size(); i++) {
			date.add(data.get(i).getStartTime().substring(0, 10));
			date.add(data.get(i).getEndTime().substring(0, 10));
		}
		int count = (int) date.stream().distinct().count();

		return count;
	}

  /*	public static void count(ArrayList<MonitoredData> lista) {
		Map m = new HashMap();
		for (MonitoredData item : lista) {
			switch (item.getActivityLabel()) {
			case "Leaving":
				leavingC++;
				break;
			case "Toileting":
				toiletingC++;
				break;
			case "Showering":
				showeringC++;
				break;
			case "Sleeping":
				sleepingC++;
				break;
			case "Breakfast":
				breakfastC++;
				break;
			case "Lunch":
				lunchC++;
				break;
			case "Dinner":
				dinnerC++;
				break;
			case "Snack":
				snackC++;
				break;
			case "Spare_Time/TV":
				spareC++;
				break;
			case "Grooming":
				groomingC++;
				break;

			}
		}
		m.put("Leaving", leavingC);
		m.put("Toileting", toiletingC);
		m.put("Showering", showeringC);
		m.put("Sleeping", sleepingC);
		m.put("Breakfast", breakfastC);
		m.put("Lunch", lunchC);
		m.put("Dinner", dinnerC);
		m.put("Snack", snackC);
		m.put("Spare_Time/TV", spareC);
		m.put("Grooming", groomingC);
		System.out.println(Arrays.asList(m));
	}*/
	public static Map<String, Long> countActivities(ArrayList<MonitoredData> data) {
		List<String> date = new ArrayList<String>();
		for (int i = 0; i < data.size(); i++) {
	     	date.add(data.get(i).getActivityLabel());
		}
		Map<String, Long> m = date.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
		return m;
	}

	public static void main(String[] args) throws IOException {

		ArrayList<MonitoredData> list = readData();
		FileWriter fstream1 = new FileWriter("Task_1.txt");
		FileWriter fstream2 = new FileWriter("Task_2.txt");
		FileWriter fstream3 = new FileWriter("Task_3.txt");
		BufferedWriter out1 = new BufferedWriter(fstream1);
		BufferedWriter out2 = new BufferedWriter(fstream2);
		BufferedWriter out3 = new BufferedWriter(fstream3);
		for (int i = 0; i < list.size(); i++) {
			out1.write(list.get(i).getStartTime() + "		" + list.get(i).getEndTime() + "		"
					+ list.get(i).getActivityLabel() + "\n");
		}	
		Map<String, Long> m =countActivities(list);
		m.forEach((key, value) -> {
			try {
				out3.write(key + "=" + value+"\n");
			} catch (IOException e) {
				System.out.println("Exception");
				e.printStackTrace();
			}
		});
		out2.write("Numarul de zile e " + countDays(list));
		out1.close();
		out2.close();
		out3.close();

	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getActivityLabel() {
		return activityLabel;
	}

	public void setActivityLabel(String activityLabel) {
		this.activityLabel = activityLabel;
	}
}
