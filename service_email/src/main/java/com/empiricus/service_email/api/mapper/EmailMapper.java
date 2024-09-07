package com.empiricus.service_email.api.mapper;

import com.empiricus.service_email.domain.dto.EmailDTOInput;
import com.empiricus.service_email.domain.dto.EmailDTOOutput;
import com.empiricus.service_email.domain.model.Email;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailMapper {

    private final ModelMapper mapper;

    public Page<EmailDTOOutput> toPageDTO(Page<Email> emails){
        return emails.map(email -> mapper.map(email, EmailDTOOutput.class));
    }

    public Email toModel(EmailDTOInput input){
        return mapper.map(input, Email.class);
    }

    public EmailDTOOutput toDto(Email email){
        return mapper.map(email, EmailDTOOutput.class);
    }
}
