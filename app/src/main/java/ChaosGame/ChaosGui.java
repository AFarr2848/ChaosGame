package ChaosGame;
/*
 * Aaron Farrar
 * APCSA Pd 1
 * Chaos Game
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * The main GUI frame
 */
public class ChaosGui extends JFrame implements ActionListener {
  private OptionsPanel optionsPanel;
  private ChaosShape shape;
  private GamePanel gamePanel;
  private JButton startButton;
  private Color color;
  private int iterations;
  private double rotations, skew;
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

  /**
   * Grabs appropriate values from the OptionsPanel and calls paintShape when the
   * run button is pressed
   *
   * @param e Action
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == startButton) {
      iterations = Integer.parseInt(optionsPanel.fieldIterations.getText());
      rotations = Double.parseDouble(optionsPanel.fieldRotation.getText());
      skew = Double.parseDouble(optionsPanel.fieldSkew.getText());
      shape = new ChaosShape(optionsPanel.selectedSides, panelX / 2, new Point(panelX / 2, panelY / 2));
      color = optionsPanel.colorChosen;
      if (optionsPanel.customColor)
        gamePanel.paintShape(shape, iterations, p -> shape.getRandomCorner(), optionsPanel.btnPressed, color);
      else
        gamePanel.paintShape(shape, iterations, p -> shape.getRandomCorner(), optionsPanel.btnPressed);

    }
  }

}
