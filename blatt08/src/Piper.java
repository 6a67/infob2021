import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Program that takes a regular expression as input and reads from System.in; It then outputs which
 * lines match that expression and how often it is matched per line
 */
public class Piper {

    public static void main(String[] args) {
        DecoReader decoReader = new DecoReader(new InputStreamReader(System.in), args[0]);

        String line = null;
        try {
            line = decoReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(line != null) {
            int matches = decoReader.getAmountOfMatches();

            if(matches >= 1) {
                System.out.printf("%-32s","Line " + decoReader.getLineNumber() + " with " + matches + String.format(" match%s:", (matches > 1) ? "es" : ""));
                System.out.println(line);
            }

            try {
                line = decoReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
