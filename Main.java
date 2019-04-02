import java.util.*;

public class Main {

    public static void main(String[] args) {
        double alpha = 0.1;
        double maxAlpha = 0.9;
        int max = 1000;
        int repeat = 1000;

        long startTime = System.currentTimeMillis();

//        compareLinearQuadratic(alpha, maxAlpha, max, repeat);
        optimalQuadraticCoefficient(alpha, maxAlpha, max, repeat);
//        findBadQuadraticPairs(1, max, repeat);

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (double) (endTime - startTime) / (1000 * 60 * 60) + "hours\n");
    }

    public static void findBadQuadraticPairs(double alpha, int max, int repeat){
        // find the bad quadratic pairs for a given alpha. "Bad" is defined as it takes the algorithm more than a given
        // time limit to search for an open slot. The default time limit is set at 10 second, but one can set custome
        // time limit by including an additional argument to the StudentSet constructor.
        Map<QuadraticPair, Integer> badPairs = new HashMap<QuadraticPair, Integer>();
        for (int r = 0; r < repeat; r++) {
            System.out.println("Round: " + (r+1));
            for (int i = 1; i < 10; i++) {
                for (int j = 1; j < 10; j++) {
                    double c1 = i * 0.1;
                    double c2 = j * 0.1;
//                    System.out.println("Round: " + r + ", c1 = " + String.format("%.1f", c1) + ", c2 = " + String.format("%.1f", c2));
                    StudentSet ss = new StudentSet(max, alpha, 2, c1, c2);
                    if (ss.timedOut) {
                        QuadraticPair pair = new QuadraticPair(c1, c2);
                        if (badPairs.containsKey(pair))
                            badPairs.put(pair, badPairs.get(pair) + 1);
                        else
                            badPairs.put(pair, 1);
                    }
                }
            }
        }

        System.out.println("Frequencies of time out:");
        // turn map into array
        List<Map.Entry<QuadraticPair, Integer>> badPairArray = new ArrayList<Map.Entry<QuadraticPair, Integer>>(badPairs.entrySet());
        // sort array based on map entry object's value, reverse order
        Collections.sort(badPairArray, Comparator.comparing(Map.Entry<QuadraticPair, Integer>::getValue).reversed());

        for (Map.Entry<QuadraticPair, Integer> p : badPairArray){
            System.out.println(p.getKey() + ": " + p.getValue().toString());
        }
    }

    public static void optimalQuadraticCoefficient(double alpha, double maxAlpha, int max, int repeat){
        // Compute the optimal quadratic coefficient, based totally on average chain length, for each alpha after certain
        // number of repeats. Note that we do not consider the efficiency of the coefficient in this computation, i.e.
        // a quadratic coefficient that produces the shortest average chain length could take a long time to find the next empty slot.
        double c1Min = 0.1, c1Max = 1;
        double c2Min = 0.1, c2Max = 1;
        double c1, c2;
        double chainLength = 0;
        double optimalc1 = 0, optimalc2 = 0;
        double minCL = 1000;

        StringBuilder output = new StringBuilder("Optimal c1, c2 pair in terms of average chain length.\nRepetition: " + repeat +
                "\nalpha\tc1\tc2\tmin_ave_chain_len\n");

        // compute for each alpha value
        while (alpha <= maxAlpha){
            minCL = 1000;
            output.append(String.format("%.1f", alpha) + "\t");
            // compute min chain length for each (c1, c2)
            for (c1 = c1Min; c1 <= c1Max; c1+=0.1){
                for (c2 = c2Min; c2 <= c2Max; c2+=0.1){
                    chainLength = 0;
                    for (int i = 0; i < repeat; i++) {
                        StudentSet ss2 = new StudentSet(max, alpha, 2, c1, c2);
                        chainLength += ss2.averageChainLength();
                    }
                    double aveCL = chainLength / (double)repeat;
                    if (aveCL < minCL) { // record min chain length and the corresponding (c1, c2)
                        minCL = aveCL;
                        optimalc1 = c1;
                        optimalc2 = c2;
                    }
                }
            }
            output.append (String.format("%.1f", optimalc1) + "\t" + String.format("%.1f", optimalc2) + "\t" + String.format("%.3f", minCL) + "\n");
            alpha += 0.1;
        }
        System.out.println(output);

    }

    public static void compareLinearQuadratic(double alpha, double maxAlpha, int max, int repeat){
        // compare linear probing and quadratic probing in terms of average chain length.
        // The quadratic pair is hard coded as c1 = 0.2, c2 = 0.6
        double c1 = 0.4, c2 = 0.4;
        double chainLength = 0;

        String output = "Average chain length comparison.\nRepetition: " + repeat +
                "\nQuadratic c1 = " + c1 + ", c2 = " + c2 +
                "\nalpha\tLinear\tQuadratic\n";

        // compute for each alpha value
        while (alpha <= maxAlpha){
            output += (String.format("%.1f", alpha) + "\t");

            // linear probing chain length averaging out over repetition
            chainLength = 0;
            for (int i = 0; i < repeat; i++) {
                StudentSet ss1 = new StudentSet(max, alpha, 1, 0, 0);
                chainLength += ss1.averageChainLength();
            }
            output += (String.format("%.3f", chainLength / repeat) + "\t");

            // quadratic probing chain length averaging out over repetition
            chainLength = 0;
            for (int i = 0; i < repeat; i++) {
                StudentSet ss2 = new StudentSet(max, alpha, 2, c1, c2);
                chainLength += ss2.averageChainLength();
            }
            output += (String.format("%.3f", chainLength / repeat) + "\n");

            alpha += 0.1;
        }

        System.out.println(output);
    }
}
