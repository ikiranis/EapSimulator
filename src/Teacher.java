import java.util.ArrayList;
import java.util.Random;

/**
 * Η κλάση του καθηγητή
 */
public class Teacher extends Thread {
    // Τα βασικά properties του καθηγητή
    private int id;
    private ArrayList<Student> students = new ArrayList<>();

    private Random random = new Random();

    public Teacher(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        // Ξεκινάει όλα τα threads των φοιτητών που ανατέθηκαν στον συγκεκριμένο καθηγητή
        for(Student student : students) {
            student.start();
        }

        // Αναμονή να τερματίσουν όλοι οι φοιτητές
        waitStudentsThreads();
    }

    /**
     * Βαθμολόγηση εργασιών/εξετάσεων. Είναι synchronized για να μην μπορεί ο καθηγητής
     * να βαθμολογεί ταυτόχρονα άλλες εργασίες/εξετάσεις
     *
     * @param work
     * @param student
     */
    public synchronized void setGrade(Work work, Student student) {
        // Διαφορετικό κείμενο αναλόγως αν είναι εργασία ή εξετάσεις
        String pronounce = (work instanceof Paper) ? "την " : "τις ";

        System.out.println("Ο " + this + " βαθμολογεί " + pronounce + work + " του " + student);

        // Καθυστέρηση για να εξομοιωθεί ο χρόνος βαθμολόγησης
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Βαθμολογείται η εργασία/εξέταση με 1-10
        work.setGrade(random.nextInt(11 - 1) + 1);

        System.out.println("Ο " + this + " βαθμολόγησε " + pronounce + work + " (" + work.getGrade() + ") του " + student);
    }

    /**
     * Ο καθηγητής βαθμολογεί τις εξετάσεις του φοιτητή. Χρησιμοποιείται στην περίπτωση που όλοι οι καθηγητές
     * βαθμολογούν το γραπτό στις εξετάσεις ενός φοιτητή. Είναι synchronized για να μην βαθμολογεί ο καθηγητής
     * ταυτόχρονα κι άλλες εξετάσεις
     *
     * @param student
     * @param exam
     * @return
     */
    public synchronized int getGrade(Student student, Exam exam) {
        int grade = random.nextInt(11 - 1) + 1;
        System.out.println("O " + this + " βαθμολόγησε τον " + student + " στις " + exam + " με " + grade);

        return grade;
    }

    /**
     * Ανάθεση του φοιτητή στον καθηγητή
     *
     * @param student
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Αναμονή τερματισμού των threads των φοιτητών
     */
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
