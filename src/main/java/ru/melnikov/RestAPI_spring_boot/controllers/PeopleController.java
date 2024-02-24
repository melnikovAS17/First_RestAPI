package ru.melnikov.RestAPI_spring_boot.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.melnikov.RestAPI_spring_boot.models.Person;
import ru.melnikov.RestAPI_spring_boot.services.PersonService;
import ru.melnikov.RestAPI_spring_boot.utils.personErrors.PersonErrorsResponse;
import ru.melnikov.RestAPI_spring_boot.utils.personErrors.PersonNotCreatedException;
import ru.melnikov.RestAPI_spring_boot.utils.personErrors.PersonNotFoundException;

import java.util.List;

@RestController
public class PeopleController {

    private final PersonService personService;
    @Autowired
    public PeopleController(PersonService personService) {
        this.personService = personService;
    }
    @GetMapping("/show")
    public List<Person> showAll(){
        return personService.showAllPeople(); //Jackson сам конвертирует данные - входит в spring-web зависимость
    }

    @GetMapping("/show/{id}")
    public Person showOne(@PathVariable("id") int id){
        return personService.showPersonInfo(id);
    }

    @PostMapping("/show")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Person person, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            //**********************************************
            //(В локальном проекте) Вынес логику по отлову ошибки и созданию сообщения ошибки в отдельный класс в services
            //**********************************************
            //Создаем строку с нашей ошибкой
            StringBuilder errorsMsg = new StringBuilder();
            //Добавляем ошибки из bindingResult в лист ошибок
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for(FieldError f: errorList){
                //конкотенируем сообщение об ошибке
                errorsMsg.append(f.getField())//получаем строку на каком поле была ощибка
                        .append(" - ").append(f.getDefaultMessage())//Сообщение ошибки (описываем в эксеп-хендлере)
                        .append(";");
            }
            //Передаем сообщение об ошибке в конструрктор нашего кастомного исключения
            throw new PersonNotCreatedException(errorsMsg.toString());
        }
        personService.save(person);

        return ResponseEntity.ok(HttpStatus.OK); // - 200 status
    }



    //Создвем метод чтобы отлавливать наше кастомное исключение
    @ExceptionHandler
    public ResponseEntity<PersonErrorsResponse> handleException(PersonNotFoundException e){
        //Создаём наш объект ошибки и передаем ему в конструктор сообщение ошибки и время в млс
        PersonErrorsResponse personErrorsResponse = new PersonErrorsResponse(
                "Person with this id wasn't found",
                System.currentTimeMillis());
        //возвращаем наш объект со статусом ответа http
        return new ResponseEntity<>(personErrorsResponse, HttpStatus.NOT_FOUND); //NOT_FOUND - 404 status
    }

    @ExceptionHandler
    public ResponseEntity<PersonErrorsResponse> handleException(PersonNotCreatedException e){
        PersonErrorsResponse personErrorsResponse = new PersonErrorsResponse(
                //Сообщение ощибки будет получено из bindingResult
                //в который ощибки будут добавлены валидатором и текст будет указанный в модели
                //пример: @NotEmpty(message = "")
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(personErrorsResponse, HttpStatus.BAD_REQUEST); //- 400 status bad request
    }
}
