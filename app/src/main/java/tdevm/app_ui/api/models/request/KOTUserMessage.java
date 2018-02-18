package tdevm.app_ui.api.models.request;

/**
 * Created by Tridev on 18-02-2018.
 */

public class KOTUserMessage {

    private int kot_id;
    private String kot_message;


    public KOTUserMessage(int kot_id, String kot_message) {
        this.kot_id = kot_id;
        this.kot_message = kot_message;
    }

    public int getKot_id() {
        return kot_id;
    }

    public void setKot_id(int kot_id) {
        this.kot_id = kot_id;
    }

    public String getKot_message() {
        return kot_message;
    }

    public void setKot_message(String kot_message) {
        this.kot_message = kot_message;
    }
}
