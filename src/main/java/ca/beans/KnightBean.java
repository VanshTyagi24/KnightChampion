package ca.beans;
import java.io.Serializable;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class KnightBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String knightName;
	private int knightId;
	private int trial;
	private int positionX;
	private int positionY;
	private double average;
	//private double average;
	
}
