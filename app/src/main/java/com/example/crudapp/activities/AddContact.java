package com.example.crudapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crudapp.R;
import com.example.crudapp.dao.ContactDAO;
import com.example.crudapp.model.Contact;
import com.google.android.material.textfield.TextInputEditText;

public class AddContact extends AppCompatActivity {

    private TextView txtAddOrUpdate;
    private Button btnSaveOrUpdateContact;
    private TextInputEditText inputName;
    private TextInputEditText inputEmail;
    private TextInputEditText inputPhone;
    private TextInputEditText inputAdress;
    private ContactDAO contactDAO;
    private Contact contactFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        bindingComponentsAddContactActivity();
        contactDAO = new ContactDAO(getApplicationContext());

        Bundle dados = getIntent().getExtras();
        if(dados != null){
            contactFromIntent=(Contact) dados.getSerializable("contact");
            if(contactFromIntent != null) {
                txtAddOrUpdate.setText("Alterar Contato");
                inputName.setText(contactFromIntent.getName());
                inputPhone.setText(contactFromIntent.getPhone());
                inputEmail.setText(contactFromIntent.getEmail());
                inputAdress.setText(contactFromIntent.getAdress());
                btnSaveOrUpdateContact.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String name = inputName.getText().toString();
                        String email = inputEmail.getText().toString();
                        String phone = inputPhone.getText().toString();
                        String adress = inputAdress.getText().toString();
                        Contact newCont = new Contact(name,phone,adress,email);
                        newCont.setId(contactFromIntent.getId());
                        if(validateInputs(name,email,phone,adress)){
                            contactDAO.update(newCont);
                            Toast.makeText(getApplicationContext(), "Contato Alterado !",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Contato não foi salvo !",Toast.LENGTH_SHORT).show();
                        }
                    }



                });



            }
        }else{


        btnSaveOrUpdateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String phone = inputPhone.getText().toString();
                String adress = inputAdress.getText().toString();

                Contact cont = new Contact(name,phone,adress, email);
                if(validateInputs(name,email,phone,adress)){
                    if(contactFromIntent == null) {
                        contactDAO.save(cont);
                        Toast.makeText(getApplicationContext(), "Contato Salvo !",Toast.LENGTH_SHORT).show();
                    }
                    inputName.setText("");
                    inputEmail.setText("");
                    inputAdress.setText("");
                    inputPhone.setText("");
                    finish();



                }else{
                    Toast.makeText(getApplicationContext(), "Contato não foi salvo !",Toast.LENGTH_SHORT).show();
                }



            }
        });
        }
    }






    public boolean validateInputs(String name, String email, String telefone, String endereco){
        if(name == null || name.equals("")){
            Toast.makeText(getApplicationContext(),"Campo nome está vazio" ,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(email == null || email.equals("")){
            Toast.makeText(getApplicationContext(),"Campo email está vazio" ,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(telefone == null || telefone.equals("")){
            Toast.makeText(getApplicationContext(),"Campo telefone está vazio" ,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(endereco == null || endereco.equals("")){
            Toast.makeText(getApplicationContext(),"Campo endereco está vazio" ,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;





    }






    public void bindingComponentsAddContactActivity(){
        txtAddOrUpdate = findViewById(R.id.txtAddOrUpdate);
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPhone = findViewById(R.id.inputPhone);
        inputAdress = findViewById(R.id.inputAdress);
        btnSaveOrUpdateContact = findViewById(R.id.btnSaveOrUpdateContact);
    }
}