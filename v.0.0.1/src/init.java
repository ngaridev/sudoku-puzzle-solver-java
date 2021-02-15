import javax.swing.*;

public class init {
    public static void main(String[] args) {
        if(JOptionPane.showConfirmDialog(null,"Do you want to make your own puzzle")==0){
            new Puzzle.SudoKuPuzzle(Integer.parseInt(JOptionPane.showInputDialog(null, "Enter size of sudoku puzzle")));
        }else{
            new FileChooser();
        }
    }
}
