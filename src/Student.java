/**
 * Η κλάση του φοιτητή
 */
public class Student extends Thread {
    // Βασικά properties του φοιτητή
    private int id;
    private Teacher teacher;
    private Paper[] papers = new Paper[4];
    private Exam[] exams = new Exam[2];

    public Student(int id, Teacher teacher) {
        this.id = id;
        this.teacher = teacher;
    }

    @Override
    public void run() {
        // Ο φοιτητής γράφει εργασίες
        writePapers();

        // Υπολογίζεται η συνολική βαθμολογία των εργασιών
        int papersSum = calcPapersSumGrade();

        // Αν ο φοιτητής συγκεντρώσει πάνω από 20, συμμετέχει στις εξετάσεις
        if(papersSum >= 20) {
            writeExams();
        } else { // Αν όχι, οι εξετάσεις μηδενίζονται, σαν δείγμα ότι δε συμμετείχε
            setNullExams();
            System.out.println("Ο " + this + " δεν μπορεί να συμμετάσχει στις εξετάσεις, γιατί έχει συνολικό βαθμό εργασιών " + papersSum);
        }
    }

    /**
     * Οι εξετάσεις μηδενίζονται, σαν δείγμα ότι δε συμμετείχε ο φοιτητής σε αυτές
     */
    private void setNullExams() {
        for(int i=0; i<2; i++) {
            exams[i] = new Exam(i + 1);
            exams[i].setGrade(0);
        }
    }

    /**
     * Ο φοιτητής γράφει εργασίες
     */
    private void writePapers() {
        // Γράφει στην σειρά 4 εργασίες
        for(int i=0; i<4; i++) {
            // Δημιουργείται το αντίστοιχο αντικείμενο για κάθε εργασία
            papers[i] = new Paper(i+1);
            System.out.println("Ο " + this + " παρέδωσε την " + papers[i]);

            // Καθυστέρηση για να εξομοιωθεί ο χρόνος συγγραφής της εργασίας
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Η εργασία παραδίδεται και ο καθηγητής καλείται να τη βαθμολογήσει
            // Επειδή η συγκεκριμένη μέθοδος είναι synchronized, το πρόγραμμα μπλοκάρεται εδώ,
            // αν ο καθηγητής βαθμολογεί άλλη εργασία
            teacher.setGrade(papers[i], this);
        }
    }

    /**
     * Ο φοιτητής γράφει στις εξετάσεις
     */
    private void writeExams() {
        // Συμμετέχει 2 φορές στις εξετάσεις, αναλόγως την επιτυχία του στις τελικές
        for(int i=0; i<2; i++) {
            // Δημιουργείται το αντίστοιχο αντικείμενο
            exams[i] = new Exam(i+1);

            // Γίνεται έλεγχος στις επαναληπτικές, αν χρειάζεται να συμμετάσχει σε αυτές
            if (i == 1) {
                // Αν ο φοιτητής έχει γράψει πάνω από 5 στις τελικές, τότε δεν εκτελείται ο κώδικας
                // για τη συμμετοχή στις επαναληπτικές και αυτές μηδενίζονται
                if (exams[0].getGrade() >= 5) {
                    exams[1].setGrade(0);
                    continue;
                }
            }

            System.out.println("Ο " + this + " συμμετείχε στις " + exams[i]);

            // Καθυστέρηση για να εξομοιωθεί ο χρόνος συμμετοχής στις εξετάσεις
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Οι εξετάσεις βαθμολογούνται
            gradeExams(Main.getGradeKind(), exams[i]);
        }
    }

    /**
     * Βαθμολόγηση εξετάσεων. Υλοποιείται η προδιαγραφή της υποεργασίας, να βαθμολογούνται οι εξετάσεις
     * είτε από έναν καθηγητή, είτε από όλους
     *
     * @param gradeKind
     * @param exam
     */
    private void gradeExams(boolean gradeKind, Exam exam) {
        if (gradeKind) { // Αν είναι true τις εξετάσεις βαθμολογεί μόνο ένας καθηγητής
            teacher.setGrade(exam, this);
        } else { // Αλλιώς βαθμολογείται από όλους τους καθηγητές
            int sum = 0;

            // Όλοι οι καθηγητές βαθμολογούν
            for (Teacher teacher : Main.getTeachers()) {
                sum += teacher.getGrade(this, exam);
            }

            // Υπολογίζεται ο μέσος όρος από τις βαθμολογήσεις των καθηγητών
            int averageGrade = sum / Main.getTeachers().size();

            System.out.println("Οι καθηγητές βαθμολόγησαν τον " + this + " στις " + exam + " με βαθμό: " + averageGrade);

            // Καταχωρείται ο μέσος όρος, στις συγκεκριμένες εξετάσεις
            exam.setGrade(averageGrade);
        }
    }

    /**
     * Υπολογισμός συνολικού βαθμού εργασιών
     *
     * @return
     */
    private int calcPapersSumGrade() {
        int sum = 0;

        for (Paper paper : papers) {
            sum += paper.getGrade();
        }

        return sum;
    }

    /**
     * Υπολογισμός της τελικής βαθμολογίας, από τις εξετάσεις
     * Ο τελικός βαθμός, αν ο φοιτητής συμμετείχε και στις τελικές και στις επαναληπτικές,
     * βγαίνει από τον μεγαλύτερο από τους δύο
     *
     * @return
     */
    private int getFinalGrade() {
        if (exams[1].getGrade() != 0) {
            if (exams[1].getGrade() > exams[0].getGrade()) {
                return exams[1].getGrade();
            }

            return exams[0].getGrade();
        }

        return exams[0].getGrade();
    }

    /**
     * Το κείμενο της τελικής βαθμολογίας
     *
     * @return
     */
    public String getFinalGradeText() {
        if (exams[0].getGrade() == 0) { // Αν δεν υπάρχει βαθμολογία, ο φοιτητές δεν συμμετείχε στις εξετάσεις
            return "Ο " + this + " δεν μπόρεσε να συμμετάσχει στις εξετάσεις, γιατί είχε συνολικό βαθμό εργασιών " + calcPapersSumGrade();
        }

        return "Η τελική βαθμολογία του " + this + " είναι: " + getFinalGrade();
    }

    @Override
    public String toString() {
        return "Student" + id;
    }
}
