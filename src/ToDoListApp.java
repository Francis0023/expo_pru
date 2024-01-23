import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ToDoListApp {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JCheckBox checkBox;
    private JFormattedTextField taskTextField;

    public ToDoListApp() {
        // Inicializar la interfaz gráfica
        initializeUI();
    }

    public void showUI() {
        // Hacer visible la ventana
        frame.setVisible(true);
    }

    private void initializeUI() {
        // Crear la ventana principal
        frame = new JFrame("Lista de Tareas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        // Crear la tabla y su modelo
        tableModel = new DefaultTableModel(new Object[]{"Tarea", "Completada"}, 0);
        table = new JTable(tableModel);

        // Crear el menú
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem openMenuItem = new JMenuItem("Abrir");
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        fileMenu.add(openMenuItem);
        // Crear la barra de menú y agregar el menú
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        // Crear el panel de entrada de tareas
        JPanel inputPanel = new JPanel();
        taskTextField = new JFormattedTextField();
        taskTextField.setColumns(20);
        checkBox = new JCheckBox("Completada");
        JButton addButton = new JButton("Agregar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        inputPanel.add(new JLabel("Tarea: "));
        inputPanel.add(taskTextField);
        inputPanel.add(checkBox);
        inputPanel.add(addButton);

        // Crear el panel principal y agregar componentes
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(new JScrollPane(table));
        mainPanel.add(inputPanel);

        // Agregar el panel principal a la ventana
        frame.add(mainPanel);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                // Leer el contenido del archivo seleccionado
                FileReader fileReader = new FileReader(fileChooser.getSelectedFile());
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                // Limpiar la tabla antes de agregar nuevas filas
                tableModel.setRowCount(0);

                // Leer cada línea y agregarla a la tabla
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(","); // Supongamos que los datos están separados por comas
                    tableModel.addRow(data);
                }

                // Cerrar el lector
                bufferedReader.close();

            } catch (Exception e) {
                e.printStackTrace();
                // Manejar errores según sea necesario
            }
        }
    }

    private void addTask() {
        // Obtener valores de los componentes de la interfaz de usuario
        String task = taskTextField.getText().trim();
        boolean completed = checkBox.isSelected();

        // Validar la entrada (no permitir tareas vacías)
        if (task.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, ingrese una tarea", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Agregar la tarea a la tabla
        addTaskToTable(task, completed);

        // Limpiar el campo de texto y la casilla de verificación después de agregar la tarea
        taskTextField.setText("");
        checkBox.setSelected(false);
    }

    private void addTaskToTable(String task, boolean completed) {
        try {
            // Agregar la tarea a la tabla
            tableModel.addRow(new Object[]{task, completed});
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir al agregar la tarea
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error al agregar la tarea", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



}
