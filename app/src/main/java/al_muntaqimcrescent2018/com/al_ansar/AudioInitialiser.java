package al_muntaqimcrescent2018.com.al_ansar;

/**
 * Created by Imran on 09-02-2018.
 */

public class AudioInitialiser {

    String description;
    String date;
    String audioUri;

    public AudioInitialiser() {
    }

    public AudioInitialiser(String description, String date, String audioUri) {
        this.description = description;
        this.date = date;
        this.audioUri = audioUri;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getAudioUri() {
        return audioUri;
    }
}
