package newsnake;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame ventana_principal = new JFrame();
		ventana_principal.setTitle("Snake");
		ventana_principal.setSize(new Dimension(407,450));
		ventana_principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana_principal.setResizable(false);
		ventana_principal.setVisible(true);
		ventana_principal.setLocationRelativeTo(null);
		Pantalla pantalla_principal = new Pantalla();
		ventana_principal.add(pantalla_principal);
	}

}
