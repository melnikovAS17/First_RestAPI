package ru.melnikov.RestAPI_spring_boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.melnikov.RestAPI_spring_boot.models.Person;
import ru.melnikov.RestAPI_spring_boot.repositories.PersonRepository;
import ru.melnikov.RestAPI_spring_boot.utils.personErrors.PersonNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> showAllPeople(){
       return personRepository.findAll();
    }

    public Person showPersonInfo(int id){
        //Получаем объект Person или выбрасываем собственное (кастомное) исключение
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }

}
