package newsnake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Pantalla extends JPanel implements ActionListener, KeyListener {

	// ATRIBUTOS
	private static final long serialVersionUID = 1L;

	private int WIDTH = 400;
	private int HEIGHT = 400;
	private int SIZE = WIDTH/10; // largo de la serpiente en pixels (para dibujar solo)
	
	private boolean w;
	private boolean a;
	private boolean s;
	private boolean d;
	
	private Food food;
	
	private boolean GAMEOVER;
	private Timer timer;
	
	private Snake snake;
	private ArrayList<Snake> snakes;
	
	private int snake_size = 1;
	private int score = 0;
	private int x = 1;
	private int y = 1;
	
	// CONSTRUCTOR
	public Pantalla() {
		setSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
		
		setFocusable(true); 
		
		timer = new Timer(400, this); // 400 nanosegundos
		timer.start();
		GAMEOVER = false;
		
		w = false;
		a = false;
		s = false;
		d = false;
		
		snakes = new ArrayList<Snake>();
		snake = new Snake(x,y);
		snakes.add(snake);
		food = crearFood();
		
		addKeyListener(this);
	}
	
	// METODO QUE PINTA LA LAMINA O PANTALLA
	public void paint(Graphics g) {
		super.paint(g);
		// Pintar Score
		g.setColor(Color.WHITE);
		g.drawString("Puntuación: " + score, 10, 415);
		g.drawString("Tamaño Serpiente: " + snake_size, 170, 415);
		
		// Pintar comida
		g.setColor(Color.RED);
		g.fillRect(food.getX()*SIZE, food.getY()*SIZE, SIZE, SIZE);
		
		// Pintar serpiente
		g.setColor(Color.GREEN);
		for (Snake snake: snakes) {
			g.fillRect(snake.getX()*SIZE, snake.getY()*SIZE, SIZE, SIZE);
		}
		
		// Pintar lineas X
		g.setColor(Color.DARK_GRAY);
		for (int i = 0; i <= 10; i++) {
			g.drawLine(0, i*WIDTH/10, WIDTH, i*WIDTH/10);
		}
		
		// Pintar lineas Y
		for (int i = 0; i <= 10; i++) {
			g.drawLine(i*HEIGHT/10, 0,  i*HEIGHT/10, HEIGHT);
		}
		
		
	}
	
	// METODO QUE CREA LA COMIDA EN UN PUNTO RANDOM
	public Food crearFood() {
		return new Food((int) (Math.random()*9)+1, (int) (Math.random()*9)+1);
	}
	
	// METODO QUE MUEVE LA SERPIENTE
	public void mover() {
		// cada vez que el size de snake es 0, creo un nuevo cuadrado en X,Y.
		if (snakes.size() == 0) {
			snake = new Snake(x,y);
			snakes.add(snake);
		}
		
		
		// Si se sale de los margenes pierde
		if (x < 0 || y < 0 ||  x > 9|| y > 9) { 
			GAMEOVER = true;
		} else { // Si no, se mueve normal
			if (w) y--;
			if (a) x--;
			if (s) y++;
			if (d) x++;
		}
		
		// Voy creando cada vez un cuadrado nuevo, en dirección a W,A,S o D.
		snake = new Snake(x, y); 			
		snakes.add(snake);
		
		// A la vez que creo, elimino el último para que la serpiente no crezca si no come.
		if (snakes.size() > snake_size) {
			snakes.remove(0); 				
		}
		
		// Si la serpiente toca una parte de su cuerpo el juego termina
		for (int i = 0; i < snakes.size(); i++) {
			if (x == snakes.get(i).getX() && y == snakes.get(i).getY()) {
				if (i != snakes.size() - 1) {
					GAMEOVER = true;
				}
			}
		}
		
		// Si la cabeza coicide con la comida, crece la serpiente
		if (snakes.get(0).getX() == food.getX() && snakes.get(0).getY() == food.getY()) {
			snake_size += 1;
			food = crearFood();
			score += 10;
		}
	}
	
	// METODO QUE REALIZA ACCIONES CADA VEZ QUE EL RELOJ (timer) SALTA (cada 400 nanosegundos).
	public void actionPerformed(ActionEvent e) {
		if (!GAMEOVER) {
			mover();
			repaint();
		} else {
			timer.stop();
			JOptionPane.showMessageDialog(null, "GAME OVER! \nPuntuacion: " + score + "\nTamaño serpiente: " + snake_size);
		}
	}

	// METODOS DE DETECCION DE TECLAS -- HACIA ABAJO --
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W && !s) {
			w = true;
			a = false;
			s = false;
			d = false;
		}
		
		if (key == KeyEvent.VK_A && !d) {
			w = false;
			a = true;
			s = false;
			d = false;
		}
		
		if (key == KeyEvent.VK_S && !w) {
			w = false;
			a = false;
			s = true;
			d = false;
		}
		
		if (key == KeyEvent.VK_D && !a) {
			w = false;
			a = false;
			s = false;
			d = true;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		// No lo uso (puesto por la interfaz)

	}

	public void keyTyped(KeyEvent e) {
		// No lo uso  (puesto por la interfaz)
	}

	
}
