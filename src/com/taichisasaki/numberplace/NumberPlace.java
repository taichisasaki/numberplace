import java.io.*;
import java.util.*;

/**
 * A number place solver
 */
class NumberPlace {
	final int MIN_NUMBER = 1;

	private int maxNumber;
	private int unassigned;
	private List<Integer> cells;

	NumberPlace(int maxNumber) {
		this(maxNumber, 0);
	}

	/**
	 * @param maxNumber Maximum number to assign to the cells
	 * @param unassigned Number of unassigned symbol
	 */
	NumberPlace(int maxNumber, int unassigned) {
		this.maxNumber = maxNumber;
		this.unassigned = unassigned;
	}

	/**
	 * Solves the number place
	 */
	boolean solve() {
		if (!cells.contains(unassigned)) {
			return true;
		}

		int index = cells.indexOf(unassigned);

		for (int number : getAlternatives(index)) {
			cells.set(index, number);

			if (solve()) {
				return true;
			}
		}

		cells.set(index, unassigned);
		return false;
	}

	/**
	 * Gets alternatives to assign to the cell of index
	 */
	protected Set<Integer> getAlternatives(int index) {
		Set<Integer> set = new HashSet<Integer>();

		for (int i = MIN_NUMBER; i <= maxNumber; i++) {
			set.add(i);
		}

		set.removeAll(getNumbersInRow(index));
		set.removeAll(getNumbersInCol(index));
		set.removeAll(getNumbersInBox(index));
		return set;
	}

	/**
	 * Gets numbers in the row that contains the cell of index
	 */
	protected Set<Integer> getNumbersInRow(int index) {
		Set<Integer> set = new HashSet<Integer>();

		for (int i = index / maxNumber * maxNumber, max = i + maxNumber; i < max; i++) {
			set.add(cells.get(i));
		}

		return set;
	}

	/**
	 * Gets numbers in the column that contains the cell of index
	 */
	protected Set<Integer> getNumbersInCol(int index) {
		Set<Integer> set = new HashSet<Integer>();

		for (int i = index % maxNumber; i < cells.size(); i += maxNumber) {
			set.add(cells.get(i));
		}

		return set;
	}

	/**
	 * Gets numbers in the box that contains the cell of index
	 */
	protected Set<Integer> getNumbersInBox(int index) {
		int boxWidth = getBoxWidth();
		int boxTop = index / maxNumber / boxWidth * boxWidth;
		int boxBottom = boxTop + boxWidth;
		int boxLeft = index % maxNumber / boxWidth * boxWidth;
		int boxRight = boxLeft + boxWidth;

		Set<Integer> set = new HashSet<Integer>();

		for (int y = boxTop; y < boxBottom; y++) {
			for (int x = boxLeft; x < boxRight; x++) {
				set.add(cells.get(y * maxNumber + x));
			}
		}

		return set;
	}

	/**
	 * Gets a width of boxes
	 */
	int getBoxWidth() {
		return (int) Math.sqrt(maxNumber);
	}

	public List<Integer> getCells() {
		return cells;
	}

	public void setCells(List<Integer> cells) {
		this.cells = cells;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (int y = 0; y < maxNumber; y++) {
			for (int x = 0; x < maxNumber; x++) {
				if (x != 0) {
					builder.append(",");
				}

				builder.append(cells.get(y * maxNumber + x));
			}

			builder.append(System.getProperty("line.separator"));
		}

		return builder.toString();
	}
}
