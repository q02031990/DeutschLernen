package tiengduc123.com.deutschlernen;

import java.io.Serializable;

/**
 * Created by Dell on 12/3/2015.
 */
public class VideoObj implements Serializable {
    String nameVideo;
    String timeVideo;
    String key;

    public VideoObj(String nameVideo, String timeVideo, String key) {
        this.nameVideo = nameVideo;
        this.timeVideo = timeVideo;
        this.key = key;
    }
}
