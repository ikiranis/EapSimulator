import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static Random random = new Random();
    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Teacher> teachers = new ArrayList<>();

    private static void generateStudents() {
        int numberOfStudents = random.nextInt(6 - 1) + 1;
        for(int i=1; i<=numberOfStudents; i++) {
            int teacherId = random.nextInt(teachers.size());
            students.add(new Student(i, teachers.get(teacherId)));
            teachers.get(teacherId).addStudent(students.get(i-1));
        }
    }

    private static void generateTeachers() {
        int numberOfTeachers = random.nextInt(6 - 1) + 1;
        for(int i=1; i<=numberOfTeachers; i++) {
            teachers.add(new Teacher(i));
        }
    }

    /**
     * Εκκίνηση όλων των threads, περνώντας τις αντίστοιχες παραμέτρους δεδομένων σε κάθε ένα
     *
     */
    private static void startThreads() {
        for(int i=0; i<teachers.size(); i++) {
            teachers.get(i).start();
        }
    }

    /**
     * Αναμονή από το thread της main, για να τερματίσουν όλα τα threads
     */
    private static void waitThreads() {
        for (Teacher teacher: teachers) {
            try {
                teacher.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        generateTeachers();
        generateStudents();

        startThreads();

        waitThreads();

    }
}