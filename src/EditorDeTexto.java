import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditorDeTexto extends JFrame implements ActionListener {
    private JTextArea areaTexto;
    private JFileChooser fileChooser;
    private String nombreArchivo;

    public EditorDeTexto() {
        setTitle("Editor de Texto Simple");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        areaTexto = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        JMenuBar menuBar = new JMenuBar();

        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem nuevoItem = new JMenuItem("Nuevo");
        JMenuItem abrirItem = new JMenuItem("Abrir");
        JMenuItem guardarItem = new JMenuItem("Guardar");
        JMenuItem guardarComoItem = new JMenuItem("Guardar como");
        JMenuItem cerrarItem = new JMenuItem("Cerrar");

        archivoMenu.add(nuevoItem);
        archivoMenu.add(abrirItem);
        archivoMenu.add(guardarItem);
        archivoMenu.add(guardarComoItem);
        archivoMenu.add(cerrarItem);

        JMenu edicionMenu = new JMenu("Edición");
        JMenuItem buscarItem = new JMenuItem("Buscar");
        JMenuItem reemplazarItem = new JMenuItem("Reemplazar");
        JMenuItem copiarItem = new JMenuItem("Copiar");
        JMenuItem cortarItem = new JMenuItem("Cortar");
        JMenuItem pegarItem = new JMenuItem("Pegar");

        edicionMenu.add(buscarItem);
        edicionMenu.add(reemplazarItem);
        edicionMenu.add(copiarItem);
        edicionMenu.add(cortarItem);
        edicionMenu.add(pegarItem);

        menuBar.add(archivoMenu);
        menuBar.add(edicionMenu);

        setJMenuBar(menuBar);

        nuevoItem.addActionListener(this);
        abrirItem.addActionListener(this);
        guardarItem.addActionListener(this);
        guardarComoItem.addActionListener(this);
        cerrarItem.addActionListener(this);

        buscarItem.addActionListener(this);
        reemplazarItem.addActionListener(this);
        copiarItem.addActionListener(this);
        cortarItem.addActionListener(this);
        pegarItem.addActionListener(this);

        fileChooser = new JFileChooser();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "Nuevo":
                areaTexto.setText("");
                nombreArchivo = null;
                break;

            case "Abrir":
                int opcion = fileChooser.showOpenDialog(this);
                if (opcion == JFileChooser.APPROVE_OPTION) {
                    File archivo = fileChooser.getSelectedFile();
                    nombreArchivo = archivo.getAbsolutePath();
                    try {
                        BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo));
                        areaTexto.setText("");
                        String linea;
                        while ((linea = lector.readLine()) != null) {
                            areaTexto.append(linea + "\n");
                        }
                        lector.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                break;

            case "Guardar":
                if (nombreArchivo != null) {
                    try {
                        BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivo));
                        escritor.write(areaTexto.getText());
                        escritor.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    guardarComo();
                }
                break;

            case "Guardar como":
                guardarComo();
                break;

            case "Cerrar":
                System.exit(0);
                break;

            case "Buscar":
                //aun no hay nada
                break;

            case "Reemplazar":
                // Aun no hay nada
                break;

            case "Copiar":
                areaTexto.copy();
                break;

            case "Cortar":
                areaTexto.cut();
                break;

            case "Pegar":
                areaTexto.paste();
                break;
        }
    }

    private void guardarComo() {
        int opcion = fileChooser.showSaveDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            nombreArchivo = archivo.getAbsolutePath();
            try {
                BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivo));
                escritor.write(areaTexto.getText());
                escritor.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EditorDeTexto();
        });
    }
}
