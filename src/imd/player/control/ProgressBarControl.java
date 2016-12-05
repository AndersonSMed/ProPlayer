package imd.player.control;

import javax.swing.JProgressBar;

/**
 * Class responsible for calculate the music progress and return it to the user.
 * @author Anderson Santos and Yuri Reinaldo.
 */
public class ProgressBarControl implements Runnable {

    /**
     * Receives an {@link JProgressBar} and make an backup. 
     */
    private javax.swing.JProgressBar progressBar;
    
    /**
     * {@link Thread} responsible for calculate the progress.
     */
    private Thread progress;

    /**
     * Void constructor.
     */
    public ProgressBarControl() {

    }

    /**
     * It backs up a {@link JProgressBar} and sets the local {@link Thread} as a {@link ProgressBarControl} Thread.
     * @param progressBar Receives the {@link JProgressBar} that will receive the playback progress.
     */
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

    /**
     * Thread that calculates the playback progress of the song and returns it to the {@link JProgressBar}.
     */
    @Override
    public void run() {
        try {
            int initialProgress = Mp3Player.getInstance().getStream().available();
            this.progressBar.setMaximum(initialProgress);
            while (Mp3Player.getInstance().getThread_t().isAlive()) {
                try {
                    if(Mp3Player.getInstance().getStream().available() == 0){
                        break;
                    }
                    this.progressBar.setValue(initialProgress - Mp3Player.getInstance().getStream().available());
                    Thread.sleep(10);
                } catch (Exception e) {
                    break;
                }
            }
            this.progressBar.setValue(this.progressBar.getMinimum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
