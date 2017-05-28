package tp2;
import java.util.*;

public class GanttBuilder {
	
	private int rows;
	private int cores;
	private int cols;
	private String[][] matrix;
	private ArrayList<GanttLine> ganttLineArray;
	
	public GanttBuilder() {
		ganttLineArray = new ArrayList<>();
	}
	
	public void insertGanttLine(GanttLine gLine) {
		ganttLineArray.add(gLine);
	}
	
	public void setGantt(int rows, int cores, HashMap<Integer, Integer> truePositionsMap, Trace trace) {
		this.rows = rows;
		this.cols = trace.getLineCount();
		this.cores = cores;
		matrix = new String[rows + cores][cols];
		trace.build(matrix, truePositionsMap);
		completeWithSO();
	}
	
	public void printGantt(String[][] infoMatrix) {
		for (int i = 0; i < rows; i ++) {
			for (int k = 0; k < 3; k ++) {
				if (infoMatrix[i][k] == null && k == 1)
					System.out.print("      |");
				else if (infoMatrix[i][k] == null)
					System.out.print(" |");
				else
					System.out.print(infoMatrix[i][k] + "|");
			}
			System.out.print(" |*|");
			for (int j = 0; j < cols; j ++) {
				if (matrix[i][j] == null)
					System.out.print(" |");
				else
					System.out.print(matrix[i][j] + "|");
			}
			System.out.println("");
		}
		for (int i = rows; i < rows + cores; i ++) {
			System.out.print("        SO| |*|");
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

	private void completeWithSO() {
		for (int col = 0; col < cols; col ++) {
			for (int core = 1; core <= cores; core ++) {
				boolean found = false;
				for (int row = 0; row < rows && !found; row ++) {
					if (matrix[row][col] != null && matrix[row][col].equals("" + core))
						found = true;
				}
				if (!found)
					matrix[rows - 1 + core][col] = "" + core;
			}
		}
	}
	
}
