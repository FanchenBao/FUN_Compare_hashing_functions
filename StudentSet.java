public class StudentSet {
    private Student[] students;
    private int numStudents;
    public boolean timedOut;

    public StudentSet(int max, double alpha, int hashMethod, double c1, double c2, int timeLimit){ // alpha is the loading factor, c1 and c2 for quadratic probing only
        students = new Student[max];
        numStudents = (int)(students.length * alpha);
        timedOut = false;
        for (int i = 0; i < numStudents; i++) {
            if (!insert(new Student(), hashMethod, c1, c2, timeLimit)) {
                timedOut = true;
                break;
            }
        }
    }

    public StudentSet(int max, double alpha, int hashMethod, double c1, double c2){ // constructor with default timeLimit = 10 second
        this(max, alpha, hashMethod, c1, c2, 10);
    }


    public boolean insert(Student student, int hashMethod, double c1, double c2, int timeLimit){
        int probe = 0;
        int index = hash(student.customHashCode(), probe, hashMethod, c1, c2); // compute initial hash key

        long startTime = System.currentTimeMillis();
        while (students[index] != null) { // if hash key doesn't work, probe next possible location
            index = hash(student.customHashCode(), ++probe, hashMethod, c1, c2);
            if ((System.currentTimeMillis() - startTime) / 1000 > timeLimit){
                System.out.println("Timed out!");
                return false;
            }
        }
        students[index] = student;
        return true;
    }

//    public void remove(Student student){
//
//    }
//
//    public boolean isIn(){
//
//    }

    public boolean isEmpty(){
        return (numStudents == 0);
    }

    public boolean isFull(){
        return numStudents == students.length;
    }

    // print the set out in table form, where '.' represents empty slot, 'x' filled slot
    public String tableToString(){
        StringBuilder res = new StringBuilder();
        int lineLength = 50; // allow 50 slot to be printed per line
        for (int i = 0; i < students.length; i++){
            if (students[i] == null)
                res.append(".");
            else
                res.append("x");
            lineLength--;
            if (lineLength == 0){
                res.append("\n");
                lineLength = 50;
            }
        }
        return res.toString();
    }

    // compute average chain length of the hash table
    public double averageChainLength(){
        int chainLength = 0;
        int chainCount = 0;
        int totalChainLength = 0;
        boolean inChain = false;

        for (int i = 0; i < students.length; i++){
            if (students[i] == null){ // hit an empty slot
                if (inChain){ // if was in chain, then conclude chain length, update chain count, etc.
                    totalChainLength += chainLength;
                    chainLength = 0;
                    chainCount++;
                    inChain = false;
                }
            }
            else{ // still in a chain
                chainLength++;
                inChain = true;
            }
        }
        if (inChain) { // array ends with a record
            chainCount++;
            totalChainLength += chainLength;
        }

        return (double)totalChainLength / chainCount;
    }

    // allows the set to be printed out in proper 'set' format
    public String toString(){
        String res = "{";
        if (numStudents > 0){
            for (int i = 0; i < students.length; i++){
                if (students[i] != null)
                    res += (students[i] + ",\n");
            }
            res = res.substring(0, res.length() - 2);
        }
        res += "}";
        return res;
    }


    // hash function. hashMethod = 1 (linear probing), 2 (quadratic probing)
    private int hash(long key, int p, int hashMethod, double c1, double c2){
        long finalKey = 0;
        long probe = (long)p;
        switch(hashMethod){
            case 1: // linear probing
                finalKey = key + probe;
                break;
            case 2: // quadratic probing
                finalKey = key + (long)(c1 * probe + c2 * probe * probe);
                break;
            default:
                System.out.println("Internal hashing error");
                break;
        }
        int returnKey = (int)(finalKey % students.length);
        return returnKey >= 0 ? returnKey : 0 - returnKey; // in case returnKey is negative, i.e. finalKey is too big even for long, revert its sign
    }
}
