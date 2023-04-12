package ru.academits.service;

import org.springframework.stereotype.Service;
import ru.academits.hello.dao.ContactDao;
import ru.academits.model.Contact;
import ru.academits.model.ContactValidation;

import java.util.List;
import java.util.Objects;

@Service
public class ContactService {
    private final ContactDao contactDao;

    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    private boolean isExistContactWithPhone(String phone) {
        List<Contact> contactList = contactDao.findByPhone(phone);
        return !contactList.isEmpty();
    }

    public ContactValidation validateContact(Contact contact) {
        ContactValidation contactValidation = new ContactValidation();
        contactValidation.setValid(true);
        if (contact.getFirstName().isEmpty()) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Имя должно быть заполнено.");
            return contactValidation;
        }

        if (contact.getLastName().isEmpty()) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Фамилия должно быть заполнено.");
            return contactValidation;
        }

        if (contact.getPhone().isEmpty()) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Телефон должно быть заполнено.");
            return contactValidation;
        }

        if (isExistContactWithPhone(contact.getPhone())) {
            contactValidation.setValid(false);
            contactValidation.setError("Номер телефона не должен дублировать другие номера в телефонной книге.");
            return contactValidation;
        }
        return contactValidation;
    }

    public ContactValidation addContact(Contact contact) {
        ContactValidation contactValidation = validateContact(contact);
        if (contactValidation.isValid()) {
            contactDao.create(contact);
        }
        return contactValidation;
    }

    public String deleteContacts(int[] ids) {
        int deletedContactsCount = 0;

        if (ids != null) {
            for (int id : ids) {
                contactDao.delete(id);

                deletedContactsCount++;
            }
        }

        return deletedContactsCount > 0
                ? "Количество удаленных контактов: " + deletedContactsCount
                : "Ни один контакт не был удален";
    }

    public List<Contact> getAllContacts() {
        return contactDao.getAllContacts();
    }

    public List<Contact> getContacts(String term) {
        return contactDao.getContacts(term);
    }
}