import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static Random random = new Random();
    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Teacher> teachers = new ArrayList<>();

    private static void generateStudents() {
        int numberOfStudents = random.nextInt(101 - 50) + 50;
        for(int i=1; i<=numberOfStudents; i++) {
            int teacherId = random.nextInt(teachers.size());
            students.add(new Student(i, teachers.get(teacherId)));
        }
    }

    private static void generateTeachers() {
        int numberOfTeachers = random.nextInt(16 - 5) + 5;
        for(int i=1; i<=numberOfTeachers; i++) {
            teachers.add(new Teacher(i));
        }
    }

    public static void main(String[] args) {
        generateTeachers();
        generateStudents();


        System.out.println(teachers);
        System.out.println(students);


    }
}