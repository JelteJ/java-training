package nl.jeltej.training.swing;

import nl.jeltej.training.filesearch.FileSearcherMain;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class TestingForm {
    private JPanel panelMain;
    private JTextField searchField;
    private JTable fileTable;
    private final FileSearcherMain fileSearcher = new FileSearcherMain();
    private final DefaultTableModel model;


    public TestingForm() {

        model = (DefaultTableModel) fileTable.getModel();
        TableColumn nameColumn = new TableColumn(0);
        nameColumn.setHeaderValue("File");
        nameColumn.setPreferredWidth(40);

        model.addColumn(nameColumn);
        model.setColumnIdentifiers(new Vector<>(List.of("File")));

        fileTable.setDefaultEditor(Object.class, null);
        fileTable.setModel(model);
        fileTable.repaint();

        searchField.addActionListener(event -> search());
        fileTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                openSelectedFile();
            }
        });
    }

    public static void main(String[] args) {
        JFrame testingFrame = new JFrame("Cymbal search");
        testingFrame.setContentPane(new TestingForm().panelMain);
        testingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Calculate size and center the frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        testingFrame.setSize(width / 2, height / 2);
        testingFrame.setPreferredSize(new Dimension(width / 2, height / 2));
        testingFrame.setLocationRelativeTo(null);

        URL url = Thread.currentThread().getContextClassLoader().getResource("icon.png");
        ImageIcon imgIcon = new ImageIcon(url);
        testingFrame.setIconImage(imgIcon.getImage());

        testingFrame.pack();

        testingFrame.setVisible(true);
    }

    private void search() {
        model.setRowCount(0);

        Path rootPath = Path.of("D:\\");

        List<Path> files = new ArrayList<>();
        try {
            files = fileSearcher.searchInDirectory(rootPath, searchField.getText());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        files.forEach(path ->
                model.addRow(Collections.singletonList(path.toAbsolutePath().toString()).toArray()));
    }

    private void openSelectedFile() {
        int selectedRow = fileTable.getSelectedRow();

        if (selectedRow < 0) {
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(new File((String) fileTable.getValueAt(selectedRow, 0)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
