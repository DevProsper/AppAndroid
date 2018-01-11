package com.example.gestiondemenu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.gestiondemenu.model.DateAdapter;
import com.example.gestiondemenu.model.Evenement;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, DateAdapter.DateAdapterCB {

    private Calendar calendar;
    private ArrayList<Evenement> evenementArrayList;
    private DateAdapter adapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();
        evenementArrayList = new ArrayList<>();
        adapter = new DateAdapter(evenementArrayList, this);

        recyclerView = (RecyclerView)findViewById(R.id.recy);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //jeu des donées
        calendar.add(Calendar.MONTH, -51);
        evenementArrayList.add(new Evenement(calendar.getTime(), "Date de noêl"));
        calendar.add(Calendar.MONTH, 40);
        evenementArrayList.add(new Evenement(calendar.getTime(), "Date de bonana"));
        calendar.add(Calendar.MONTH, 29);
        evenementArrayList.add(new Evenement(calendar.getTime(), "Joyeux anniversaire"));
        calendar.add(Calendar.MONTH, 12);
        evenementArrayList.add(new Evenement(calendar.getTime(), "Pourquoi tous ce bruit ?"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0, "Menu 1").setIcon(R.drawable.ic_action_name)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0,2,0, "Menu 2");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1){
            Toast.makeText(this, "Menu 1", Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == 2){
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR)
                    , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dateOfMonth) {
        calendar.set(year, month, dateOfMonth);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, calendar.get(Calendar.HOUR_OF_DAY)
                //true pour afficher le format de 24h et false pour deux heures
                ,calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        asDescription();
    }

    public void asDescription(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Description");
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Evenement evenement = new Evenement(calendar.getTime(), input.getText().toString());
                evenementArrayList.add(0,evenement);
                adapter.notifyItemInserted(0);
            }
        });
        builder.setNegativeButton("Annuler", null);
        builder.show();
    }

    @Override
    public void onEvenementClick(Evenement evenement, DateAdapter.MyViewHolder myViewHolder) {

        //VERSION DE TRANSITION
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra(DetailActivity.EVENEMENT_EXTRA, evenement);

        //Pour transmettre des composants graphique Support.v4
        Pair<View, String> paire = new Pair<>((View) myViewHolder.iv, "ImageTransition");
        Pair<View, String> paire2 = new Pair<>((View) myViewHolder.tv_description, "DescTransition");

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, paire, paire2);

        //Transmet le bundle a partir de nos options
        startActivity(i, options.toBundle());
        //VERSION DE SUPPRESSION
        //Toast.makeText(this, evenement.getDescription(), Toast.LENGTH_SHORT).show();
        //int positionElement = evenementArrayList.indexOf(evenement);
        //evenementArrayList.remove(evenement);
        //adapter.notifyDataSetChanged();
        //adapter.notifyItemRemoved(positionElement);
    }
}
