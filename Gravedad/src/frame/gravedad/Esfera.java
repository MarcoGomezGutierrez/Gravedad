package frame.gravedad;

import java.awt.Color;
import java.awt.Graphics;

import lombok.*;

@AllArgsConstructor
public class Esfera {
	
	@Getter @Setter private int x;
	@Getter @Setter private int y;
	private final Color color;
	
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(x - 25, y - 25, 50, 50);
	}
}
