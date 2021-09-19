package com.example.piktomowafraglist;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements SelectedCategoryFragment.SelectedCategoryFragmentListener,
                    CategoryFragment.OnFragmentInteractionListener,
                    CategoryFragment.CategoryFragmentListener,
                    StripeFragment.OnFragmentInteractionListener,
                    SelectedCategoryFragment.OnFragmentInteractionListener{

    protected CategoryFragment categoryFragment;
    protected StripeFragment stripeFragment;
    protected SelectedCategoryFragment selectedCategoryFragment;
    Toolbar toolbar;
    protected Menu globalMenuToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categoryFragment = new CategoryFragment();
        stripeFragment = new StripeFragment();
        selectedCategoryFragment = new SelectedCategoryFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, categoryFragment)
                .replace(R.id.fragment2, stripeFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        globalMenuToolbar = menu;

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){

        if (selectedCategoryFragment.category == 0){
            globalMenuToolbar.findItem(R.id.add_icon).setVisible(false);
        }
        else {
            globalMenuToolbar.findItem(R.id.add_icon).setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_icon:
                Intent add = new Intent(this, addActivity.class);
                add.putExtra("category", selectedCategoryFragment.category);
                startActivityForResult(add, 2);
                return true;
            case R.id.paint_icon:
                Intent draw = new Intent(this, Drawing.class);
                startActivity(draw);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                data = getIntent();
                selectedCategoryFragment.category = data.getIntExtra("category", 8);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, selectedCategoryFragment)
                        .replace(R.id.fragment2, stripeFragment).commit();

                ArrayList<SingleItem> list;
                String json;

                if (selectedCategoryFragment.category == 1){
                    list = SelectedCategoryFragment.alphabet;
                    json = SelectedCategoryFragment.ALPHABET_JSON;
                }else if (selectedCategoryFragment.category == 2){
                    list = SelectedCategoryFragment.numbers;
                    json = SelectedCategoryFragment.NUMBERS_JSON;
                }else if (selectedCategoryFragment.category == 3){
                    list = SelectedCategoryFragment.colors;
                    json = SelectedCategoryFragment.COLORS_JSON;
                }else if (selectedCategoryFragment.category == 4){
                    list = SelectedCategoryFragment.shapes;
                    json = SelectedCategoryFragment.SHAPES_JSON;
                }else if (selectedCategoryFragment.category == 5){
                    list = SelectedCategoryFragment.directions;
                    json = SelectedCategoryFragment.DIRECTIONS_JSON;
                }else if (selectedCategoryFragment.category == 6){
                    list = SelectedCategoryFragment.questions;
                    json = SelectedCategoryFragment.QUESTIONS_JSON;
                }else if (selectedCategoryFragment.category == 7){
                    list = SelectedCategoryFragment.persons;
                    json = SelectedCategoryFragment.PERSONS_JSON;
                }else if (selectedCategoryFragment.category == 8){
                    list = SelectedCategoryFragment.wlasne_pikto;
                    json = SelectedCategoryFragment.WLASNE_PIKTO_JSON;
                }else {
                    list = SelectedCategoryFragment.wlasne_pikto;
                    json = SelectedCategoryFragment.WLASNE_PIKTO_JSON;
                }

                selectedCategoryFragment.gridViewSelect.setAdapter(new CategoryAdapter(this,
                        list, json));
            }
        }
    }

    @Override
    public void onBackPressed(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, categoryFragment)
                .replace(R.id.fragment2, stripeFragment).commit();

        globalMenuToolbar.findItem(R.id.add_icon).setVisible(false);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void ChangeStripeOnStripeFragment(SingleItem clicked) {
        stripeFragment.updateStripe(clicked);
    }

    @Override
    public void Toolbar() {
        globalMenuToolbar.findItem(R.id.add_icon).setVisible(true);
    }

    @Override
    public void ViewCategory(int data) {
        selectedCategoryFragment.category = data;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, selectedCategoryFragment)
                .replace(R.id.fragment2, stripeFragment).commit();
    }

}
