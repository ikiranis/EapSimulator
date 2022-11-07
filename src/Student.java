public class Student extends Thread {
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
        writePapers();
        writeExams();
    }

    private void writePapers() {
        for(int i=0; i<4; i++) {
            papers[i] = new Paper(i+1);
            System.out.println("Ο " + this + " παρέδωσε την ΓΕ" + papers[i].getNumber());

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            teacher.setGrade(papers[i], this);

        }
    }

    private void writeExams() {
        for(int i=0; i<2; i++) {
            exams[i] = new Exam(i+1);
            if (i == 0) {
                System.out.println("Ο " + this + " συμμετείχε στις τελικές εξετάσεις");
            } else {
                System.out.println("Ο " + this + " συμμετείχε στις επαναληπτικές εξετάσεις");
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            teacher.setGrade(exams[i], this);
        }
    }

    @Override
    public String toString() {
        return "Student" + id;
    }
}
