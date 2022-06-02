package com.example.crudapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.crudapp.R;
import com.example.crudapp.adapter.ContactAdapter;
import com.example.crudapp.dao.ContactDAO;
import com.example.crudapp.helpers.DBHelper;
import com.example.crudapp.model.Contact;
import com.example.crudapp.helpers.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private List<Contact> contactList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ContactDAO dao;
    private EditText inputSearch;
    private ImageView btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Binding os componentes
        bindingComponentsActivityMain();

        //Action Fab
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddContact.class);
                startActivity(intent);
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Confirmar", null).show();
            }
        });
        //Listagem de contatos
        ContactDAO dao = new ContactDAO(getApplicationContext());

        //SUBSTITUIR POR DAO FINDALL;
        contactList = dao.findAll();


        //Configurar Adapter
        ContactAdapter adapterContact = new ContactAdapter(contactList);


        //Configurar Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterContact);



        //Adicionar Evento Recycler View
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getApplicationContext(), CardContact.class);
                            Contact cont = contactFromList(position);
                            intent.putExtra("contact", cont);
                            startActivity(intent);



//                            Toast
//                                    .makeText(getApplicationContext(), "Item Pressionado: " +
//                                            contactList.get(position).getName(), Toast.LENGTH_SHORT)
//                                    .show();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        }
        }


        ));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contactList.clear();
                contactList.addAll(dao.findAll());
                adapterContact.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputSearch.getText().toString();
                contactList.clear();
                if(input.equals("")){
                    contactList = dao.findAll();
                }else{
                    contactList = dao.findForName(input);
                }

                //Configurar Adapter
                ContactAdapter adapterContact = new ContactAdapter(contactList);

                //Configurar Recycler View
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapterContact);

            }
        });
        
        
        


    }




    @Override
    protected void onStart() {
        super.onStart();
        dao = new ContactDAO(getApplicationContext());
        contactList.clear();
        contactList = dao.findAll();
        //Configurar Adapter
        ContactAdapter adapterContact = new ContactAdapter(contactList);


        //Configurar Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterContact);

    }

    private Contact contactFromList(int position){
        Contact contact = contactList.get(position);
        return contact;
    }

    private void bindingComponentsActivityMain(){
        floatingActionButton = findViewById(R.id.fabAdd);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        btnSearch = findViewById(R.id.btnSearch);
        inputSearch = findViewById(R.id.inputSearch);
    }



}