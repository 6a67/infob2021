import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Program that checks the size of a file or dir every seconds and outputs if a change happened
 */
public class FileTimerTask {

    public static void main(String[] args) {



        File file = new File(args[0]);

        if(!file.exists()) {
            System.out.println(args[0] + " ist weder eine Datei noch eine Directory");
            return;
        }

        Timer timer = new Timer();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                timer.cancel();
                System.out.println("Das Programm wurde beendet.");
            }
        });

        TimerTask timerTask = new TimerTask() {
            private long currentSize = getSize(file);

            @Override
            public void run() {
                if(!file.exists()) {
                    System.out.println(args[0] + " ist nicht mehr vorhanden");
                    timer.cancel();
                    return;
                }

                long tmpSize = getSize(file);
                if(tmpSize != currentSize) {
                    currentSize = tmpSize;
                    System.out.println("Die Größe hat sich auf " + convertSize(currentSize) + " geändert.");
                }
            }
        };

        timer.schedule(timerTask, 0, 1000);
    }

    /**
     * Function to get the size of both files and dirs
     * @param input file or dir as file object
     * @return size of the file or dir
     */
    private static long getSize(File input) {
        if(input.isFile()) return input.length();
        long size = 0;
        File[] files = input.listFiles();

        for(File f : files) {
            if(f.isFile()) {
                size += f.length();
            } else {
                size += getSize(f);
            }
        }
        return size;
    }

    /**
     * Converts the size to a string
     * @param i size in bytes
     * @return size as string with unit
     */
    private static String convertSize(long i) {
        double b = i;
        double k = b / 1024;
        double m = k / 1024;
        double g = m / 1024;
        double t = g / 1024;
        double p = t / 1024;

        if(p > 1) return Math.floor(p * 100) / 100 + " PiB";
        if(t > 1) return Math.floor(t * 100) / 100 + " TiB";
        if(g > 1) return Math.floor(g * 100) / 100 + " GiB";
        if(m > 1) return Math.floor(m * 100) / 100 + " MiB";
        if(k > 1) return Math.floor(k * 100) / 100 + " KiB";
        return b + " B";
    }

}
