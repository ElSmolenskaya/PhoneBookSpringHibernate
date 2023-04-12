package ru.academits.hello.dao;

import ru.academits.model.Contact;

import java.util.List;

public interface ContactDao extends GenericDao<Contact, Long> {
    List<Contact> getAllContacts();

    List<Contact> getContacts(String term);

    List<Contact> findByPhone(String phone);

    void delete(long id);
}