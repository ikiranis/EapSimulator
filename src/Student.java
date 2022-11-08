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
            System.out.println("Ο " + this + " παρέδωσε την " + papers[i]);

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
            if (i == 1) {
                if (exams[0].getGrade() > 4) {
                    continue;
                }
            }

            exams[i] = new Exam(i+1);

            System.out.println("Ο " + this + " συμμετείχε στις " + exams[i]);

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
