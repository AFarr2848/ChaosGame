package ChaosGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChaosGui extends JFrame implements ActionListener {
  private OptionsPanel optionsPanel;
  private ChaosShape shape;
  private GamePanel gamePanel;
  private JButton startButton;
  private int iterations;
  private static int panelX = 500, panelY = 500;

  public ChaosGui() {
    setTitle("hello");
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    optionsPanel = new OptionsPanel();
    gamePanel = new GamePanel();
    shape = new ChaosShape(3, panelX / 2, new Point(panelX / 2, panelY / 2));
    startButton = new JButton("Run");

    startButton.addActionListener(this);

    add(optionsPanel, BorderLayout.EAST);
    add(gamePanel, BorderLayout.CENTER);
    add(startButton, BorderLayout.SOUTH);
    pack();
    setVisible(true);
  }

  public static void main(String[] args) {
    new ChaosGui();
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == startButton) {
      shape = new ChaosShape(optionsPanel.selectedSides, panelX / 2, new Point(panelX / 2, panelY / 2));
      iterations = optionsPanel.selectedIterations;
      gamePanel.paintShape(shape, iterations, p -> shape.getRandomCorner(), optionsPanel.btnPressed);

    }
  }

}
