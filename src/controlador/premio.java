package controlador;

import javax.swing.*;

public class premio {
    private JPanel mainPremio;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton CANCELARButton;
    private JButton ACEPTARButton;
    private JComboBox comboBox1;


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JPanel getMainPremio() {
        return mainPremio;
    }

    public void setMainPremio(JPanel mainPremio) {
        this.mainPremio = mainPremio;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public void setTextField1(JTextField textField1) {
        this.textField1 = textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public void setTextField2(JTextField textField2) {
        this.textField2 = textField2;
    }

    public JButton getCANCELARButton() {
        return CANCELARButton;
    }

    public void setCANCELARButton(JButton CANCELARButton) {
        this.CANCELARButton = CANCELARButton;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public void setTextField3(JTextField textField3) {
        this.textField3 = textField3;
    }

    public JButton getACEPTARButton() {
        return ACEPTARButton;
    }

    public void setACEPTARButton(JButton ACEPTARButton) {
        this.ACEPTARButton = ACEPTARButton;
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    public void setComboBox1(JComboBox comboBox1) {
        this.comboBox1 = comboBox1;
    }
}
