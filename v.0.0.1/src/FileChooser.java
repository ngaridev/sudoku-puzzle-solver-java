import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;

public class FileChooser {

    private File chosenFile;
    private String filePath;
    FileChooser(){
        JMenu fileOption = new JMenu("File");
        JMenuBar menuBar = new JMenuBar();
        JFrame App = new JFrame();
        JMenuItem open = new JMenuItem("Open");
        App.setSize(500,500);
        App.setVisible(true);
        App.setLayout(null);
        App.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {App.dispose();}
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        menuBar.setBounds(0, 0, 500, 20);
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()== open){
                    JFileChooser fc = new JFileChooser();
                    int i = fc.showOpenDialog(fc);
                    if(i==JFileChooser.APPROVE_OPTION){
                        chosenFile = fc.getSelectedFile();
                        filePath = chosenFile.getPath();
                        if(chosenFile!=null)JOptionPane.showMessageDialog(App, "File chosen successfully");
                        else JOptionPane.showMessageDialog(App, "File not chosen\nTry again later");
                    }
                }
                App.dispose();//For the code to work we will run the file from here
                FileSys slv = new FileSys();
                Solver.Sys exroot = slv.extractPuzzle(chosenFile.getPath());
                Solver.Sys root = new Solver.Sys(exroot.getFinalData());
                System.out.println(chosenFile);
                System.out.println(root);
                try{
                    root.printData();
                    root.solvePuzzle();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        menuBar.add(fileOption);fileOption.add(open);
        App.add(menuBar);
    }
    public File getChosenFile(){return this.chosenFile;}
    public String getFilePath(){return this.filePath;}
    /***
     *
     *     public static void main(String[] args) {
     *         FileSys slv = new FileSys();
     *         Solver.Sys root = slv.extractPuzzle(new File("C:\\Users\\User\\Documents\\Sudoku solver\\Java\\Sample Arrays\\file00003.ser"));
     *         System.out.println(root);
     *         try{
     *             root.printData();
     *             root.solvePuzzle();
     *         }catch (Exception ex){
     *             ex.printStackTrace();
     *         }
     *     }
     */
}
