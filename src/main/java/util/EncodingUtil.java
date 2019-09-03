package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.charset.Charset;

/**
 * @author kevin
 * @version : EncodingUtil.java, v 0.1 2019年08月10日 16:28 kevin Exp $
 */
public class EncodingUtil {
    public static ObservableList<String> getSupportedEncodingList() {

        return FXCollections.observableArrayList(Charset.availableCharsets().keySet());
    }
}