package ru.melnikov.RestAPI_spring_boot.utils.personErrors;

public class PersonErrorsResponse { //Описываем поля, котрые будут отправляться при ошибке
    private String message;
    private long timeStamp;

    public PersonErrorsResponse(String message, long timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
