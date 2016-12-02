package imd.player.control;

public class ProgressBarControl implements Runnable {

    private javax.swing.JProgressBar progressBar;
    private Thread progress;

    public ProgressBarControl() {

    }

    public void calculateProgress(javax.swing.JProgressBar progressBar) {
        this.progressBar = progressBar;
        if (this.progress == null || !this.progress.isAlive()) {
            this.progress = new Thread(this, "progresscalculator");
        } else {
            this.progress.interrupt();
            this.progress = new Thread(this, "progresscalculator");
        }
        progress.start();
    }

    @Override
    public void run() {
        try {
            int initialProgress = Mp3Player.getInstance().getStream().available();
            this.progressBar.setMaximum(initialProgress);
            while (Mp3Player.getInstance().getThread_t().isAlive()) {
                if (Mp3Player.getInstance().getStream().available() == 0) {
                    break;
                }
                this.progressBar.setValue(initialProgress - Mp3Player.getInstance().getStream().available());
                Thread.sleep(1);
            }
            this.progressBar.setValue(this.progressBar.getMinimum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
