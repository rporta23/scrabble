/** File: PC_PR_Board.java (Rose Porta and Catherine Park)
 Scrabble Game Version 0
 
Play another round? (y/n) y
Catherine's Turn! Don't Peek!
Catherine's Letters: [Z-10, Q-10, G-2, J-8, X-8, E-1, D-2]
1.Play
2.Exchange Letter
3.Pass
Option? 
1
Catherine's Letters: [Z-10, Q-10, G-2, J-8, X-8, E-1, D-2]
Please enter letter: X
Enter where you want the letter (Row Column) 7 14
    0  1  2  3  4  5  6  7  8  9 10 11 12 13 14
  _______________________________________________
0 |# |  |  |  |  |  |  |# |  |  |  |  |  |  |# |
  _______________________________________________
1 |  |^ |  |  |  |  |  |  |  |  |  |  |  |^ |  |
  _______________________________________________
2 |  |  |^ |  |  |  |  |B |  |  |  |  |^ |  |  |
  _______________________________________________
3 |  |  |  |^ |  |  |W |A |Y |  |  |^ |  |  |  |
  _______________________________________________
4 |  |  |  |  |H |  |  |~ |  |  |^ |  |  |  |  |
  _______________________________________________
5 |  |  |  |W |~ |M |A |N |  |^ |  |  |  |  |  |
  _______________________________________________
6 |  |  |  |  |P |  |^ |E |^ |V |  |Y |U |P |  |
  _______________________________________________
7 |  |  |  |  |  |  |  |D |O |E |  |U |  |E |X |
  _______________________________________________
8 |  |  |  |  |  |  |^ |  |^ |T |O |M |  |E |  |
  _______________________________________________
9 |  |  |  |  |  |^ |  |  |  |^ |K |  |  |L |  |
  _______________________________________________
10 |  |  |  |  |^ |  |  |  |  |  |^ |  |  |  |  |
  _______________________________________________
11 |  |  |  |^ |  |  |  |  |  |  |  |^ |  |  |  |
  _______________________________________________
12 |  |  |^ |  |  |  |  |  |  |  |  |  |^ |  |  |
  _______________________________________________
13 |  |^ |  |  |  |  |  |  |  |  |  |  |  |^ |  |
  _______________________________________________
14 |# |  |  |  |  |  |  |# |  |  |  |  |  |  |# |
  _______________________________________________
Enter another letter (y/n)? n

 */
 
 import java.util.*;
 import java.io.*;

 class PC_PR_Board {
    static Random r = new Random(); 
    static Scanner cin = new Scanner(System.in);
    static int totalBag = 100;//Total amount of letters
  
    public static void main(String [] args) {
        char opt;
        do{
            System.out.print("1. Author\n2. Help\n3. Play\n0. Exit\nOption? "); // menu
            opt = cin.next().charAt(0);
            switch(opt){
                case '1': System.out.println("Rose Porta & Catherine Park"); break; // authors
                case '2': readFile2(); break; // read and print rules from PC_PR_rules.txt
                case '3': playGame(); break; // game
                case '0': System.out.print("Goodbye!"); break; // exit
                default: System.out.println("Bad Input!");
                
            }//switch
        } while(opt != '0');
     }//main

     //Function for adding players
     static void createPlayers(ArrayList <Player> Players) {
       char c;
       do { //Continue until players say stop adding
        System.out.print("Enter name: ");
        String name = cin.next();
        Player p = new Player(name); //create new player
        Players.add(p); //add player to ArrayList of players
        System.out.print("Add another player? (y/n) "); //Asks user if should add another player
        c = cin.next().charAt(0);
       }while(c != 'n');
     }//createPlayers

     static void playGame() { //Plays entire game
        totalBag = 100; //Resets number of letters for each new game
        ArrayList <Letter> Letters = new ArrayList <> (); //List to Keep Track of Letters in Bag (Separate from Player Letters)
        ArrayList <Player> Players = new ArrayList <> (); //List of All Players
        readFile(Letters); //Reads file for points and number of letters
        createPlayers(Players); //Calls function to create players
        char c = ' ';
        //Creates empty 15x15 matrix
        char [] [] sboard = new char [15] [15];
        //Fill board with spaces for display
        fillBoard(sboard);
       
        //Prints scrabble board
        printM(sboard);

        Collections.shuffle(Players); //Have players play in random order
        //Playing a round
        do{//Loop until user exits
          for (Player p : Players) { //iterates through all players in player ArrayList
            fillLetters(Letters, p); //Fills each player with random letters
            if (p.letters.size() == 0) { //Checking to see if any of the players has 0 letters
              System.out.println("Game over!"); //Game over if one has 0 letters
              printWinners(Players);
              System.out.println();
              return; //returns
              }//if
            System.out.println(p.name + "'s Turn! Don't Peek!");
            roundOptions(Letters, sboard, p, Players);//Player p's Turn/Round
          }//for
           
           System.out.print("Play another round? (y/n) ");
           c = cin.next().charAt(0); //Takes user input for continuing
        } while (c != 'n');

        System.out.println("Game over!"); //Game over if round ends
          printWinners(Players);
          System.out.println();
     }//playGame()

     static void printWinners (ArrayList <Player> Players) { //Print winners in order of highest to lowest score
        Collections.sort(Players, Player.totalComp);
        System.out.println("Final Rankings!");
        for (int i = Players.size() - 1; i >= 0; i--) {
          System.out.println(Players.get(i));
        }//for
     }//printWinners

     static void roundOptions(ArrayList <Letter> Letters, char [][] sboard, Player player, ArrayList <Player> Players){
      // options for round-- play, exchange, or pass
      System.out.println(player.name + "'s Letters: " + player.letters); //Show user their letters
      System.out.println("1.Play\n2.Exchange Letter\n3.Pass\nOption? "); // menu
      char op = cin.next().charAt(0);
      switch(op){
        case '1': playRound(Letters, sboard, player, Players); break; // play round as normal
        case '2': { // exchange letter and skip round
          exchangeLetter(player, Letters); 
          player.roundScores.add(0); 
          writeFile(Players);
          break;
        }
        case '3': {player.roundScores.add(0); writeFile(Players); break;} // add score of 0 for round and skip to next turn
        default: System.out.println("Invalid input");
      }
     } //roundOptions

     static void exchangeLetter(Player player, ArrayList <Letter> Letters){ // exchange letter
      char l; // value of letter to exchange
       do{
        System.out.println("Which letter would you like to exchange? "); 
        l = cin.next().charAt(0);
       }while(checkLetter(l, player) == -1);
       player.letters.remove(checkLetter(l, player)); // remove letter from player's letters
       for(Letter x : Letters){ // put exchanged letter back in "bag"
         if(x.value == l) x.num++;
       }
       fillLetters(Letters, player); // replace with new letter
     } //exchangeLetter


     static void fillBoard(char [] [] m) { //Fill board with spaces
        for (char [] r : m) {
            for(int i = 0; i < 15; i++){
                r[i] = ' ';
            }//for
        }//for

        // starting position in center
        m[7][7] = '*'; 

        // triple word
        m[0][0] = '#'; 
        m[0][7] = '#';
        m[0][14] = '#';
        m[14][0] = '#';
        m[14][7] = '#';
        m[14][14] = '#';

        // double word on diagonals
        for(int i = 0; i < m.length; i++){ 
          if(m[i][i] == ' ') m[i][i] = '^'; // main diagonal
          
        }
        for(int i = 0; i < m.length; i++){ // second diagonal
          for(int j = m.length-1; j>=0; j--){
            if(i == (m.length -1-j) && (m[j][i] == ' ')) m[j][i] = '^';
          }   
        }
    }//fillBoard
    
     static void printM(char [] [] m) {//Prints Entire Matrix and Formats to Look like Board
         System.out.println("    0  1  2  3  4  5  6  7  8  9 10 11 12 13 14");
         System.out.println("  _______________________________________________");
         for (int j = 0; j < 15; j++) {
             System.out.print(j + " |");
             for(int i = 0; i < 15; i++) {
                System.out.print(m[j][i] + " |"); 
             }//inner for
             System.out.println();
             System.out.println("  _______________________________________________");
         }//outer for
     }//printM
 
     //Function for user to put word on the board
     static void playRound(ArrayList <Letter> Letters, char [] [] sboard, Player player, ArrayList <Player> Players) {
        char done;
        char value;
        int roundScore = 0;
        boolean doubleWord = false;
        boolean tripleWord = false;
        do { // loop until user is done placing letters
            System.out.println(player.name + "'s Letters: " + player.letters); //Show user their letters
            System.out.print("Please enter letter: "); //Ask user to enter letter of word
            do {  //loop until user enters valid letter
              value = cin.next().charAt(0); //Store the letter as variable letter   
              if (checkLetter(value, player) == -1) {
                System.out.println("Invalid Letter. Try again!"); //checks to see if letter is in player's letter bag
              }//if
            } while(checkLetter(value, player) == -1);
            
            int row;
            int column;
            do{ // loop until user chooses legal placement for letter (next to another letter)
              System.out.print("Enter where you want the letter (Row Column) "); //Ask user to specify location on board for letter
              row = cin.nextInt();
              column = cin.nextInt();
              if(!checkLocation(sboard,row,column)) System.out.println("Illegal placement, try again");
            }while(!checkLocation(sboard, row, column));

            if(sboard[row][column] == '#') tripleWord = true; // check if position has double or triple word
            if(sboard[row][column] == '^') doubleWord = true;
            sboard[row][column] = value; //Put letter on board
            
            int i = checkLetter(value, player);
            Letter current = player.letters.get(i); // current letter for player to put on board
            player.letters.remove(i); //removes used letter from player's letters ArrayList
            roundScore += current.pts; // add value of current letter to roundscore
            printM(sboard); //Prints board with letters

            System.out.print("Enter another letter (y/n)? "); 
            done = cin.next().charAt(0);

         } while(done != 'n' && player.letters.size() > 0);
         
         if(tripleWord) roundScore = roundScore*3; // multiply score if triple or double word
         if(doubleWord) roundScore = roundScore*2;
         player.roundScores.add(roundScore); // add roundscore to list of roundscores for player
         player.totalScore += roundScore; // increment total score
         fillLetters(Letters, player); //Refill up to 7 letters for player
         writeFile(Players);
         System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
     }//playRound()

     // tried to check for letters already on board connected to inputted letters to count those also, but became too complex
     static int addPoints(int row, int column, char [][] sboard, ArrayList <Letter> Letters){
      int roundScore = 0;
      int r = row;
      int c = column;
      while(Character.isLetter(sboard[r][c]) && Character.isLetter(sboard[r + 1][c]) && (r < 15) && (r > -1) && (c < 15) && (c > -1)){ // going forward
        for(Letter x : Letters){ 
          if(x.value == sboard[r][c]) roundScore += x.pts;
        }
        r++;
      }
      while(Character.isLetter(sboard[r][c]) && Character.isLetter(sboard[r -1][c]) && (r < 15) && (r > -1) && (c < 15) && (c > -1)){ // going backwards
        for(Letter x : Letters){ 
          if(x.value == sboard[r][c]) roundScore += x.pts;
        }
        r--;
      }
      while(Character.isLetter(sboard[r][c]) && Character.isLetter(sboard[r][c + 1]) && (r < 15) && (r > -1) && (c < 15) && (c > -1)){ // going down
        for(Letter x : Letters){ 
          if(x.value == sboard[r][c]) roundScore += x.pts;
        }
        c++;
      }

      while(Character.isLetter(sboard[r][c]) && Character.isLetter(sboard[r][c - 1]) && (r < 15) && (r > -1) && (c < 15) && (c > -1)){ // going up
        for(Letter x : Letters){ 
          if(x.value == sboard[r][c]) roundScore += x.pts;
        }
        c--;
      }
      return roundScore;
     } //addPoints

     static boolean checkLocation(char [][] sboard, int row, int column){ // check to make sure player places letter in legal position (next to another letter)
       if(Character.isLetter(sboard[row][column]) || sboard[row][column] == '~') return false; // don't let player overwrite another letter on the board
       if(((row == 7) && (column == 7)) && !Character.isLetter(sboard[row][column]) || sboard[row][column] == '~') return true; // case for starting position in center
       if(Character.isLetter(sboard[row -1][column]) || sboard[row - 1][column] == '~') return true; // make sure placement is next to another letter or a blank ('~')
       if(Character.isLetter(sboard[row + 1][column]) || sboard[row + 1][column] == '~') return true;
       if(Character.isLetter(sboard[row][column - 1]) || sboard[row][column - 1] == '~') return true;
       if(Character.isLetter(sboard[row][column + 1]) || sboard[row][column + 1] == '~') return true;
       return false;
     } // checkLocation

     //Checks if inputted letter exists in player's letters ArrayList
     static int checkLetter(char value, Player player) {
       for (int i = 0; i < player.letters.size(); i++) {
         if (player.letters.get(i).value == value) return i; //returns index of letter
       }//for
       return -1; //returns -1 if letter is not in arraylist
     }//checkLetter()

     //Taken from Homework 10 (File: PC_PR_PlayLL.java)
     static void readFile(ArrayList <Letter> Letters){ // read in letters from PC_PR_letters.txt and add to list
      Scanner fin = null; // scanner to read file
          String filename = "PC_PR_letters.txt";
          try{fin = new Scanner(new File(filename));}
          catch(IOException ex) {System.out.print(ex);}
          while(fin.hasNextLine()){ // read file line by line
              String n = fin.nextLine();
              String [] A = n.split(","); // split line by commas
        char value = A[0].charAt(0);
        int pts = Integer.parseInt(A[1]); // convert pts to int
        int num = Integer.parseInt(A[2]); // convert num to int
        Letter a = new Letter(value, pts, num); // create new letter
        Letters.add(a); // add to list of letters
          }
      fin.close(); // close file
    }//readFile()

    //Taken from Homework 10 (File: PC_PR_PlayLL.java)
    static void readFile2(){ // read rules from PC_PR_rules.txt and print to screen
      Scanner fin = null; // scanner to read file
          String filename = "PC_PR_rules.txt";
          try{fin = new Scanner(new File(filename));}
          catch(IOException ex) {System.out.print(ex);}
          while(fin.hasNextLine()){ // read file line by line and print to screen
              String n = fin.nextLine();
              System.out.println(n);
          }
      fin.close(); // close file
    }//readFile2()

    // from homework 10
    static void writeFile(ArrayList <Player> P){ // write player scores to scoresheet after each round
      PrintWriter fout = null;
      String filename = "PC_PR_scoresheet.txt"; // file for scoresheet
      try{fout = new PrintWriter(new File(filename));}
      catch(IOException ex) {System.out.print(ex);}
      fout.write("Name    "); // heading
      for(int i = 0; i < P.get(0).roundScores.size(); i++){
        fout.write("Round " + (i + 1) + "  ");
      }
      fout.write("Total");
      fout.write("\n");
      for(Player p : P){ // writes scores to scoresheet for each player
          fout.write(p.name + "\t");
          for(int x : p.roundScores){ // round scores
            fout.write(x + "\t\t");
          }
          fout.write(p.totalScore + "\n"); // total scores
        }
      fout.close();
    } //writeFile

    static void fillLetters(ArrayList <Letter> Letters, Player p) { //Replacing Used Letters with New Letters after each turn
      int index;
      Letter L; 
      if (totalBag > 0) {
        for(int i = p.letters.size(); i < 7; i++) { //Only replace number of letters that user used
          do {
            index = r.nextInt(27); //randomly replace with letters from A to Z or blank ' '
            L = Letters.get(index);   
          } while (L.num == 0); //Check to make sure there are some left of that letter (if not generate a different one)
          p.letters.add(L); //Add randomly generated letter to players arraylist of letters
          L.num--; //decrement number left of specific letter
          totalBag--;
        }//for
      }//if
      else return;

     }//fillletters()

 }//class

 //Class for Saving Letters and their total & points
 class Letter {
   char value; //letter A to Z or ' ' : from PC_PR_letters.txt
   int pts;
   int num; //how many there are

   public Letter (char value, int pts, int num) {
     this.value = value;
     this.pts = pts;
     this.num = num;
   }//letter constructor
   
   public String toString () {
     return (value + "-" + pts);
   }//toString()

 }//class Letter
 
//Class for creating players
 class Player {
   static Random r = new Random();
   String name;
   ArrayList <Integer> roundScores;
   int totalScore;
   ArrayList <Letter> letters;

   public Player(String name) {
     this.name = name;
     roundScores = new ArrayList <>();
     totalScore = 0;
     letters = new ArrayList <> ();
   }//player constructor

   public String toString() {
     return (name + " " + totalScore);
   }//toString()

   public static Comparator <Player> totalComp =
      new Comparator <Player> () {
        public int compare(Player p1, Player p2) {
          return (p1.totalScore - p2.totalScore);
        }
      };

 }//class Player