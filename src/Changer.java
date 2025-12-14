import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;

public class Changer extends javax.swing.JFrame {

    private final Table table =  new Table();

    private final String[] text = new String[] {
            "Name\t",
            "Group\t",
            "Average grade\t",
    };

    private final JTextField[] textAreas = new JTextField[] {
            new JTextField("Name\t"),
            new JTextField("Group\t"),
            new JTextField("Average grade\t"),
    };

    private final JButton addButton = new JButton("Add");
    private final JButton removeButton = new JButton("Remove");

    public Changer() {

        GridLayout gl = new GridLayout(4,2);
        gl.setHgap(10);
        gl.setVgap(10);

        setTitle("Changer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        setBounds(
                width / 2 - width / 5 - width / 3 / 2,
                height / 2 - height / 3,
                width / 5,
                height / 3);
        setLayout(gl);

        for (int i = 0; i < 3; i++) {
            JLabel[] label = new JLabel[]{
                    new JLabel("Name:"),
                    new JLabel("Group:"),
                    new JLabel("Average grade:"),
            };
            label[i].setHorizontalAlignment(JLabel.RIGHT);
            add(label[i]);

            textAreas[i].addFocusListener(new areaField());
            add(textAreas[i]);
        }

        addButton.addActionListener(new tableChanger());
        add(addButton);

        removeButton.addActionListener(new tableChanger());
        add(removeButton);

        setVisible(true);

    }

    private class tableChanger implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == addButton){

                String[] add = new String[3];

                if (!textAreas[2].getText().matches("[0-9]")){
                    JOptionPane.showMessageDialog(null, "Avg grade must be number");
                    return;
                }

                for(int i = 0; i < 3; i++){
                    if(Objects.equals(textAreas[i].getText(), text[i])){
                        add[i] = "";
                    }
                    else {
                        add[i] = textAreas[i].getText();
                    }
                }
                table.model.addRow(add);
                table.getHighest();
            }

            else if(e.getSource() == removeButton){
                if(table.model.getRowCount() == 0){
                    JOptionPane.showMessageDialog(null, "There are no rows in the table");
                    return;
                }
                try{
                    table.model.removeRow(table.table.getSelectedRow());
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Row is not selected");
                }
                table.getHighest();
            }

        }

    }

    private class areaField implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {

            for (int i = 0; i < textAreas.length; i++) {
                if (e.getSource() == textAreas[i] && Objects.equals(textAreas[i].getText(), text[i])){
                    textAreas[i].setText("");
                }
            }

        }

        @Override
        public void focusLost(FocusEvent e) {

            for (int i = 0; i < textAreas.length; i++) {
                if (e.getSource() == textAreas[i] && Objects.equals(textAreas[i].getText(), "")){
                    textAreas[i].setText(text[i]);
                }
            }

        }
    }


}