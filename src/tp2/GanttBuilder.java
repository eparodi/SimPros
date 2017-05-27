package tp2;
import java.util.*;

public class GanttBuilder {
	
	private int rows;
	private int cols;
	private String[][] matrix;
	private ArrayList<GanttLine> ganttLineArray;
	
	public GanttBuilder() {
		ganttLineArray = new ArrayList<>();
	}
	
	public void insertGanttLine(GanttLine gLine) {
		ganttLineArray.add(gLine);
	}
	
	public void setGantt(int rows, HashMap<Integer, Integer> truePositionsMap, Trace trace) {
		this.rows = rows;
		this.cols = trace.getLineCount();
		matrix = new String[rows][cols];
		trace.build(matrix, truePositionsMap);
	}
	
	public void printGantt() {
		for (int i = 0; i < rows; i ++) {
			for (int j = 0; j < cols; j ++) {
				if (matrix[i][j] == null)
					System.out.print(" |");
				else
					System.out.print(matrix[i][j] + "|");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	public void printGanttLine(int index) {
		System.out.println("Time : " + index);
		ganttLineArray.get(index).printGanttLine();
	}
	
}
