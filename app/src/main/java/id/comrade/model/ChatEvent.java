package id.comrade.model;

import java.util.List;

public class ChatEvent extends Chat {
    private List<Event> mEvent;

    public ChatEvent(String message, String date, String time, int status, String uidSender,
                     String uidRecevier, List<Event> event) {
        super(message, date, time, status, uidSender, uidRecevier);
        this.mEvent = event;
    }



        public List<Event> getEvent() {
            return mEvent;
        }

        public void setEvent(List<Event> mEvent) {
            this.mEvent = mEvent;
        }

}
