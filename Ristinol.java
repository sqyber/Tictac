import java.util.Random;
import java.util.Scanner;


public class Ristinol {
    //Define variables
    String winner;
    int turnCount = 0;
    int rowCoord = 0;
    int columnCoord = 0;

    public static void main(String[] args) {
        Ristinol game = new Ristinol();
        Scanner input = new Scanner(System.in);


        // make sure board is the correct size
        System.out.println("Give the size of the board (3-12)");
        int size = input.nextInt();
        boolean sizecheck = false;
        while (!sizecheck) {
            if (size < 3) {
                System.out.println("Too small, the size must be between 3 and 12. Try again");
                size = input.nextInt();
            } else if (size > 12) {
                System.out.println("Too large, the size must be between 3 and 12. Try again");
                size = input.nextInt();
            } else sizecheck = true;
        }

        //call objects

        System.out.println();
        Board tictac = new Board(size);
        tictac.printBoard();
        System.out.println(3 + (size / 6) + " in a line needed for a win");
        System.out.println("Coordinates start from the top eg. top row is row 1 and leftmost column is column 1");
        System.out.println("Player X goes first. Enter");
        game.gamePlay(tictac);

    }

    //The main game loop
    public void gamePlay(Board tictac) {
        Scanner input = new Scanner(System.in);
        Random ran = new Random();

        int x = 0;
        int y = 0;
        while (winner == null) {
            char playerChar = playTurn();
            System.out.println(playerChar + "'s Turn");
            boolean validCoords = true;
            while(validCoords){
                boolean loop = true;
                //input for x coordinate
                while (loop) {
                    if (playerChar == 'X') {
                        System.out.println("Select row");
                        rowCoord = input.nextInt() - 1;
                        if (rowCoord < 0 || rowCoord > tictac.getSize()-1) {
                            System.out.println("Invalid coordinate. Try again");
                        } else {

                            loop = false;
                        }
                        //ai player
                    } else {
                        rowCoord = ran.nextInt(tictac.getSize());
                        loop = false;
                    }
                }

                loop = true;
                //input for y coordinate
                while (loop) {
                    if (playerChar == 'X') {
                        System.out.println("Select column");
                        columnCoord = input.nextInt() - 1;
                        if (columnCoord < 0 || columnCoord > tictac.getSize()-1) {
                            System.out.println("Invalid coordinate. Try again");
                        } else {
                            loop = false;
                        }
                        //ai player
                    } else {
                        columnCoord = ran.nextInt(tictac.getSize());
                        loop = false;
                    }
                }
                validCoords = tictac.boardCheck(rowCoord,columnCoord);

            }

            x = rowCoord;
            y = columnCoord;
            turnCount++;
            System.out.println();
            tictac.changeBoard(x, y, playerChar);
            tictac.printBoard();
            if(tictac.winState(playerChar, turnCount)){
                winner = "over";
            }
        }
    }


    // method to keep track of the current players turn
    public char playTurn() {
        {
            if (turnCount == 0 || turnCount % 2 == 0)
                return 'X';
            else
                //required to be 'O' for ai to work
                return 'O';
        }
    }

}
// class for the board creation, printing and editing
class Board {
    private char gameBoard[][];
    private int size;

    //constructor
    public Board(int size) {
        this.size = size;
        this.gameBoard = generateBoard(size);

    }

    //getter
    public int getSize() {
        return size;
    }

