package main;

import map.MapGen;

import javax.swing.JFrame;

public class Main {

		public static void main(String[] args) {
		//make a window
			JFrame window = new JFrame();
			
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(false);
			window.setTitle("Phantom Dash");
			
			GamePanel gamePanel = new GamePanel();
			window.add(gamePanel);
			
			window.pack();
			
			window.setLocationRelativeTo(null);
			window.setVisible(true);
			gamePanel.setupGame();
			gamePanel.startGameTimer();
		}
}
