package nccu;

import javafx.scene.input.DataFormat;

public class SetDataFormat {
    static DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

    public static DataFormat getDataFormat() {
        return SERIALIZED_MIME_TYPE;
    }
}
