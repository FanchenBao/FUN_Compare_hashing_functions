import java.util.Random;

public class Student {
    private String firstName;
    private String lastName;
    private String zNumber;
    private int major;
    private double gpa;

    private Random randGen = new Random();

    public Student(String firstName, String lastName, String zNumber, int major, double gpa){ // constructor
        this.firstName = firstName;
        this.lastName = lastName;
        this.zNumber = zNumber;
        this.major = major;
        this.gpa = gpa;
    }

    public Student(){ // default constructor
        this.firstName = randomString(2, 10,false);
        this.lastName = randomString(2, 10,false);
        this.zNumber = "Z" + randomString(8, 8,true);
        this.major = randGen.nextInt(100);
        this.gpa = 2 + randGen.nextDouble() * 2; // set gpa not lower than 2, because gpa below 1 looks to unrealistic

    }

    private String randomString(int minLen, int maxLen, boolean numeric){
        // ASCII a = 97, z = 122
        // ASCII 0 = 48, 9 = 57
        if (!numeric)
            return createRandomString(97, 122-97+1, minLen + randGen.nextInt(maxLen - minLen + 1));
        else
            return createRandomString(48, 57-48+1, minLen + randGen.nextInt(maxLen - minLen + 1));
    }

    private String createRandomString(int asciiStart, int range, int length){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < length; i++)
            res.append ((char)(asciiStart + randGen.nextInt(range)));
        return res.toString();
    }


    private int generateCode(String str){
        int res = 0;
        for (int i = 0; i < str.length(); i++){
            res += (i + 1) * str.charAt(i);
        }
        return res;
    }

    // custom hash code function, generate a long integer value from all fields of input
    public long customHashCode(){
        long hashCode = (long) (generateCode(lastName) + generateCode(firstName) + generateCode(zNumber) + 1000 * gpa + major);

        return hashCode;
    }

    @Override
    public String toString() {
        return "< " + firstName + ", "
                    + lastName + ", "
                    + zNumber + ", "
                    + major + ", "
                    + String.format("%.2f", gpa) + " >";
    }
}
