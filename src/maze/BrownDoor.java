package maze;

import java.awt.Color;

public class BrownDoor extends Door{
	public BrownDoor(Room r1, Room r2) {
		super(r1, r2);
	}
	
	public Color getColor()
	{
		return new Color(96,73,45);
	}
}
