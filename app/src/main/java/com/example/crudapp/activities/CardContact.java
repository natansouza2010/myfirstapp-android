package com.example.crudapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.crudapp.R;
import com.example.crudapp.dao.ContactDAO;
import com.example.crudapp.model.Contact;
import com.google.android.material.snackbar.Snackbar;

public class CardContact extends AppCompatActivity {

    private TextView txtName;
    private TextView txtPhone;
    private TextView txtEmail;
    private TextView txtAdress;
    private Button btnUpdate;
    private Button btnDelete;
    private ContactDAO contactDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_contact);
        bindingCompoentCardContact();


        //DAO
        contactDAO = new ContactDAO(getApplicationContext());

        //Recuperar Dados do Contato
        Bundle dados = getIntent().getExtras();
        Contact contact =  (Contact) dados.getSerializable("contact");

        //Seta os valores do contato selecionado no card
        txtName.setText(contact.getName());
        txtPhone.setText("Telefone : " + contact.getPhone());
        txtAdress.setText("Endereço : " + contact.getAdress());
        txtEmail.setText( "Email : " +contact.getEmail());



        //Events
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddContact.class);
                intent.putExtra("contact", contact);
                startActivity(intent);
                finish();

//                Snackbar.make(view, "Contato será Alterado", Snackbar.LENGTH_SHORT).setAction("Confirmar",null).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(CardContact.this);
                dialog.setTitle("Confirmar exclusão");
                dialog.setMessage("Deseja excluir o contato: " + contact.getName() + " ?");
                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        contactDAO.delete(contact.getId());
                        finish();
                    }
                });
                dialog.setNegativeButton("Não", null);

                //exibe o Dialog
                dialog.create();
                dialog.show();

//                contactDAO.delete(contact.getId());
//                Snackbar.make(view, "Contato será deletado", Snackbar.LENGTH_SHORT).setAction("Excluir",null).show();
            }
        });
    }



    private void bindingCompoentCardContact(){
        txtName = findViewById(R.id.txtNomeFromCard);
        txtPhone = findViewById(R.id.txtTelefoneFromCard);
        txtEmail = findViewById(R.id.txtEmailFromCard);
        txtAdress = findViewById(R.id.txtEnderecoFromCard);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

    }

}