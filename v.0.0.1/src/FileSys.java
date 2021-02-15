import javax.swing.*;
import java.io.*;
import java.util.Objects;

/***
 * This class stores puzzle objects to make debugging easier
 *
 */
public class FileSys implements java.io.Serializable {
    private static boolean checkPuzzle(String[][]arg){ //Change to check the puzzle validity..
        //No puzzle should be all zero's
        //All columns and rows should have at least one filled node
        for(String[] txtRow: arg)
            for(String txt: txtRow)
                if(!txt.equals("0"))return true;
        return false;
    }

    public void  savePuzzle(Solver.Sys workingPuzzle) throws IOException {
        File storageDir = new File("C:\\Users\\User\\Documents\\Sudoku solver\\Java\\Sample Arrays");
            //Save it
            String fileName = "C:\\Users\\User\\Documents\\Sudoku solver\\Java\\Sample Arrays\\file0000".concat(String.valueOf(Objects.requireNonNull(storageDir.list()).length + 1)).concat(".ser");
            try{
                new ObjectOutputStream(new FileOutputStream(fileName)).writeObject(workingPuzzle);
                System.out.println("Object saved successfully");
            }catch (FileNotFoundException e){
                System.out.println("IO error risen.Error saving file.File not saved");
            }
        //Check if it exists

    }
    public Solver.Sys extractPuzzle(String fileArg){
        Solver.Sys objectOne = null;
        try{
            // Reading the object from a file
            System.out.println("Reading file");//R
            File nf = new File(fileArg);
            File copy = nf;
            System.out.println(nf);
            FileInputStream fis = new FileInputStream(copy);
            System.out.println(fis);
            ObjectInputStream ois = new ObjectInputStream(fis);
            System.out.println(ois);
            // Method for deserialization of object
            objectOne = (Solver.Sys) ois.readObject();
            System.out.println(objectOne);
            System.out.println("Object has been deserialized ");//For programmer
            return objectOne;
        }catch (ClassNotFoundException cnf){
            System.out.println("Class not found");
            System.out.println(cnf.getMessage());
        }catch(FileNotFoundException ex) {
            System.out.println("File not found");//For debugging
            JOptionPane.showMessageDialog(null, "File was not found\nYou will need to make a new sudoku puzzle");
            //Prompt them to research or make another puzzle
            Puzzle.SudoKuPuzzle pz = new Puzzle.SudoKuPuzzle(Integer.parseInt(JOptionPane.showInputDialog(null, "Enter size of sudoku puzzle")));//Throw them to make another puzzle
        }catch (Exception exc){
            //Prompt them to research or make another puzzle
            exc.printStackTrace();//For debugging
            int yes = JOptionPane.showConfirmDialog(null, "Do you want to make a puzzle");
            if(yes==0){
                Puzzle.SudoKuPuzzle pz = new Puzzle.SudoKuPuzzle(Integer.parseInt(JOptionPane.showInputDialog(null, "Enter size of sudoku puzzle")));
            }
        }
        return objectOne;
    }
    public Solver.Sys extractPuzzle(File fileArg){
        Solver.Sys objectOne = null;
        try{
            System.out.println("Reading file");//R
            File copy = fileArg;
            System.out.println(copy);
            FileInputStream fis = new FileInputStream(copy);
            System.out.println(fis);
            ObjectInputStream ois = new ObjectInputStream(fis);
            System.out.println(ois);
            // Method for deserialization of object
            objectOne = (Solver.Sys) ois.readObject();
            System.out.println(objectOne);
            System.out.println("Object has been deserialized ");//For programmer
            return objectOne;
        }catch (ClassNotFoundException cnf){
            System.out.println("Class not found");
            System.out.println(cnf.getMessage());
        }
        catch(FileNotFoundException ex) {
            System.out.println("File not found");//For debugging
            JOptionPane.showMessageDialog(null, "File was not found\nYou will need to make a new sudoku puzzle");
            //Prompt them to research or make another puzzle
            Puzzle.SudoKuPuzzle pz = new Puzzle.SudoKuPuzzle(Integer.parseInt(JOptionPane.showInputDialog(null, "Enter size of sudoku puzzle")));//Throw them to make another puzzle
        } catch (Exception exc){
            //Prompt them to research or make another puzzle
            exc.printStackTrace();//For debugging
            int yes = JOptionPane.showConfirmDialog(null, "Do you want to make a puzzle");
            if(yes==0){
                Puzzle.SudoKuPuzzle pz = new Puzzle.SudoKuPuzzle(Integer.parseInt(JOptionPane.showInputDialog(null, "Enter size of sudoku puzzle")));
            }

        }
        System.out.println(objectOne);
        return objectOne;
    }
}
