package ChaosGame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

public class ChaosGame extends JFrame {
  private OptionsPanel optionsPanel;
  private ChaosShape shape;
  private GamePanel gamePanel;

  public ChaosGame() {
    setTitle("hello");
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    optionsPanel = new OptionsPanel();
    gamePanel = new GamePanel();
    shape = new ChaosShape(3, 400);
    gamePanel.paintShape(shape);

    add(optionsPanel, BorderLayout.EAST);
    add(gamePanel, BorderLayout.CENTER);
    pack();
    setVisible(true);
  }

  public static void main(String[] args) {
    new ChaosGame();
  }

  private class GamePanel extends JPanel {
    private ChaosShape shape;

    public GamePanel() {
      setPreferredSize(new Dimension(500, 500));
      setBackground(Color.BLACK);
      setVisible(true);
    }

    public void paintShape(ChaosShape shape) {
      this.shape = shape;
      repaint();
    }

    public void paintComponent(Graphics g) {
      g.setColor(Color.WHITE);
      g.drawPolygon(shape.polygon);

    }
  }
}
