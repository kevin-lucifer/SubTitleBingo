package util;

import util.jchardet.nsDetector;
import util.jchardet.nsPSMDetector;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author kevin
 * @version $Id: FileEncodeDetector.java, v 0.1 2019年08月10日 10:22 PM kevin Exp $
 */
public class FileEncodeDetector {

    public static List<Charset> guessCharset(File file) throws IOException {
        //HtmlCharsetDetector

        byte[] buf = new byte[1024];
        int len;
        boolean done = false, found = false;
        boolean isAscii = true;
        nsDetector det = new nsDetector(nsPSMDetector.ALL);

        CharsetDetectionObserver charsetDetectionObserver = new CharsetDetectionObserver();

        det.Init(charsetDetectionObserver);

        BufferedInputStream imp = new BufferedInputStream(new FileInputStream(file));

        while ((len = imp.read(buf, 0, buf.length)) != -1) {

            // Check if the stream is only ascii.
            if (isAscii) { isAscii = det.isAscii(buf, len); }

            // DoIt if non-ascii and not done yet.
            if (!isAscii && !done) { done = det.DoIt(buf, len, false); }
        }
        det.DataEnd();

        if (isAscii) {
            System.out.println("CHARSET = ASCII");
            found = true;
            return Arrays.asList(Charset.forName("ASCII"));
        }

        if (!found) {
            String prob[] = det.getProbableCharsets();
            for (int i = 0; i < prob.length; i++) {
                System.out.println("Probable Charset = " + prob[i]);
            }

            List<String> charSetNameList = Arrays.asList(prob);
            if (charSetNameList == null || charSetNameList.isEmpty()) {
                return Arrays.asList(Charset.defaultCharset());
            }

            charSetNameList.sort(Comparator.comparing(Function.identity()));
            return charSetNameList.stream().map(chartSetName -> Charset.forName(chartSetName)).collect(Collectors.toList());
        }

        return null;
    }
}