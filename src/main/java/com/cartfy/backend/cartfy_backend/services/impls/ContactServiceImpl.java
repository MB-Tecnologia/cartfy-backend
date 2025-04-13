package com.cartfy.backend.cartfy_backend.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cartfy.backend.cartfy_backend.entities.Contact;
import com.cartfy.backend.cartfy_backend.entities.User;
import com.cartfy.backend.cartfy_backend.models.requests.ContactCreateRequest;
import com.cartfy.backend.cartfy_backend.models.requests.ContactUpdateRequest;
import com.cartfy.backend.cartfy_backend.models.responses.OperationResponse;
import com.cartfy.backend.cartfy_backend.models.responses.RetrieveResponse;
import com.cartfy.backend.cartfy_backend.repository.ContactRepository;
import com.cartfy.backend.cartfy_backend.services.ContactService;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Collection;



@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepository _contactRepo;

    public OperationResponse save(ContactCreateRequest contactRequest){
        try {
            
            LocalDateTime now = LocalDateTime.now();

            var user = new User();
            user.setId(contactRequest.idUser());


            var contactUser = new User();
            contactUser.setId(contactRequest.idContactUser());

            var contact = Contact.builder()
                .usuario(user)
                .usuarioContato(contactUser)
                .apelido(contactRequest.nickname())                
                .dtInclusao(now)
                .dtAlteracao(now)
                .build();
            
            _contactRepo.save(contact);

            return new OperationResponse(true, "Contato salva com sucesso");
                
        
        } catch(Exception e){
            e.printStackTrace();    
            return new OperationResponse(false, "Erro: " + e.toString());
        }
        
    }
            

    public OperationResponse update(long idContact, ContactUpdateRequest contactUpdateRequest){
        try {
            Contact contactDB = _contactRepo.getReferenceById(idContact); 
            
            LocalDateTime now = LocalDateTime.now();

            contactDB.setApelido(contactUpdateRequest.nickname());
            contactDB.setDtAlteracao(now);
            
            _contactRepo.save(contactDB);

            return new OperationResponse(true, "Contato atualizado com sucesso");

        } catch(Exception e){
            e.printStackTrace();    
            return new OperationResponse(false, "Erro: " + e.toString());
        }
    }

    public OperationResponse delete(long idContact){
        
        try {
            _contactRepo.deleteById(idContact);
            
            return new OperationResponse(true, "Contato deletado com sucesso");

        } catch (Exception e) {
            
            return new OperationResponse(false, "Erro: " + e.toString());
        }        
    }

    @Override
    public RetrieveResponse<Collection<Contact>> getAllContactsByUser(long idUser) {
        try {
            var resp = _contactRepo.findByUsuario_Id(idUser);
            

            return new RetrieveResponse<Collection<Contact>>(true, "", Optional.of(resp));
            
        } catch (Exception e) {
            
            return new RetrieveResponse<Collection<Contact>>(false, "Erro", null);
        }
                
    }
    
}
