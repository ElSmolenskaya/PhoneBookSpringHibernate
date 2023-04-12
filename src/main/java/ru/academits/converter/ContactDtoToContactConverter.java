package ru.academits.converter;

import org.springframework.stereotype.Service;
import ru.academits.dto.ContactDto;
import ru.academits.model.Contact;

@Service
public class ContactDtoToContactConverter extends AbstractConverter<ContactDto, Contact> {
    @Override
    public Contact convert(ContactDto source) {
        Contact c = new Contact();

        c.setId(source.getId());
        c.setFirstName(source.getFirstName());
        c.setLastName(source.getLastName());
        c.setPhone(source.getPhone());

        return c;
    }
}
