package ru.melnikov.RestAPI_spring_boot.utils.personErrors;

import java.util.function.Supplier;

//Создаем кастомное исключение (если человек не найден в метое findById())
public class PersonNotFoundException extends RuntimeException {
    //Ничего не пишем здесь тк описываем сообщение  - шибки в методе контроллера
}
