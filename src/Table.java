import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class Table extends JFrame {

    JLabel highestGrade = new JLabel("Student with highest average grade: NULL");

    ArrayList<String> grades = new ArrayList<>();

    private final Object[][] students = new String[][]{
            {"Dmytro", "PS 4-1", "4.1"},
            {"Ivan", "DA 3-2", "2.3"},
            {"Maks", "AR 1-4", "3"}
    };
    private final Object[] columnsNames = new String[]{"Name", "Group", "Average grade"};

    public DefaultTableModel model = new DefaultTableModel(students, columnsNames);
    public JTable table = new JTable(model);

    public Table() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Table");
        setLayout(new BorderLayout(5, 5));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        setBounds(
                width / 2 - (width / 3) / 2,
                height / 2 - (int) (height / 1.5) / 2,
                width / 3,
                (int) (height / 1.5));

        table.setPreferredScrollableViewportSize(new Dimension(width, height));
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        table.addPropertyChangeListener(new editing());

        highestGrade.setHorizontalAlignment(SwingConstants.CENTER);
        add(highestGrade, BorderLayout.PAGE_START);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        getHighest();

        setVisible(true);
    }

    public void getHighest() {

        if (model.getRowCount() == 0) {
            highestGrade.setText("Student table is empty");
            return;
        }

        grades = new ArrayList<>();

        double highest = 0;
        int pos = 0;
        for (int i = 0; i < model.getRowCount(); i++) {

            grades.add((String) model.getValueAt(i, 2));

            if (highest < Double.parseDouble((String) model.getValueAt(i, 2))) {
                highest = Double.parseDouble((String) model.getValueAt(i, 2));
                pos = i;
            }
        }

        highestGrade.setText("Student with highest average grade: " +
                model.getValueAt(pos, 0) + " (" +
                model.getValueAt(pos, 1) + "): " +
                model.getValueAt(pos, 2));

    }

    public class editing implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            int row = table.getEditingRow();
            int collum = table.getEditingColumn();

            if (collum == 2){
                if (!String.valueOf(table.getValueAt(row, collum)).matches("[0-9.]+")) {
                    JOptionPane.showMessageDialog(null, "Selected collum must be a number");
                    table.setValueAt(grades.get(collum-1), row, collum);
                }
            }

            getHighest();

        }
    }
}