    //print the array board
    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(" " + gameBoard[i][j]);
            }
            System.out.println();
        }
    }

    // generate the board for the printBoard
    static char[][] generateBoard(int size) {
        char gameBoard[][] = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameBoard[i][j] = '.';
            }
        }
        return gameBoard;
    }

    //changes gameboard array
    public void changeBoard(int x, int y, char playerChar) {
        gameBoard[x][y] = playerChar;
    }

    //Wrong input check
    public boolean boardCheck(int rowCoord, int columnCoord){
        if (gameBoard[rowCoord][columnCoord] == '.'){
            return false;
        }else {
            return true;
        }
    }

    //winchecks
    public boolean winState(char playerChar, int turncount) {

        // number of connected marks to win
        int winreq = 3 + (size / 6);

        //wincheck loop, one if statement for each direction
        for (int rowCoord = 0; rowCoord < size; rowCoord++) {
            for (int columnCoord = 0; columnCoord < size; columnCoord++) {
                if (gameBoard[rowCoord][columnCoord] == playerChar) {

                    //rows to right
                    if ((columnCoord + winreq) <= this.size) {
                        if (gameBoard[rowCoord][columnCoord + 1] == playerChar) {
                            if (gameBoard[rowCoord][columnCoord + 2] == playerChar) {
                                if (winreq == 3) {
                                    System.out.println(playerChar + "wins with three in row!");
                                    return true;
                                } else {
                                    if (gameBoard[rowCoord][columnCoord + 3] == playerChar) {
                                        if (winreq == 4) {
                                            System.out.println(playerChar + "wins with four in row!");
                                            return true;
                                        } else {
                                            if (gameBoard[rowCoord][columnCoord + 4] == playerChar) {
                                                System.out.println(playerChar + "wins with five in row!");
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    //rows to left
                    if (columnCoord >= winreq) {
                        if (gameBoard[rowCoord][columnCoord - 1] == playerChar) {
                            if (gameBoard[rowCoord][columnCoord - 2] == playerChar) {
                                if (winreq == 3) {
                                    System.out.println(playerChar + " wins with three in row!");
                                    return true;
                                } else {
                                    if (gameBoard[rowCoord][columnCoord - 3] == playerChar) {
                                        if (winreq == 4) {
                                            System.out.println(playerChar + " wins with four in row!");
                                            return true;
                                        } else {
                                            if (gameBoard[rowCoord][columnCoord - 4] == playerChar) {
                                                System.out.println(playerChar + " wins with five in row!");
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    //columns to down
                    if (rowCoord >= winreq) {
                        if (gameBoard[rowCoord - 1][columnCoord] == playerChar) {
                            if (gameBoard[rowCoord - 2][columnCoord] == playerChar) {
                                if (winreq == 3) {
                                    System.out.println(playerChar + " wins with three in column!");
                                    return true;
                                } else {
                                    if (gameBoard[rowCoord - 3][columnCoord] == playerChar) {
                                        if (winreq == 4) {
                                            System.out.println(playerChar + " wins with four in column!");
                                            return true;
                                        } else {
                                            if (gameBoard[rowCoord - 4][columnCoord] == playerChar) {
                                                System.out.println(playerChar + " wins with five in column!");
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //columns to up
                    if (rowCoord + winreq <= this.size) {
                        if (gameBoard[rowCoord + 1][columnCoord] == playerChar) {
                            if (gameBoard[rowCoord + 2][columnCoord] == playerChar) {
                                if (winreq == 3) {
                                    System.out.println(playerChar + " wins with three in column!");
                                    return true;
                                } else {
                                    if (gameBoard[rowCoord + 3][columnCoord] == playerChar) {
                                        if (winreq == 4) {
                                            System.out.println(playerChar + " wins with four in column!");
                                            return true;
                                        } else {
                                            if (gameBoard[rowCoord + 4][columnCoord] == playerChar) {
                                                System.out.println(playerChar + " wins with five in column!");
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //diagonal to north east
                    if (columnCoord + winreq <= this.size && rowCoord + winreq <= this.size) {
                        if (gameBoard[rowCoord + 1][columnCoord + 1] == playerChar) {
                            if (gameBoard[rowCoord + 2][columnCoord + 2] == playerChar) {
                                if (winreq == 3) {
                                    System.out.println(playerChar + " wins with three in diagonal!");
                                    return true;
                                } else {
                                    if (gameBoard[rowCoord + 3][columnCoord + 3] == playerChar) {
                                        if (winreq == 4) {
                                            System.out.println(playerChar + " wins with four in diagonal!");
                                            return true;
                                        } else {
                                            if (gameBoard[rowCoord + 4][columnCoord + 4] == playerChar) {
                                                System.out.println(playerChar + " wins with five in diagonal!");
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //diagonal to south west
                    if (rowCoord >= winreq && columnCoord >= winreq) {
                        if (gameBoard[rowCoord - 1][columnCoord - 1] == playerChar) {
                            if (gameBoard[rowCoord - 2][columnCoord - 2] == playerChar) {
                                if (winreq == 3) {
                                    System.out.println(playerChar + " wins with three in diagonal!");
                                    return true;
                                } else {
                                    if (gameBoard[rowCoord - 3][columnCoord - 3] == playerChar) {
                                        if (winreq == 4) {
                                            System.out.println(playerChar + " wins with four in diagonal!");
                                            return true;
                                        } else {
                                            if (gameBoard[rowCoord - 4][columnCoord - 4] == playerChar) {
                                                System.out.println(playerChar + " wins with five in diagonal!");
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //diagonal to north west
                    if (columnCoord >= winreq && rowCoord + winreq <= this.size) {
                        if (gameBoard[rowCoord + 1][columnCoord - 1] == playerChar) {
                            if (gameBoard[rowCoord + 2][columnCoord - 2] == playerChar) {
                                if (winreq == 3) {
                                    System.out.println(playerChar + " wins with three in diagonal!");
                                    return true;
                                } else {
                                    if (gameBoard[rowCoord + 3][columnCoord - 3] == playerChar) {
                                        if (winreq == 4) {
                                            System.out.println(playerChar + " wins with four in diagonal!");
                                            return true;
                                        } else {
                                            if (gameBoard[rowCoord + 4][columnCoord - 4] == playerChar) {
                                                System.out.println(playerChar + " wins with five in diagonal!");
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //diagonal to south east
                    if (rowCoord + winreq <= this.size && columnCoord >= winreq) {
                        if (gameBoard[rowCoord + 1][columnCoord - 1] == playerChar) {
                            if (gameBoard[rowCoord + 2][columnCoord - 2] == playerChar) {
                                if (winreq == 3) {
                                    System.out.println(playerChar + " wins with three in diagonal!");
                                    return true;
                                } else {
                                    if (gameBoard[rowCoord + 3][columnCoord - 3] == playerChar) {
                                        if (winreq == 4) {
                                            System.out.println(playerChar + " wins with four in diagonal!");
                                            return true;
                                        } else {
                                            if (gameBoard[rowCoord - 4][columnCoord - 4] == playerChar) {
                                                System.out.println(playerChar + " wins with five in diagonal!");
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //Draw if the board fills up
        if(turncount == Math.pow(this.size, 2)){
            System.out.println(" It's a draw!");
            return true;
        }
        return false;
    }

}


