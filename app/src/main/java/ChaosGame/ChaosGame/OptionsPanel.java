package ChaosGame;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

    
public class OptionsPanel extends JPanel implements ActionListener{
    private JComboBox<String> menuPolygon;
    private JComboBox<String> menuRule;
    private JComboBox<String> menuColor;
    private JTextField fieldIterations, fieldRotation, fieldSkew;
    private JRadioButton btnShape;
    public String selectedPolygon, selectedRule, selectedColor;
    public int selectedIterations;
    public double selectedRotation, selectedSkew;
    public boolean btnPressed;
    private NumberFormatter formatterInt, formatterDouble;
    
    public OptionsPanel(){
        setLayout(new GridLayout(0, 2));
        setBorder(BorderFactory.createTitledBorder("Settings"));


        //I looked this up
        formatterInt = new NumberFormatter(new DecimalFormat("0.0"));
        formatterInt.setMinimum(0);
        formatterInt.setAllowsInvalid(false);
        formatterInt.setCommitsOnValidEdit(true);
        formatterDouble = new NumberFormatter(new DecimalFormat("0"));
        formatterDouble.setMinimum(0);
        formatterDouble.setAllowsInvalid(false);
        formatterDouble.setCommitsOnValidEdit(true);

        String[] optsPolygon = {"Triangle", "Square", "Pentagon", "Hexagon"};
        String[] optsRule = {"temp", "temp", "temp", "temp"};
        String[] optsColor = {"temp", "temp", "temp", "temp"};

        menuPolygon = new JComboBox<>(optsPolygon);
        menuRule = new JComboBox<>(optsRule);
        menuColor = new JComboBox<>(optsColor);

        fieldIterations = new JFormattedTextField(formatterInt);
        fieldRotation = new JFormattedTextField(formatterDouble);
        fieldSkew = new JFormattedTextField(formatterDouble);

        btnShape = new JRadioButton("Draw Polygon");

        menuPolygon.addActionListener(this);
        menuRule.addActionListener(this);
        menuColor.addActionListener(this);

        fieldIterations.addActionListener(this);
        fieldRotation.addActionListener(this);
        fieldSkew.addActionListener(this);

        btnShape.addActionListener(this);

        add(new JLabel("Polygon"));
        add(menuPolygon);
        add(new JLabel("Generation Rule"));
        add(menuRule);
        add(new JLabel("Dot Color"));
        add(menuColor);
        add(new JLabel("Iterations"));
        add(fieldIterations);
        add(new JLabel("Rotation"));
        add(fieldRotation);
        add(new JLabel("Skew"));
        add(fieldSkew);
        add(btnShape);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuPolygon)
            selectedPolygon = (String)menuPolygon.getSelectedItem();
        if(e.getSource() == menuRule)
            selectedRule = (String)menuRule.getSelectedItem();
        if(e.getSource() == menuColor)
            selectedColor = (String)menuColor.getSelectedItem();

        if(e.getSource().getClass() == JTextField.class){
            selectedRotation = Double.parseDouble(fieldRotation.getText());
            selectedSkew = Double.parseDouble(fieldSkew.getText());
            selectedIterations = Integer.parseInt(fieldIterations.getText());
        }
    
        if(e.getSource() == btnShape)
            btnPressed = btnShape.isSelected();
    }

}
