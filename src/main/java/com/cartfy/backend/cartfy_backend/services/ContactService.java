package com.cartfy.backend.cartfy_backend.services;

import java.util.Collection;

import com.cartfy.backend.cartfy_backend.entities.Contact;
import com.cartfy.backend.cartfy_backend.models.requests.ContactCreateRequest;
import com.cartfy.backend.cartfy_backend.models.requests.ContactUpdateRequest;
import com.cartfy.backend.cartfy_backend.models.responses.OperationResponse;
import com.cartfy.backend.cartfy_backend.models.responses.RetrieveResponse;

public interface ContactService {
    
    public OperationResponse save(ContactCreateRequest contact);
    public OperationResponse update(long idContact, ContactUpdateRequest contact);
    public OperationResponse delete(long idContact);    
    public RetrieveResponse<Collection<Contact>> getAllContactsByUser(long idUser);
}
