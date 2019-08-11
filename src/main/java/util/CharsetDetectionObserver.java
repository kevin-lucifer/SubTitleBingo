
package util;

import util.jchardet.nsICharsetDetectionObserver;

/**
 * @author kevin
 * @version : CharsetDetectionObserver.java, v 0.1 2019年08月10日 15:14 kevin Exp $
 */
public class CharsetDetectionObserver implements nsICharsetDetectionObserver {
    private String guessResult = null;

    @Override
    public void Notify(String s) {
        guessResult = s;
    }

    /**
     * Getter method for property <tt>guessResult</tt>.
     *
     * @return property value of guessResult
     */
    public String getGuessResult() {
        return guessResult;
    }
}
