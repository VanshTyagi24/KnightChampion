package ca.beans;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Path {
	private String name;
	private int[][] knightPath = new int[8][8];
	private int tri;
}
