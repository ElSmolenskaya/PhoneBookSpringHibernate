package ru.academits.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.academits.converter.ContactDtoToContactConverter;
import ru.academits.converter.ContactToContactDtoConverter;
import ru.academits.dto.ContactDto;
import ru.academits.model.ContactValidation;
import ru.academits.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/phoneBook/rpc/api/v1")
public class PhoneBookController {
    private static final Logger logger = LoggerFactory.getLogger(PhoneBookController.class);

    private final ContactService contactService;
    private final ContactDtoToContactConverter contactDtoToContactConverter;
    private final ContactToContactDtoConverter contactToContactDtoConverter;

    public PhoneBookController(ContactService contactService, ContactDtoToContactConverter contactDtoToContactConverter, ContactToContactDtoConverter contactToContactDtoConverter) {
        this.contactService = contactService;
        this.contactDtoToContactConverter = contactDtoToContactConverter;
        this.contactToContactDtoConverter = contactToContactDtoConverter;
    }

    @RequestMapping(value = "getAllContacts", method = RequestMethod.GET)
    @ResponseBody
    public List<ContactDto> getAllContacts() {
        logger.info("called method getAllContacts");
        return contactToContactDtoConverter.convert(contactService.getAllContacts());
    }

    @RequestMapping(value = "getContacts", method = RequestMethod.POST)
    @ResponseBody
    public List<ContactDto> getContacts(@RequestBody(required = false) String term) {
        logger.info("called method getContacts");
        return contactToContactDtoConverter.convert(contactService.getContacts(term));
    }

    @RequestMapping(value = "addContact", method = RequestMethod.POST)
    @ResponseBody
    public ContactValidation addContact(@RequestBody ContactDto contact) {
        return contactService.addContact(contactDtoToContactConverter.convert(contact));
    }

    @RequestMapping(value = "deleteContacts", method = RequestMethod.POST)
    @ResponseBody
    public String deleteContacts(@RequestBody String idsToDeleteInString) {
        int[] ids = null;

        if (idsToDeleteInString != null && !idsToDeleteInString.isEmpty()) {
            ids = Arrays.stream(idsToDeleteInString.split(",")).mapToInt(Integer::parseInt).toArray();
        }

        return contactService.deleteContacts(ids);
    }
}