package util;

import java.io.*;
import java.nio.charset.Charset;
import java.time.Duration;

/**
 * @author kevin
 * @version $Id: SubTitleFileHandler.java, v 0.1 2019年08月10日 10:25 PM kevin Exp $
 */
public class SubTitleFileHandler {
    /**
     * file full path
     */
    String             filePath;
    String             fileName;
    String             newFileName;
    File               subtitleFile;
    OutputStreamWriter writer;
    InputStreamReader  reader;
    BufferedReader     bufferedReader;

    public SubTitleFileHandler(String filePath, String fileName, String newFileName, Charset sourceFileCharset,
                               Charset targetFileCharset) throws Exception {
        if (fileName.equals(newFileName)) {
            throw new RuntimeException("output filename could not be the same as the original one");
        }
        this.filePath = filePath;
        this.newFileName = newFileName;
        this.fileName = fileName;
        subtitleFile = new File(filePath, fileName);
        writer = new OutputStreamWriter(new FileOutputStream(new File(filePath, newFileName)), targetFileCharset.name());
        reader = new InputStreamReader(new FileInputStream(new File(filePath, fileName)), sourceFileCharset.name());
        bufferedReader = new BufferedReader(reader);
    }

    /**
     * 根据偏移量循环处理字幕文件
     *
     * @param ms
     * @throws IOException
     */
    public void handelFile(int ms) throws IOException {
        if (ms == 0) {
            return;
        }
        String line = bufferedReader.readLine();
        while (line != null) {
            processLine(line, ms);
            line = bufferedReader.readLine();
        }
        writer.flush();

        reader.close();
        bufferedReader.close();
        writer.close();
    }

    /**
     * 根据偏移量处理单行字幕
     *
     * @param line
     * @param milliSecs
     * @throws IOException
     */
    private void processLine(String line, int milliSecs) throws IOException {
        if (line.indexOf("-->") != -1 && milliSecs != 0) { // 这一行是时间轴

            // 将字幕显示的起始时间和结束时间分开。
            String timespan[] = line.split("-->");

            // 起始时间的小时、分、秒、毫秒分开。
            String begin[] = timespan[0].split("[:,]");

            // 重点：把起始时间的小时、分、秒、毫秒统统加在一起构造一个Duration。
            // plus方法是可串行的，就像StringBuffer的append。
            Duration beginTime =
                    Duration.ofHours(Long.parseLong(begin[0].trim())).plus(
                            Duration.ofMinutes(Long.parseLong(begin[1].trim()))).plus(
                            Duration.ofSeconds(Long.parseLong(begin[2].trim()))).plus(
                            Duration.ofMillis(Long.parseLong(begin[3].trim())));

            // 结束时间也如法炮制。
            String end[] = timespan[1].split("[:,]");

            Duration endTime =
                    Duration.ofHours(Long.parseLong(end[0].trim())).plus(
                            Duration.ofMinutes(Long.parseLong(end[1].trim()))).plus(
                            Duration.ofSeconds(Long.parseLong(end[2].trim()))).plus(
                            Duration.ofMillis(Long.parseLong(end[3].trim())));

            // 把这两个时间分别加上我们希望的偏移量，也就是向前（milliSecs为负）或者向后移动的毫秒数，
            // 得到两个新的Duration
            Duration newStart = beginTime.plusMillis(milliSecs);
            Duration newEnd = endTime.plusMillis(milliSecs);

            // pw是一个PrintWriter，把调整后的一行时间轴写入新的字幕文件。
            writer.write(parse(newStart) + " --> " + parse(newEnd) + "\r\n");

        } else {
            writer.write(line + "\r\n"); // 不是时间轴行，原样打印
        }
    }

    /**
     * 将一个时间段转化为srt字幕的标准时间格式
     *
     * @param d
     * @return
     */
    private String parse(Duration d) {
        long h = d.toHours(); // 得到小时数
        String sh = h < 10 ? "0" + h : "" + h; // 如果只有一位数，加上个0

        // 为了得到后面的分，秒，毫秒，我们要将小时减掉，否则取分钟的时候会连小时算进去
        d = d.minusHours(h);

        long min = d.toMinutes(); // 得到分钟
        String smin = min < 10 ? "0" + min : "" + min;
        d = d.minusMinutes(min); // 减掉分钟
        long s = d.getSeconds(); // 得到秒，注意这里是getSeconds，没有toSeconds方法
        String ss = s < 10 ? "0" + s : "" + s;
        d = d.minusSeconds(s); // 减掉秒
        long m = d.toMillis(); // 得到毫秒
        String sm = m < 10 ? "00" + m : (m < 100 ? "0" + m : "" + m);
        return sh + ":" + smin + ":" + ss + "," + sm;
    }
}