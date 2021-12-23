import java.io.*;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        BufferedReader br = null;
        String file = "input.txt";
        Game game = null;
        char neighbor;
        double result = Double.NEGATIVE_INFINITY;
        double finalResult = result;
        String move = null;
        String pos = null;
        HashMap<Integer, String> alphabet = new HashMap<Integer, String>();
        alphabet.put(0, "A");
        alphabet.put(1, "B");
        alphabet.put(2, "C");
        alphabet.put(3, "D");
        alphabet.put(4, "E");
        alphabet.put(5, "F");
        alphabet.put(6, "G");
        alphabet.put(7, "H");
        alphabet.put(8, "I");
        alphabet.put(9, "J");
        alphabet.put(10, "K");
        alphabet.put(11, "L");
        alphabet.put(12, "M");
        alphabet.put(13, "N");
        alphabet.put(14, "O");
        alphabet.put(15, "P");
        alphabet.put(16, "Q");
        alphabet.put(17, "R");
        alphabet.put(18, "S");
        alphabet.put(19, "T");
        alphabet.put(20, "U");
        alphabet.put(21, "V");
        alphabet.put(22, "W");
        alphabet.put(23, "X");
        alphabet.put(24, "Y");
        alphabet.put(25, "Z");


        try {
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            game = new Game();
            String N = br.readLine().trim();
            String Mode = br.readLine().trim();
            String player = br.readLine().trim();
            String Depth = br.readLine().trim();
            game.config = Integer.parseInt(N);
            game.mode = Mode;
            game.player = player;
            game.depth = Integer.parseInt(Depth);
            game.cellValues = new String[game.config][game.config];
            game.boardstate = new char[game.config][game.config];
            for (int i = 0; i < Integer.parseInt(N); i++) {
                String statesInfo = br.readLine();
                String[] statesSplit = statesInfo.split(" ");
                int j = 0;

                for (String f : statesSplit) {

                    game.cellValues[i][j] = f;

                    j++;
                }
            }
            for (int i = 0; i < Integer.parseInt(N); i++) {
                String statesInfo = br.readLine();
                char[] statesSplit = statesInfo.toCharArray();
                int j = 0;
                for (char f : statesSplit) {
                    game.boardstate[i][j] = f;
                    j++;
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (NullPointerException ex) {
            File fw = new File("output.txt");
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter(fw));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } finally {
            try {
                br.close();
            } catch (IOException ex) {
            } catch (NullPointerException ex) {
            }
        }
        if (game.player.charAt(0) == 'X') {
            neighbor = 'O';
            game.neighbor = 'O';
        } else {
            neighbor = 'X';
            game.neighbor = 'X';
        }
        if (game.mode.equals("MINIMAX")) {
            for (int i = 0; i < game.config; i++) {
                for (int j = 0; j < game.config; j++) {
                    if (game.boardstate[i][j] == '.') {
                        char[][] state = new char[game.config][game.config];
                        for (int m = 0; m < game.config; m++) {
                            for (int n = 0; n < game.config; n++) {
                                state[m][n] = game.boardstate[m][n];
                            }
                        }
                        state[i][j] = game.player.charAt(0);
                        double utilityValue = minimax(state, game, neighbor, 1, i, j, "Stake", game.player.charAt(0));
                        for (int m = 0; m < game.config; m++) {
                            for (int n = 0; n < game.config; n++) {
                                state[m][n] = game.boardstate[m][n];
                            }
                        }
                        result = Math.max(result, utilityValue);
                        if (finalResult != result) {
                            pos = i + " " + j;
                            move = "Stake";
                        }
                        finalResult = result;
                    }
                }
            }

            for (int i = 0; i < game.config; i++) {
                for (int j = 0; j < game.config; j++) {
                    if (game.boardstate[i][j] == game.player.charAt(0)) {
                        char[][] state = new char[game.config][game.config];
                        for (int m = 0; m < game.config; m++) {
                            for (int n = 0; n < game.config; n++) {
                                state[m][n] = game.boardstate[m][n];
                            }
                        }
                        if (i + 1 < game.config && state[i][j] == game.player.charAt(0) && state[i + 1][j] == '.') {
                            state[i + 1][j] = game.player.charAt(0);
                            double utilityValue = minimax(state, game, neighbor, 1, i + 1, j, "Raid", game.player.charAt(0));
                            result = Math.max(result, utilityValue);
                            if (finalResult != result) {
                                pos = (i + 1) + " " + j;
                                move = "Raid";
                            }
                            finalResult = result;
                            for (int m = 0; m < game.config; m++) {
                                for (int n = 0; n < game.config; n++) {
                                    state[m][n] = game.boardstate[m][n];
                                }
                            }

                        }
                        if (i - 1 >= 0 && state[i][j] == game.player.charAt(0) && state[i - 1][j] == '.') {
                            state[i - 1][j] = game.player.charAt(0);
                            double utilityValue = minimax(state, game, neighbor, 1, i - 1, j, "Raid", game.player.charAt(0));
                            result = Math.max(result, utilityValue);
                            if (finalResult != result) {
                                pos = (i - 1) + " " + j;
                                move = "Raid";
                            }
                            finalResult = result;
                            for (int m = 0; m < game.config; m++) {
                                for (int n = 0; n < game.config; n++) {
                                    state[m][n] = game.boardstate[m][n];
                                }
                            }
                        }
                        if (j + 1 < game.config && state[i][j] == game.player.charAt(0) && state[i][j + 1] == '.') {
                            state[i][j + 1] = game.player.charAt(0);
                            double utilityValue = minimax(state, game, neighbor, 1, i, j + 1, "Raid", game.player.charAt(0));
                            result = Math.max(result, utilityValue);
                            if (finalResult != result) {
                                pos = i + " " + (j + 1);
                                move = "Raid";
                            }
                            finalResult = result;
                            for (int m = 0; m < game.config; m++) {
                                for (int n = 0; n < game.config; n++) {
                                    state[m][n] = game.boardstate[m][n];
                                }
                            }
                        }
                        if (j - 1 >= 0 && state[i][j] == game.player.charAt(0) && state[i][j - 1] == '.') {
                            state[i][j - 1] = game.player.charAt(0);
                            double utilityValue = minimax(state, game, neighbor, 1, i, j - 1, "Raid", game.player.charAt(0));
                            result = Math.max(result, utilityValue);
                            if (finalResult != result) {
                                pos = i + " " + (j - 1);
                                move = "Raid";
                            }
                            finalResult = result;
                            for (int m = 0; m < game.config; m++) {
                                for (int n = 0; n < game.config; n++) {
                                    state[m][n] = game.boardstate[m][n];
                                }
                            }
                        }

                        for (int m = 0; m < game.config; m++) {
                            for (int n = 0; n < game.config; n++) {
                                state[m][n] = game.boardstate[m][n];
                            }
                        }

                    }
                }
            }

        } else {
            for (int i = 0; i < game.config; i++) {
                for (int j = 0; j < game.config; j++) {
                    if (game.boardstate[i][j] == '.') {
                        char[][] state = new char[game.config][game.config];
                        for (int m = 0; m < game.config; m++) {
                            for (int n = 0; n < game.config; n++) {
                                state[m][n] = game.boardstate[m][n];
                            }
                        }


                        state[i][j] = game.player.charAt(0);

                        double utilityValue = alphabetaminimax(state, game, neighbor, 1, i, j, "Stake", game.player.charAt(0), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                        for (int m = 0; m < game.config; m++) {
                            for (int n = 0; n < game.config; n++) {
                                state[m][n] = game.boardstate[m][n];
                            }
                        }

                        result = Math.max(result, utilityValue);
                        if (finalResult != result) {
                            pos = i + " " + j;
                            move = "Stake";
                        }
                        finalResult = result;


                    }
                }
            }

            for (int i = 0; i < game.config; i++) {
                for (int j = 0; j < game.config; j++) {
                    if (game.boardstate[i][j] == game.player.charAt(0)) {
                        char[][] state = new char[game.config][game.config];
                        for (int m = 0; m < game.config; m++) {
                            for (int n = 0; n < game.config; n++) {
                                state[m][n] = game.boardstate[m][n];
                            }
                        }
                        if (i + 1 < game.config && state[i][j] == game.player.charAt(0) && state[i + 1][j] == '.') {
                            state[i + 1][j] = game.player.charAt(0);
                            double utilityValue = alphabetaminimax(state, game, neighbor, 1, i + 1, j, "Raid", game.player.charAt(0), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

                            result = Math.max(result, utilityValue);
                            if (finalResult != result) {
                                pos = (i + 1) + " " + j;
                                move = "Raid";
                            }
                            finalResult = result;
                            for (int m = 0; m < game.config; m++) {
                                for (int n = 0; n < game.config; n++) {
                                    state[m][n] = game.boardstate[m][n];
                                }
                            }

                        }
                        if (i - 1 >= 0 && state[i][j] == game.player.charAt(0) && state[i - 1][j] == '.') {
                            state[i - 1][j] = game.player.charAt(0);
                            double utilityValue = alphabetaminimax(state, game, neighbor, 1, i - 1, j, "Raid", game.player.charAt(0), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                            result = Math.max(result, utilityValue);
                            if (finalResult != result) {
                                pos = (i - 1) + " " + j;
                                move = "Raid";
                            }
                            finalResult = result;
                            for (int m = 0; m < game.config; m++) {
                                for (int n = 0; n < game.config; n++) {
                                    state[m][n] = game.boardstate[m][n];
                                }
                            }
                        }
                        if (j + 1 < game.config && state[i][j] == game.player.charAt(0) && state[i][j + 1] == '.') {
                            state[i][j + 1] = game.player.charAt(0);
                            double utilityValue = alphabetaminimax(state, game, neighbor, 1, i, j + 1, "Raid", game.player.charAt(0), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                            result = Math.max(result, utilityValue);
                            if (finalResult != result) {
                                pos = i + " " + (j + 1);
                                move = "Raid";
                            }
                            finalResult = result;
                            for (int m = 0; m < game.config; m++) {
                                for (int n = 0; n < game.config; n++) {
                                    state[m][n] = game.boardstate[m][n];
                                }
                            }
                        }
                        if (j - 1 >= 0 && state[i][j] == game.player.charAt(0) && state[i][j - 1] == '.') {
                            state[i][j - 1] = game.player.charAt(0);
                            double utilityValue = alphabetaminimax(state, game, neighbor, 1, i, j - 1, "Raid", game.player.charAt(0), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                            result = Math.max(result, utilityValue);
                            if (finalResult != result) {
                                pos = i + " " + (j - 1);
                                move = "Raid";
                            }
                            finalResult = result;
                            for (int m = 0; m < game.config; m++) {
                                for (int n = 0; n < game.config; n++) {
                                    state[m][n] = game.boardstate[m][n];
                                }
                            }
                        }

                        for (int m = 0; m < game.config; m++) {
                            for (int n = 0; n < game.config; n++) {
                                state[m][n] = game.boardstate[m][n];
                            }
                        }

                    }
                }
            }

        }
        File fw = new File("output.txt");
        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(fw));
            String[] position = pos.split(" ");
            bw.write((alphabet.get(Integer.parseInt(position[1]))));
            int sum = Integer.parseInt(position[0]) + 1;
            bw.write(sum + "");
            bw.write(' ');
            bw.write(move);
            bw.newLine();
            char[][] state1 = new char[game.config][game.config];
            for (int f = 0; f < game.config; f++) {
                for (int g = 0; g < game.config; g++) {
                    state1[f][g] = game.boardstate[f][g];
                }
            }
            for (int f = 0; f < game.config; f++) {
                for (int g = 0; g < game.config; g++) {
                    if (f == Integer.parseInt(position[0]) && g == Integer.parseInt(position[1])) {
                        state1[f][g] = game.player.charAt(0);
                        if (f + 1 < game.config && state1[f + 1][g] == neighbor) {
                            state1[f + 1][g] = game.player.charAt(0);
                        }
                        if (f - 1 >= 0 && state1[f - 1][g] == neighbor) {
                            state1[f - 1][g] = game.player.charAt(0);
                        }
                        if (g + 1 < game.config && state1[f][g + 1] == neighbor) {
                            state1[f][g + 1] = game.player.charAt(0);
                        }
                        if (g - 1 >= 0 && state1[f][g - 1] == neighbor) {
                            state1[f][g - 1] = game.player.charAt(0);
                        }
                    }
                }
            }
            for (int f = 0; f < game.config; f++) {
                for (int g = 0; g < game.config; g++) {
                    bw.write(state1[f][g]);
                }
                if (f + 1 < game.config) {
                    bw.newLine();
                }
            }
            bw.close();

        } catch (IOException e) {
            System.out.println("unable to write file");
        }
    }


    public static double minimax(char[][] state, Game game, char neighbor, int depth, int r, int c, String move, char next) {

        if (move.equals("Raid")) {
            if (r + 1 < game.config && state[r + 1][c] == neighbor) {
                state[r + 1][c] = next;
            }
            if (r - 1 >= 0 && state[r - 1][c] == neighbor) {
                state[r - 1][c] = next;
            }
            if (c + 1 < game.config && state[r][c + 1] == neighbor) {
                state[r][c + 1] = next;
            }
            if (c - 1 >= 0 && state[r][c - 1] == neighbor) {
                state[r][c - 1] = next;
            }
        }
        int count = 0;
        for (int i = 0; i < game.config; i++) {
            for (int j = 0; j < game.config; j++) {
                if (state[i][j] == '.') {
                    count = count + 1;
                }
            }
        }
        if (count == 0) {
            int opponent_sum = 0;
            int player_sum = 0;
            for (int i = 0; i < game.config; i++) {
                for (int j = 0; j < game.config; j++) {
                    if (state[i][j] == game.player.charAt(0)) {
                        player_sum = player_sum + Integer.parseInt(game.cellValues[i][j]);
                    } else {
                        if (state[i][j] == game.neighbor) {
                            opponent_sum = opponent_sum + Integer.parseInt(game.cellValues[i][j]);
                        }
                    }
                }
            }
            return player_sum - opponent_sum;
        }
        if (depth == game.depth) {
            int opponent_sum = 0;
            int player_sum = 0;
            for (int i = 0; i < game.config; i++) {
                for (int j = 0; j < game.config; j++) {
                    if (state[i][j] == game.player.charAt(0)) {
                        player_sum = player_sum + Integer.parseInt(game.cellValues[i][j]);
                    } else {
                        if (state[i][j] == game.neighbor) {
                            opponent_sum = opponent_sum + Integer.parseInt(game.cellValues[i][j]);
                        }
                    }
                }
            }
            return player_sum - opponent_sum;
        }
        char nextMove, currentMove;
        if (neighbor == 'X') {
            neighbor = 'O';
            nextMove = 'X';
            currentMove = 'O';
        } else {
            neighbor = 'X';
            nextMove = 'O';
            currentMove = 'X';
        }

        Double v = Double.POSITIVE_INFINITY;
        for (int i = 0; i < game.config; i++) {
            for (int j = 0; j < game.config; j++) {
                if (state[i][j] == '.') {
                    char[][] state1 = new char[game.config][game.config];
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                    state1[i][j] = nextMove;
                    v = Math.min(v, maxmin(state1, game, neighbor, depth + 1, i, j, "Stake", nextMove));
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
            }
        }
        for (int i = 0; i < game.config; i++) {
            for (int j = 0; j < game.config; j++) {
                char[][] state1 = new char[game.config][game.config];
                for (int m = 0; m < game.config; m++) {
                    for (int n = 0; n < game.config; n++) {
                        state1[m][n] = state[m][n];
                    }
                }
                if (i + 1 < game.config && state1[i][j] == nextMove && state1[i + 1][j] == '.') {
                    state1[i + 1][j] = nextMove;
                    v = Math.min(v, maxmin(state1, game, neighbor, depth + 1, i + 1, j, "Raid", nextMove));
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
                if (i - 1 >= 0 && state1[i][j] == nextMove && state1[i - 1][j] == '.') {
                    state1[i - 1][j] = nextMove;
                    v = Math.min(v, maxmin(state1, game, neighbor, depth + 1, i - 1, j, "Raid", nextMove));
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
                if (j + 1 < game.config && state1[i][j] == nextMove && state1[i][j + 1] == '.') {
                    state1[i][j + 1] = nextMove;
                    v = Math.min(v, maxmin(state1, game, neighbor, depth + 1, i, j + 1, "Raid", nextMove));
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
                if (j - 1 >= 0 && state1[i][j] == nextMove && state1[i][j - 1] == '.') {
                    state1[i][j - 1] = nextMove;
                    v = Math.min(v, maxmin(state1, game, neighbor, depth + 1, i, j - 1, "Raid", nextMove));
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
            }
        }
        return v;
    }

    public static double maxmin(char[][] state, Game game, char neighbor, int depth, int r, int c, String move, char next) {
        if (move.equals("Raid")) {
            if ((r + 1) < game.config && state[r + 1][c] == neighbor) {
                state[r + 1][c] = next;
            }
            if (r > 0 && state[r - 1][c] == neighbor) {
                state[r - 1][c] = next;
            }
            if ((c + 1) < game.config) {
                if (state[r][c + 1] == neighbor) {
                    state[r][c + 1] = next;
                }
            }
            if (c > 0 && state[r][c - 1] == neighbor) {
                state[r][c - 1] = next;
            }
        }
        int count = 0;
        for (int i = 0; i < game.config; i++) {
            for (int j = 0; j < game.config; j++) {
                if (state[i][j] == '.') {
                    count = count + 1;
                }
            }
        }
        if (count == 0) {
            int opponent_sum = 0;
            int player_sum = 0;
            for (int i = 0; i < game.config; i++) {
                for (int j = 0; j < game.config; j++) {
                    if (state[i][j] == game.player.charAt(0)) {
                        player_sum = player_sum + Integer.parseInt(game.cellValues[i][j]);
                    } else {
                        if (state[i][j] == game.neighbor) {
                            opponent_sum = opponent_sum + Integer.parseInt(game.cellValues[i][j]);
                        }
                    }
                }
            }
            return player_sum - opponent_sum;
        }
        if (depth == game.depth) {
            int opponent_sum = 0;
            int player_sum = 0;
            for (int i = 0; i < game.config; i++) {
                for (int j = 0; j < game.config; j++) {
                    if (state[i][j] == game.player.charAt(0)) {
                        player_sum = player_sum + Integer.parseInt(game.cellValues[i][j]);
                    } else {
                        if (state[i][j] == game.neighbor) {
                            opponent_sum = opponent_sum + Integer.parseInt(game.cellValues[i][j]);
                        }
                    }
                }
            }
            return player_sum - opponent_sum;
        }
        char nextMove, currentMove;
        if (neighbor == 'X') {
            neighbor = 'O';
            nextMove = 'X';
            currentMove = 'O';
        } else {
            neighbor = 'X';
            nextMove = 'O';
            currentMove = 'X';
        }
        Double v = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < game.config; i++) {
            for (int j = 0; j < game.config; j++) {
                if (state[i][j] == '.') {
                    char[][] state1 = new char[game.config][game.config];
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                    state1[i][j] = nextMove;
                    v = Math.max(v, minimax(state1, game, neighbor, depth + 1, i, j, "Stake", nextMove));
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
            }
        }
        for (int i = 0; i < game.config; i++) {
            for (int j = 0; j < game.config; j++) {
                char[][] state1 = new char[game.config][game.config];
                for (int m = 0; m < game.config; m++) {
                    for (int n = 0; n < game.config; n++) {
                        state1[m][n] = state[m][n];
                    }
                }
                if (i + 1 < game.config && state1[i][j] == nextMove && state1[i + 1][j] == '.') {
                    state1[i + 1][j] = nextMove;
                    v = Math.max(v, minimax(state1, game, neighbor, depth + 1, i + 1, j, "Raid", nextMove));
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
                if (i - 1 >= 0 && state1[i][j] == nextMove && state1[i - 1][j] == '.') {
                    state1[i - 1][j] = nextMove;
                    v = Math.max(v, minimax(state1, game, neighbor, depth + 1, i - 1, j, "Raid", nextMove));
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
                if (j + 1 < game.config && state1[i][j] == nextMove && state1[i][j + 1] == '.') {
                    state1[i][j + 1] = nextMove;
                    v = Math.max(v, minimax(state1, game, neighbor, depth + 1, i, j + 1, "Raid", nextMove));
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
                if (j - 1 >= 0 && state1[i][j] == nextMove && state1[i][j - 1] == '.') {
                    state1[i][j - 1] = nextMove;
                    v = Math.max(v, minimax(state1, game, neighbor, depth + 1, i, j - 1, "Raid", nextMove));
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
                for (int m = 0; m < game.config; m++) {
                    for (int n = 0; n < game.config; n++) {
                        state1[m][n] = state[m][n];
                    }
                }
            }
        }
        return v;
    }

    public static double alphabetaminimax(char[][] state, Game game, char neighbor, int depth, int r, int c, String move, char next, Double alpha, Double beta) {
        if (move.equals("Raid")) {
            if (r + 1 < game.config && state[r + 1][c] == neighbor) {
                state[r + 1][c] = next;
            }
            if (r - 1 >= 0 && state[r - 1][c] == neighbor) {
                state[r - 1][c] = next;
            }
            if (c + 1 < game.config && state[r][c + 1] == neighbor) {
                state[r][c + 1] = next;
            }
            if (c - 1 >= 0 && state[r][c - 1] == neighbor) {
                state[r][c - 1] = next;
            }
        }
        int count = 0;
        for (int i = 0; i < game.config; i++) {
            for (int j = 0; j < game.config; j++) {
                if (state[i][j] == '.') {
                    count = count + 1;
                }
            }
        }
        if (count == 0) {
            int opponent_sum = 0;
            int player_sum = 0;
            for (int i = 0; i < game.config; i++) {
                for (int j = 0; j < game.config; j++) {
                    if (state[i][j] == game.player.charAt(0)) {
                        player_sum = player_sum + Integer.parseInt(game.cellValues[i][j]);
                    } else {
                        if (state[i][j] == game.neighbor) {
                            opponent_sum = opponent_sum + Integer.parseInt(game.cellValues[i][j]);
                        }
                    }
                }
            }
            return player_sum - opponent_sum;
        }
        if (depth == game.depth) {

            int opponent_sum = 0;
            int player_sum = 0;
            for (int i = 0; i < game.config; i++) {
                for (int j = 0; j < game.config; j++) {
                    if (state[i][j] == game.player.charAt(0)) {
                        player_sum = player_sum + Integer.parseInt(game.cellValues[i][j]);
                    } else {
                        if (state[i][j] == game.neighbor) {
                            opponent_sum = opponent_sum + Integer.parseInt(game.cellValues[i][j]);
                        }
                    }
                }
            }
            return player_sum - opponent_sum;
        }
        char nextMove, currentMove;
        if (neighbor == 'X') {
            neighbor = 'O';
            nextMove = 'X';
            currentMove = 'O';
        } else {
            neighbor = 'X';
            nextMove = 'O';
            currentMove = 'X';
        }
        Double v = Double.POSITIVE_INFINITY;
        for (int i = 0; i < game.config; i++) {
            for (int j = 0; j < game.config; j++) {
                if (state[i][j] == '.') {
                    char[][] state1 = new char[game.config][game.config];
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                    state1[i][j] = nextMove;
                    v = Math.min(v, alphabetamaxmin(state1, game, neighbor, depth + 1, i, j, "Stake", nextMove, alpha, beta));
                    if (v <= alpha) {
                        return v;
                    }
                    beta = Math.min(beta, v);
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
            }
        }
        for (int i = 0; i < game.config; i++) {
            for (int j = 0; j < game.config; j++) {
                char[][] state1 = new char[game.config][game.config];
                for (int m = 0; m < game.config; m++) {
                    for (int n = 0; n < game.config; n++) {
                        state1[m][n] = state[m][n];
                    }
                }
                if (i + 1 < game.config && state1[i][j] == nextMove && state1[i + 1][j] == '.') {
                    state1[i + 1][j] = nextMove;
                    v = Math.min(v, alphabetamaxmin(state1, game, neighbor, depth + 1, i + 1, j, "Raid", nextMove, alpha, beta));
                    if (v <= alpha) {
                        return v;
                    }
                    beta = Math.min(beta, v);
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
                if (i - 1 >= 0 && state[i][j] == nextMove && state1[i - 1][j] == '.') {
                    state1[i - 1][j] = nextMove;
                    v = Math.min(v, alphabetamaxmin(state1, game, neighbor, depth + 1, i - 1, j, "Raid", nextMove, alpha, beta));
                    if (v <= alpha) {
                        return v;
                    }
                    beta = Math.min(beta, v);
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
                if (j + 1 < game.config && state[i][j] == nextMove && state1[i][j + 1] == '.') {
                    state1[i][j + 1] = nextMove;
                    v = Math.min(v, alphabetamaxmin(state1, game, neighbor, depth + 1, i, j + 1, "Raid", nextMove, alpha, beta));
                    if (v <= alpha) {
                        return v;
                    }
                    beta = Math.min(beta, v);
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
                if (j - 1 >= 0 && state[i][j] == nextMove && state1[i][j - 1] == '.') {
                    state1[i][j - 1] = nextMove;
                    v = Math.min(v, alphabetamaxmin(state1, game, neighbor, depth + 1, i, j - 1, "Raid", nextMove, alpha, beta));
                    if (v <= alpha) {
                        return v;
                    }
                    beta = Math.min(beta, v);
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                }
            }
        }
        return v;
    }

    public static double alphabetamaxmin(char[][] state, Game game, char neighbor, int depth, int r, int c, String move, char next, Double alpha, Double beta) {
        if (move.equals("Raid")) {
            if ((r + 1) < game.config && state[r + 1][c] == neighbor) {
                state[r + 1][c] = next;
            }
            if (r > 0 && state[r - 1][c] == neighbor) {
                state[r - 1][c] = next;
            }
            if ((c + 1) < game.config) {
                if (state[r][c + 1] == neighbor) {
                    state[r][c + 1] = next;
                }
            }
            if (c > 0 && state[r][c - 1] == neighbor) {
                state[r][c - 1] = next;
            }
        }
        int count = 0;
        for (int i = 0; i < game.config; i++) {
            for (int j = 0; j < game.config; j++) {
                if (state[i][j] == '.') {
                    count = count + 1;
                }
            }
        }
        if (count == 0) {
            int opponent_sum = 0;
            int player_sum = 0;
            for (int i = 0; i < game.config; i++) {
                for (int j = 0; j < game.config; j++) {
                    if (state[i][j] == game.player.charAt(0)) {
                        player_sum = player_sum + Integer.parseInt(game.cellValues[i][j]);
                    } else {
                        if (state[i][j] == game.neighbor) {
                            opponent_sum = opponent_sum + Integer.parseInt(game.cellValues[i][j]);
                        }
                    }
                }
            }
            return player_sum - opponent_sum;
        }
        if (depth == game.depth) {

            int opponent_sum = 0;
            int player_sum = 0;
            for (int i = 0; i < game.config; i++) {
                for (int j = 0; j < game.config; j++) {
                    if (state[i][j] == game.player.charAt(0)) {
                        player_sum = player_sum + Integer.parseInt(game.cellValues[i][j]);
                    } else {
                        if (state[i][j] == game.neighbor) {

                            opponent_sum = opponent_sum + Integer.parseInt(game.cellValues[i][j]);
                        }
                    }
                }
            }
            return player_sum - opponent_sum;
        }
        char nextMove, currentMove;
        if (neighbor == 'X') {
            neighbor = 'O';
            nextMove = 'X';
            currentMove = 'O';
        } else {
            neighbor = 'X';
            nextMove = 'O';
            currentMove = 'X';
        }
        Double v = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < game.config; i++) {
            for (int j = 0; j < game.config; j++) {
                if (state[i][j] == '.') {
                    char[][] state1 = new char[game.config][game.config];
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                    state1[i][j] = nextMove;
                    v = Math.max(v, alphabetaminimax(state1, game, neighbor, depth + 1, i, j, "Stake", nextMove, alpha, beta));
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                    if (v >= beta) {
                        return v;
                    }
                    alpha = Math.max(v, alpha);
                }
            }
        }
        for (int i = 0; i < game.config; i++) {
            for (int j = 0; j < game.config; j++) {
                char[][] state1 = new char[game.config][game.config];
                for (int m = 0; m < game.config; m++) {
                    for (int n = 0; n < game.config; n++) {
                        state1[m][n] = state[m][n];
                    }
                }
                if (i + 1 < game.config && state1[i][j] == nextMove && state1[i + 1][j] == '.') {
                    state1[i + 1][j] = nextMove;
                    v = Math.max(v, alphabetaminimax(state1, game, neighbor, depth + 1, i + 1, j, "Raid", nextMove, alpha, beta));
                    if (v >= beta) {
                        return v;
                    }
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                    alpha = Math.max(v, alpha);

                }
                if (i - 1 >= 0 && state1[i][j] == nextMove && state1[i - 1][j] == '.') {
                    state1[i - 1][j] = nextMove;
                    v = Math.max(v, alphabetaminimax(state1, game, neighbor, depth + 1, i - 1, j, "Raid", nextMove, alpha, beta));
                    if (v >= beta) {
                        return v;
                    }
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                    alpha = Math.max(v, alpha);
                }
                if (j + 1 < game.config && state1[i][j] == nextMove && state1[i][j + 1] == '.') {
                    state1[i][j + 1] = nextMove;
                    v = Math.max(v, alphabetaminimax(state1, game, neighbor, depth + 1, i, j + 1, "Raid", nextMove, alpha, beta));
                    if (v >= beta) {
                        return v;
                    }
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                    alpha = Math.max(v, alpha);
                }
                if (j - 1 >= 0 && state1[i][j] == nextMove && state1[i][j - 1] == '.') {
                    state1[i][j - 1] = nextMove;
                    v = Math.max(v, alphabetaminimax(state1, game, neighbor, depth + 1, i, j - 1, "Raid", nextMove, alpha, beta));
                    if (v >= beta) {
                        return v;
                    }
                    for (int m = 0; m < game.config; m++) {
                        for (int n = 0; n < game.config; n++) {
                            state1[m][n] = state[m][n];
                        }
                    }
                    alpha = Math.max(v, alpha);
                }
                for (int m = 0; m < game.config; m++) {
                    for (int n = 0; n < game.config; n++) {
                        state1[m][n] = state[m][n];
                    }
                }
            }
        }
        return v;
    }
}

class Game {
    int config;
    String mode;
    String player;
    int depth;
    String[][] cellValues = new String[config][config];
    char[][] boardstate = new char[config][config];
    String position;
    char neighbor;
    Double result = Double.NEGATIVE_INFINITY;
}
