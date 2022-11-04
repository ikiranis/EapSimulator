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

    @Override
    public String toString() {
        return "Student" + id;
    }
}
