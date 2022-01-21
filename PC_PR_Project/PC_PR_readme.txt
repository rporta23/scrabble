Title: Scrabble Game (Version 0)
Authors: Rose Porta and Catherine Park
Course: CSC 212
Date: 13 May 2021
Files: PC_PR_Board.java, PC_PR_letters.txt, PC_PR_rules.txt, PC_PR_scoresheet.txt, PC_PR_readme.txt
Software Needed: java version 15.0.2, tested on Mac OS
Abstract: 
    The purpose of this program is for users to play a game of scrabble. The game works such that each player receives 7 random letters, and each round the player places letters on the board to form a word building off of words/letters already on the board (the word must connect to a previous word except for first word that starts in the center). The board is 15 by 15 and has special values (double word, triple word) on the diagonals and a few other locations. Each letter has an associated point value, and each round, each player accumulates points based on the letters in their word and its placement. The game continues until all letters are gone (game starts with specified amount of each letter), or until players choose to end. At the end, whoever has the most points wins. The game is for at least two players, but could be as many as user specifies. 
    The main file (PC_PR_Scrabble.java) will have a menu with options to view rules, authors, or play game. When user views rules, the program will read rules from PC_PR_rules.txt and display them. When the user selects play, they will be prompted to enter the name of each player, and the program will create a score sheet (PC_PR_scoresheet.txt) that will record the number of points accumulated by each player after each round. At the start of the game, the program will read in the data from PC_PR_letters.txt which will have information about the point value of each letter and number of each letter available. It will create a Letter object for each letter and add all letters to a list to keep track of them throughout the game.

PC_PR_letters.txt
A,1,9
B,3,2
C,3,2
D,2,4
E,1,12