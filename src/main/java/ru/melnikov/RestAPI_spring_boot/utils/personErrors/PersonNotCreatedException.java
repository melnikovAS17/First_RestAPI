package ru.melnikov.RestAPI_spring_boot.utils.personErrors;

public class PersonNotCreatedException extends RuntimeException{
    //Пишем конструктор тк в родителе есть необх поля, а в мдели валидация
    //текст которой будет вставляться в текст ошибки
    public PersonNotCreatedException(String message){
        super(message);
    }
}
