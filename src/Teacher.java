import java.util.ArrayList;
import java.util.Random;

public class Teacher extends Thread {
    private int id;
    private ArrayList<Student> students = new ArrayList<>();

    private Random random = new Random();

    public Teacher(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        for(Student student : students) {
            student.start();
        }

        for (Student student : students) {
            try {
                student.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        waitStudentsThreads();
    }

    public synchronized void setGrade(Work work, Student student) {
        String pronounce = (work instanceof Paper) ? "την " : "τις ";

        System.out.println("Ο " + this + " βαθμολογεί " + pronounce + work + " του " + student);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        work.setGrade(random.nextInt(11 - 1) + 1);

        System.out.println("Ο " + this + " βαθμολόγησε " + pronounce + work + " (" + work.getGrade() + ") του " + student);
    }

    public synchronized int getGrade(Student student, Exam exam) {
        int grade = random.nextInt(11 - 1) + 1;
        System.out.println("O " + this + " βαθμολόγησε τον " + student + " στις " + exam + " με " + grade);

        return grade;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    private void waitStudentsThreads() {
        for (Student student: students) {
            try {
                student.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "Teacher" + id;
    }
}
