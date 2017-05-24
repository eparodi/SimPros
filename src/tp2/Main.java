package tp2;

public class Main {

	public static void main(final String[] args) {
		
		//String input = new String("P|K|3|1|2|K|2|2|3|K|4|2|2|P|K|4|1|1|K|2|3|1|K|1|1|3|2|1|K|5|3|2|P|K|3|2|1||2|3||");
		Example example = new Example();
		//example.setExample1();			// 2 cores
		example.setExample2();			// 1 core
		//example.setExample3();			// 3 cores
		//example.setExample4();
		
		Scheduler scheduler = new Scheduler(example.getCoresArray(), example.getProcessArray(), example.getDeviceCount(), 2);
		TraceManager tManager = new TraceManager();
		scheduler.run(tManager);
		tManager.buildGantt(example.getRows(), example.getTruePositionsMap());
		tManager.printGantt();
		//tManager.print();
	}
	
}
