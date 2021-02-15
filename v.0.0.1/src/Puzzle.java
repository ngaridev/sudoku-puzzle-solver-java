import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Arrays;

/***
 * This class makes puzzles using  Javax.Swing App
 * By using grid lines
 */

public class Puzzle {

    //App to make the sudoku puzzle
    //Use Javax.Swing

    public static class SudoKuPuzzle{
        private final String [][] puzzle; //Stores the puzzle outline in a 2D array
        private int sudokuSize;//Dimensions
        SudoKuPuzzle(int x_size){
            this.sudokuSize = x_size;
            this.puzzle = new String[x_size][x_size];
            fillPuzzle();
            System.out.println("Inside Puzzle __init__ method java");
            for( String[] nodeRow: puzzle)System.out.println(Arrays.toString(nodeRow));
        }
        public String[][] getPuzzle(){
            return puzzle;
        }
        public int getSudokuSize(){return this.sudokuSize;}
        private void fillPuzzle(){
            //We make the app
            JFrame App = new JFrame("Create Sudoku Puzzle");
            App.setSize(500,500);
            App.setVisible(true);
            App.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            //We will make 3 panels one for instructions, one for the boxes, last one for submit button
            JPanel instructionsPanel = new JPanel(), fillPanel = new JPanel(), submitPanel = new JPanel();
            JTextField [][] textFields =  new JTextField[sudokuSize][sudokuSize];
            JLabel[] labels = {
                    new JLabel("Fill in a valid sudoku puzzle"),
                    new JLabel("Rules are :"),
                    new JLabel("1. Digits should be numbers only"),
                    new JLabel("2. Digits should be less than one"),
            };
            for (JLabel lb : labels)instructionsPanel.add(lb);
            instructionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JButton submitButton = new JButton("Submit");//Submit the puzzle
            submitPanel.add(submitButton, BorderLayout.CENTER);
            //Add the textFields
            for(int i = 0; i < textFields.length; ++i)for(int j = 0; j < textFields[i].length; ++j)textFields[i][j] = new JTextField();//Add the fields to the array
            for(JTextField[] txt_row: textFields)for(JTextField txt_f: txt_row)fillPanel.add(txt_f); //Render the entries to the panel
            App.add(instructionsPanel);App.add(fillPanel);App.add(submitPanel);
            fillPanel.setLayout(new GridLayout(sudokuSize, sudokuSize));
            App.setLayout(new GridLayout(3, 1));

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(gridValid(textFields)){//If the arrangement is valid
                        //Update the puzzle variable
                        System.out.println("Inside fillPuzzle() method");
                        for( String[] nodeRow: puzzle)System.out.println(Arrays.toString(nodeRow));
                        updatePuzzle(textFields);
                        App.dispose();
                        //For the code to work we had to solve the puzzle form this method
                        Solver.Sys puzzleSystem = new Solver.Sys(puzzle);
                        puzzleSystem.printData();
                        //We save the created puzzle
                        FileSys saver = new FileSys();
                        try {
                            saver.savePuzzle(puzzleSystem);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        puzzleSystem.solvePuzzle();
                    }
                    else{
                        JOptionPane.showMessageDialog(App, "Invalid fill");
                    }
                }
            });
        }
        private boolean gridValid(JTextField[][] txtFields){
            //Check if each number is less than 10 and that each entry is a number if empty let it remain null
            for(JTextField[] txtRow : txtFields){
                for (JTextField txt : txtRow){
                    if(!valid(txt.getText())) return false;
                }
            }
            return true;
        }
        private boolean valid(String txt){
            if (txt.equals(""))return true;
            try{
                if(Integer.parseInt(txt) <= sudokuSize)return true;
            }catch (NumberFormatException e){
                e.getStackTrace();
            }
            return false;
        }
        private void updatePuzzle(JTextField[][] txtFields){
            for(int i = 0 ; i < txtFields.length ; i ++)
                for(int j = 0 ; j < txtFields[i].length ; j ++){
                    if(txtFields[i][j].getText().equals(""))this.puzzle[i][j] = "0";
                    else{
                        this.puzzle[i][j] = txtFields[i][j].getText();
                    }
                }
        }
    }
}
