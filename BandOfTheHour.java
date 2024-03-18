package midtermProject;
import java.util.Scanner;

public class BandOfTheHour
{
    //initializing instance variables
    private int numberOfRows;
    private double[][] weights;
    private int[] rowSizes;
    private static final int MAX_NUMBER_OF_ROWS = 10;
    private static final int MAX_NUMBER_OF_POSITIONS = 8;

    private static Scanner input = new Scanner(System.in);

    //constructor
    public BandOfTheHour(int numberOfRows)
    {
        this.numberOfRows = numberOfRows;
        weights = new double[numberOfRows][MAX_NUMBER_OF_POSITIONS];
        rowSizes = new int[numberOfRows];

        for(int index = 0; index < numberOfRows; index++)
        {
            rowSizes[index] = 0;
        }

        setRowPositions();
    }// end of constructor taking one parameter

    private void setRowPositions()
    {
        //this method allows the user to choose the amount of positions per rows that they want
        //this is done based off the previous amount of rows the user inputted
        for (int index = 0; index < numberOfRows; index++)
        {
            System.out.print("Please enter number of positions in row " + (char) ('A' + index) + " : ");
            int positions;
            do
            {
                positions = input.nextInt();
                if (positions >= 1 && positions <= MAX_NUMBER_OF_POSITIONS)
                {
                    rowSizes[index] = positions;
                    break;
                }
                System.out.print("ERROR: Out of range, try again            : ");
            }while (positions < 1 || positions > 8);
        }
        System.out.println();
    }// end of setRowPositions method

    public void addMusician()
    {
        System.out.print("Please enter row letter                   : ");
        int rowIndex;
        int position;
        double weight;

        do
        {
            char row = Character.toUpperCase(input.next().charAt(0));
            rowIndex = row - 'A';
            if (rowIndex >= 0 && rowIndex < numberOfRows)
            {
                break;
            }
            System.out.print("ERROR: Out of range, try again            : ");
        }while (rowIndex < 0 || rowIndex >= numberOfRows);

        System.out.print("Please enter position number (1 to "+ rowSizes[rowIndex] + ")     : ");
        do
        {
            position = input.nextInt();
            // adjust the condition to allow adding at the end
            if(position >= 1 && position <= rowSizes[rowIndex] + 1)
            {
                break;
            }
            System.out.print("ERROR: Out of range, try again            : ");
        }while (position < 1 || position > rowSizes[rowIndex] + 1);

        if (position <= rowSizes[rowIndex] && weights[rowIndex][position - 1] != 0.0)
        {
            System.out.println("ERROR: There is already a musician there.");
            System.out.println();
            return;
        }

        System.out.print("Please enter weight (45.0 to 200.0)       : ");
        do
        {
            weight = input.nextDouble();
            if(weight >= 45.0 && weight <= 200.0)
            {
                break;
            }
            System.out.print("ERROR: Out of range, try again            : ");

        }while (weight < 45.0 || weight > 200.0);

        if (getTotalRowWeight(rowIndex) + weight > 100.0 * rowSizes[rowIndex])
        {
            System.out.println("ERROR: That would exceed the average weight limit.");
            System.out.println();
            return;
        }

        // Insert the musician at the specified position
        for (int index = rowSizes[rowIndex]; index > position - 1; index--)
        {
            weights[rowIndex][index] = weights[rowIndex][index - 1];
        }

        weights[rowIndex][position - 1] = weight;
        System.out.println("****** Musician added.");
        System.out.println();
    }// end of addMusician method



    public void removeMusician()
    {
        System.out.print("Please enter row letter                   : ");
        char row = Character.toUpperCase(input.next().charAt(0));
        int rowIndex = row - 'A';
        if (rowIndex < 0 || rowIndex >= numberOfRows) {
            System.out.print("ERROR: Out of range, try again            : ");
            return;
        }

        System.out.print("Please enter position number (1 to "+ rowSizes[rowIndex] + ")     : ");
        int position = input.nextInt();
        if (position < 1 || position > rowSizes[rowIndex]) {
            System.out.println("ERROR: Out of range, try again            : ");
            return;
        }

        if (weights[rowIndex][position - 1] == 0.0) {
            System.out.println("ERROR: That position is vacant.");
            System.out.println();
            return;
        }

        weights[rowIndex][position - 1] = 0.0;
        System.out.println("****** Musician removed.");
        System.out.println();
    }// end of removeMusician method

    public void printCurrentAssignment()
    {
        System.out.println();
        for (int i = 0; i < numberOfRows; i++)
        {
            System.out.printf("%-2s: ", (char) ('A' + i)); // Aligning the row letter to the left with a width of 2
            for (int j = 0; j < MAX_NUMBER_OF_POSITIONS; j++)
            {
                //printing out the positions for each row
                if (j < rowSizes[i])
                {
                    System.out.printf("%5.1f ", weights[i][j]);
                }
                else //for the rest of the positions the space is being filled so the left side will be lined up
                {
                    System.out.print("     ");
                }
            }
            //at the end of each row printing the total weight and row average
            //i cannot figure out hwo to make it line up nice :(((
            System.out.printf("  [ %-5.1f, %-7.1f ]\n", getTotalRowWeight(i), getAverageRowWeight(i));
        }
        System.out.println();
    }// end of printCurrentAssignment method

    private double getTotalRowWeight(int rowIndex) {
        double total = 0.0;
        for (int i = 0; i < rowSizes[rowIndex]; i++) {
            total += weights[rowIndex][i];
        }
        return total;
    }// end of getTotalRowWeight method

    private double getAverageRowWeight(int rowIndex) {
        if (rowSizes[rowIndex] == 0) {
            return 0.0;
        }
        return getTotalRowWeight(rowIndex) / rowSizes[rowIndex];
    }// end of getAverageRowWeight method

    public void displayMenu()
    {
        System.out.print("(A)dd, (R)emove, (P)rint,          e(X)it : ");
    }// end of displayMenu method

    public static void main(String[] args)
    {

        System.out.println("Welcome to the Band of the Hour");
        System.out.println("-------------------------------");

        int numberOfRows;
        System.out.print("Please enter number of rows               : ");
        do
        {
            numberOfRows = input.nextInt();
            if (numberOfRows >= 1 && numberOfRows <= MAX_NUMBER_OF_ROWS) {
                break;
            }
            System.out.print("ERROR: Out of range, try again            : ");
        }while (numberOfRows < 1 || numberOfRows > MAX_NUMBER_OF_ROWS);

        BandOfTheHour band = new BandOfTheHour(numberOfRows);

        char choice;
        do {
            band.displayMenu();
            choice = Character.toUpperCase(input.next().charAt(0));

            if (choice == 'A')
            {
                band.addMusician();
            }
            else if (choice == 'R')
            {
                band.removeMusician();
            }
            else if (choice == 'P')
            {
                band.printCurrentAssignment();
            }
            else if (choice == 'X')
            {
                System.out.println("Exiting program...");
            }
            else
            {
                System.out.print("ERROR: Invalid option, try again          : ");
                System.out.println();
            }
        } while (choice != 'X');
    }// end of main method

}// end of BandOfTheHour class
