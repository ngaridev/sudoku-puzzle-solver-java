import java.io.Serializable;
import java.util.*;

public class Solver {

    //Solves the actual maze
    public static class Node implements Serializable{
        //Individual brain
        private int finalValue = 0;
        private LinkedList<Integer> possibilities = new LinkedList<>();
        Node(int val){
            this.finalValue = val;
        }
        public void changeValue(int n){
            this.finalValue = n;
        }
        public int getValue(){
            return this.finalValue;
        }
        public Object[] getPossibilities(){
            return this.possibilities.toArray();
        }
        public boolean possEmpty(){
            for(int q: this.possibilities){
                if(q!=0)return false;
            }
            return true;
        }
        public int changeFinalValue(LinkedList<Integer> arr) {
            //Check if the arr has any other number apart from zero
            if(arr.size()==1)return arr.get(0);
            else return 0;
        }
        private void localeCheck(){

        }
        private void  updatePossibilities(int[] row, int[] col){
           //Checks if for numbers not in the row and columns
            //Check also for local 3 X 3
            this.possibilities.clear();
            LinkedList<Integer> rowLL = new LinkedList<>(), colLL = new LinkedList<>();
            //Add values
            for( int i = 0; i < row.length; ++i){
                rowLL.add(row[i]);
                colLL.add(col[i]);
            }
            System.out.println("Current position is "+ Arrays.toString(getPosition(this)));
            System.out.println("Elements on row:" + Arrays.toString(row));
            System.out.println("Elements on column:" + Arrays.toString(col));
            System.out.println("Elements on locale:" + getLocale(getPosition(this)));
            //get elements in locale using its position

            for( int i = 1; i <= 9; ++i){
                if(!(rowLL.contains(i) || colLL.contains(i) || getLocale(getPosition(this)).contains(i)))
                    this.possibilities.add(i);
            }
            System.out.println(this.possibilities);
            //Check if the possibilities are only zero and a number x  so that final value can be x
            this.finalValue = changeFinalValue(this.possibilities);
            System.out.println("FInal value changed to " + this.finalValue);
        }

    }

    public static class Sys implements Serializable {
        //Translates string data in the puzzle made and solves it
        //Does the recursive math over and over
        //Returns a final solved puzzle in a JFrame App

        private static Node[][] finalData;
        private static Node[][] localeData;

        Sys(String[][] finalData) {
            Sys.finalData = new Node[finalData.length][finalData[0].length];
            Sys.localeData = new Node[finalData.length][finalData[0].length];
            convertData(finalData);
            formLocaleData();
        }
        Sys(Node[][] fd) {
            finalData = fd;
        }
        public Node[][] getFinalData(){return finalData;}
        //Step One
        private void convertData(String[][] data){
            System.out.println("Inside Solver.java");
            for( String[] nodeRow: data)System.out.println(Arrays.toString(nodeRow));
            for( int i = 0; i < data.length; i++)
                for(int j = 0; j < data[i].length; j++) Sys.finalData[i][j] = new Node(Integer.parseInt(data[i][j]));
        }

        private static void formLocaleData(){
            int xValue = 0, yValue = 0;
            for (int k = 3; k > 0; --k){
                for(int i = yValue; i < yValue + 3; ++i){
                    // xValue runs to 3
                    for(int j = xValue; j < xValue + 3; ++j){
                        Sys.localeData[i][j] = Sys.finalData[xValue][yValue];
                    }
                }
                xValue+=3;
            }
        }
        public void solvePuzzle(){
            while(!checkPuzzleDone()){
                for(int i = 0; i < Sys.finalData.length; ++i){
                    for(int j = 0; j < Sys.finalData[i].length;++j){
                        if(finalData[i][j].finalValue == 0)finalData[i][j].updatePossibilities(makeRow(Sys.finalData[i]), makeCol(Sys.finalData, j));
                    }
                }
                printData();
            }
        }

        private boolean checkPuzzleDone() {
            //Check is complete if all the nodes have a final value that  is not zero
            for (Node[] na : Sys.finalData) {
                for (Node nd : na) {
                    if ((nd.getValue() == 0)) {
                        return false;
                    }
                }
            }
            return true;
        }
        public void printData(){
            for( Node[] nodeRow: Sys.finalData){
                for (Node q : nodeRow) {
                    System.out.print(q.getValue() + ", ");
                }
                System.out.println();
            }
        }
        public void printLocaleData(){
            for( Node[] nodeRow: Sys.localeData){
                for (Node q : nodeRow) {
                    System.out.print(q.getValue() + ", ");
                }
                System.out.println();
            }
        }
    }
    private static int[] makeRow(Node[] arr){
        int[] returnArray = new int[arr.length];
        int c = 0;
        for(Node i: arr)
            returnArray[c++] = i.getValue();
        return returnArray;
    }
    private static int[] makeCol(Node[][] na, int n){
        int[] returnArray = new int[9];
        for(int i = 0; i < 9; ++i)
            returnArray[i] = na[i][n].getValue();
        return returnArray;
    }
    private static int[] getPosition(Node nd){
        int [] ra = new int[2]; // Return array
        for( int i = 0; i < Sys.finalData.length; ++i){
            for(int j = 0; j < Sys.finalData[i].length; ++j){
                if(nd == Sys.finalData[i][j]){
                    ra[0] = i;//X value
                    ra[1] = j;//Y value
                }
            }
        }
        return ra;
    }
    private static LinkedList<Integer> getLocale(int[] arr){
        LinkedList<Integer> rl = new LinkedList<>();//return list
        //X value
        int cx = (int) ((arr[0])/3) * 3;
        int cy = (int) ((arr[1])/3) * 3;
        for(int i = cx ; i < cx + 3; ++i){
            for(int j = cy; j < cy + 3; ++j){
                rl.add(Sys.finalData[i][j].finalValue);
            }
        }
        return rl;
    }
    private static void printPossibilities(Node nd){ //For debugging
        System.out.println(nd.possibilities);
    }
}