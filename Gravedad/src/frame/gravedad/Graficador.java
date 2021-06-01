package frame.gravedad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

public class Graficador extends JPanel {

	private static final long serialVersionUID = 376340130963174408L;
	private List<Esfera> list;
	private Random r;
	private Gravedad gravedad;
	private JPanel panel;
	
	public Graficador() {
		list = new ArrayList<Esfera>();
		this.setSize(500, 500);
		this.setBackground(Color.darkGray);
		r = new Random();
		crearEvent();
		panel = this;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).paint(g);
		}
		this.repaint();
	}
	
	public void crearFigura(Point point) {
		Color color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
		Esfera esfera = new Esfera(point.x, point.y, color);
		list.add(esfera);
		gravedad = new Gravedad(esfera);
		gravedad.start();
	}
	
	private void crearEvent() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				crearFigura(e.getPoint());
			}
		});
	}
	
	private class Gravedad extends Thread {
		
		private int alturaFinal;
		private int alturaInicial;
		private int peso = 2;
		private float gravedad;
		private int elasticidad;
		private boolean detener = false;
		private Esfera esfera;
		private float velocidad;
		private String direccion;
		
		public Gravedad(Esfera esfera) {
			gravedad = peso * 9.8f;
			this.esfera = esfera;
			direccion = "abajo";
			alturaFinal = panel.getWidth() - 25;
			alturaInicial = esfera.getY();
			elasticidad = esfera.getY() + (esfera.getY() - (esfera.getY() / 2));
			//v^2 = v1 - 2g(hf - hi)
			velocidad = (2 * gravedad) * (alturaFinal - alturaInicial);
			velocidad = (int) Math.sqrt(velocidad);
		}
		
		@Override
		public void run() {
			int aI = alturaInicial;
			while(true) {
				try {Thread.sleep(1);} catch (InterruptedException e) {}
				if (direccion == "abajo") {
					aI++;
				} else if (direccion == "arriba"){
					aI--;
				}
				esfera.setY(aI);
				if (aI == alturaFinal && direccion == "abajo") {
					direccion = "arriba";
					if (detener) break;
				} else if (aI < elasticidad && direccion == "arriba") {
					direccion = "abajo";
					aI = elasticidad;
					elasticidad = elasticidad + (elasticidad - elasticidad / 2);
				}
				if (elasticidad > panel.getWidth()) {
					detener = true;
				}
			}
		}
	}
	
}
