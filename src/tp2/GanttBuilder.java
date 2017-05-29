package tp2;
import java.lang.reflect.Array;
import java.util.*;

public class GanttBuilder {
	
	private int rows;
	private int cores;
	private int cols;
	private String[][] matrix;
	private String[][] gantt;
	private ArrayList<GanttLine> ganttLineArray;
	private ArrayList<String[]> infoMatrix;
	
	public GanttBuilder() {
		ganttLineArray = new ArrayList<>();
	}

	public String[][] getGantt() {
		return gantt;
	}
	
	public void insertGanttLine(GanttLine gLine) {
		ganttLineArray.add(gLine);
	}
	
	public void setGantt(int rows, int cores, HashMap<Integer, Integer> truePositionsMap, Trace trace) {
		this.rows = rows;
		this.cols = trace.getLineCount();
		this.cores = cores;
		matrix = new String[rows + cores][cols];
		gantt = new String[rows + cores][cols + 3];		// 3 for the info columns.
		trace.build(matrix, truePositionsMap);
		completeWithSO();
		finishGantt();
	}

	public void setInfo(ArrayList<String[]> infoMatrix) {
		this.infoMatrix = infoMatrix;
	}

	private void finishGantt() {
		for (int row = 0; row < rows; row ++) {
			for (int col = 0; col < 3; col ++) {
				if (infoMatrix.get(row)[col] == null) {
					String s = " ";
					if (col == 1)
						s = "      ";
					if (col == 2)
						s += "";
					gantt[row][col] = s;
				}
				else {
					String s = infoMatrix.get(row)[col] + "";
					if (col == 2)
						s += "";
					gantt[row][col] = s;
				}
			}
			for (int col = 0; col < cols; col ++) {
				if (matrix[row][col] == null)
					gantt[row][col + 3] = " ";
				else
					gantt[row][col + 3] = matrix[row][col] + "";
			}
		}
		for (int row = rows; row < rows + cores; row ++) {
            gantt[row][0] = " ";
            gantt[row][1] = " ";
            gantt[row][2] = "SO";
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col] == null)
                    gantt[row][col + 3] = " ";
                else
                    gantt[row][col + 3] = matrix[row][col] + "";
            }
        }
	}
	
	public void printGanttLine(int index) {
		System.out.println("Time : " + index);
		ganttLineArray.get(index).print();
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
