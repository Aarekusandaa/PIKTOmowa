package com.example.piktomowafraglist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import static android.content.Context.MODE_PRIVATE;

public class SelectedCategoryFragment extends Fragment {

    private SelectedCategoryFragmentListener listener;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    protected GridView gridViewSelect;

    protected int category = 0;
    String json;
    ArrayList<SingleItem> list;

    public static Gson AlphabetGson = new Gson();
    public static final String ALPHABET_JSON = "alphabet.json";
    public static ArrayList<SingleItem> alphabet = new ArrayList<>();
    public static Gson NumbersGson = new Gson();
    public static final String NUMBERS_JSON = "numbers.json";
    public static ArrayList<SingleItem> numbers = new ArrayList<>();
    public static Gson ColorsGson = new Gson();
    public static final String COLORS_JSON = "colors.json";
    public static ArrayList<SingleItem> colors = new ArrayList<>();
    public static Gson ShapesGson = new Gson();
    public static final String SHAPES_JSON = "shapes.json";
    public static ArrayList<SingleItem> shapes = new ArrayList<>();
    public static Gson DirectionsGson = new Gson();
    public static final String DIRECTIONS_JSON = "directions.json";
    public static ArrayList<SingleItem> directions = new ArrayList<>();
    public static Gson QuestionsGson = new Gson();
    public static final String QUESTIONS_JSON = "questions.json";
    public static ArrayList<SingleItem> questions = new ArrayList<>();
    public static Gson PersonsGson = new Gson();
    public static final String PERSONS_JSON = "persons.json";
    public static ArrayList<SingleItem> persons = new ArrayList<>();
    public static Gson Wlasne_piktoGson = new Gson();
    public static final String WLASNE_PIKTO_JSON = "wlasne_pikto.json";
    public static ArrayList<SingleItem> wlasne_pikto = new ArrayList<>();

    public interface SelectedCategoryFragmentListener{
        void ChangeStripeOnStripeFragment(SingleItem clicked);
        void Toolbar();
    }

    public SelectedCategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        listener.Toolbar();

        if (category == 1){
            json = ALPHABET_JSON;
            list = alphabet;
        }
        else if (category == 2){
            json = NUMBERS_JSON;
            list = numbers;
        }
        else if (category == 3){
            json = COLORS_JSON;
            list = colors;
        }
        else if (category == 4){
            json = SHAPES_JSON;
            list = shapes;
        }
        else if (category == 5){
            json = DIRECTIONS_JSON;
            list = directions;
        }
        else if (category == 6){
            json = QUESTIONS_JSON;
            list = questions;
        }
        else if (category == 7){
            json = PERSONS_JSON;
            list = persons;
        }
        else if (category == 8){
            json = WLASNE_PIKTO_JSON;
            list = wlasne_pikto;
        }
        else {
            json = ALPHABET_JSON;
            list = alphabet;
        }

        gridViewSelect = (GridView) view.findViewById(R.id.CategoryGrid);
        gridViewSelect.setAdapter(new CategoryAdapter(getActivity().
                getApplicationContext(), list, json));

        gridViewSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SingleItem clicked = null;

                if (position <= list.size()){
                    clicked = list.get(position);
                }
                listener.ChangeStripeOnStripeFragment(clicked);
            }
        });

        gridViewSelect.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {

                final SingleItem clicked = list.get(position);

                if (clicked.removable == false){
                    return false;
                }
                else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Usuń: " + clicked.name)
                            .setMessage("Czy na pewno chcesz usunąć piktogram '" + clicked.name + "'?")
                            .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (category == 1){
                                alphabet.remove(position);
                                saveTasksToJson(AlphabetGson, ALPHABET_JSON, 1);
                            }else if (category == 2){
                                numbers.remove(position);
                                saveTasksToJson(NumbersGson, NUMBERS_JSON, 2);
                            }else if (category == 3){
                                colors.remove(position);
                                saveTasksToJson(ColorsGson, COLORS_JSON, 3);
                            }else if (category == 4){
                                shapes.remove(position);
                                saveTasksToJson(ShapesGson, SHAPES_JSON, 4);
                            }else if (category == 5){
                                directions.remove(position);
                                saveTasksToJson(DirectionsGson, DIRECTIONS_JSON, 5);
                            }else if (category == 6){
                                questions.remove(position);
                                saveTasksToJson(QuestionsGson, QUESTIONS_JSON, 6);
                            }else if (category == 7){
                                persons.remove(position);
                                saveTasksToJson(PersonsGson, PERSONS_JSON, 7);
                            }else if (category == 8) {
                                wlasne_pikto.remove((position));
                                saveTasksToJson(Wlasne_piktoGson, WLASNE_PIKTO_JSON, 8);
                            }
                            gridViewSelect.setAdapter(new CategoryAdapter(getActivity()
                                    .getApplicationContext(), list, json));
                        }
                    }).setNegativeButton("Nie", null);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    return true;
                }

            }
        });

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void saveTasksToJson(Gson gson, String fileJson, int category){

        ArrayList<SingleItem> list;
        if (category == 1){
            list = SelectedCategoryFragment.alphabet;
        }else if (category == 2){
            list = SelectedCategoryFragment.numbers;
        }else if (category == 3){
            list = SelectedCategoryFragment.colors;
        }else if (category == 4){
            list = SelectedCategoryFragment.shapes;
        }else if (category == 5){
            list = SelectedCategoryFragment.directions;
        }else if (category == 6){
            list = SelectedCategoryFragment.questions;
        }else if (category == 7){
            list = SelectedCategoryFragment.persons;
        }else if (category == 8){
            list = SelectedCategoryFragment.wlasne_pikto;
        }else {
            list = SelectedCategoryFragment.wlasne_pikto;
        }
        String listJson = gson.toJson(list);
        FileOutputStream outputStream;
        try {
            outputStream = getContext().openFileOutput(fileJson, MODE_PRIVATE);
            FileWriter writer = new FileWriter(outputStream.getFD());
            writer.write(listJson);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        if (context instanceof SelectedCategoryFragmentListener) {
            listener = (SelectedCategoryFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CategoryFragmentListener");
        }

        if ((category == 1) && (alphabet.size() == 0)){
            alphabet.add(new SingleItem("A a", R.drawable.a, false, null));
            alphabet.add(new SingleItem("ą", R.drawable.aa, false, null));
            alphabet.add(new SingleItem("B b", R.drawable.b, false, null));
            alphabet.add(new SingleItem("C c", R.drawable.c, false, null));
            alphabet.add(new SingleItem("Ć ć", R.drawable.cc, false, null));
            alphabet.add(new SingleItem("D d", R.drawable.d, false, null));
            alphabet.add(new SingleItem("E e", R.drawable.e, false, null));
            alphabet.add(new SingleItem("ę", R.drawable.ee, false, null));
            alphabet.add(new SingleItem("F f", R.drawable.f, false, null));
            alphabet.add(new SingleItem("G g", R.drawable.g, false, null));
            alphabet.add(new SingleItem("H h", R.drawable.h, false, null));
            alphabet.add(new SingleItem("I i", R.drawable.i, false, null));
            alphabet.add(new SingleItem("J j", R.drawable.j, false, null));
            alphabet.add(new SingleItem("K k", R.drawable.k, false, null));
            alphabet.add(new SingleItem("L l", R.drawable.l, false, null));
            alphabet.add(new SingleItem("Ł ł", R.drawable.ll, false, null));
            alphabet.add(new SingleItem("M m", R.drawable.m, false, null));
            alphabet.add(new SingleItem("N n", R.drawable.n, false, null));
            alphabet.add(new SingleItem("Ń ń", R.drawable.nn, false, null));
            alphabet.add(new SingleItem("O o", R.drawable.o, false, null));
            alphabet.add(new SingleItem("Ó ó", R.drawable.oo, false, null));
            alphabet.add(new SingleItem("P p", R.drawable.p, false, null));
            alphabet.add(new SingleItem("R r", R.drawable.r, false, null));
            alphabet.add(new SingleItem("S s", R.drawable.s, false, null));
            alphabet.add(new SingleItem("Ś ś", R.drawable.ss, false, null));
            alphabet.add(new SingleItem("T t", R.drawable.t, false, null));
            alphabet.add(new SingleItem("U u", R.drawable.u, false, null));
            alphabet.add(new SingleItem("W w", R.drawable.w, false, null));
            alphabet.add(new SingleItem("Y y", R.drawable.y, false, null));
            alphabet.add(new SingleItem("Z z", R.drawable.z, false, null));
            alphabet.add(new SingleItem("Ż ż", R.drawable.zz, false, null));
            alphabet.add(new SingleItem("Ź ź", R.drawable.zzz, false, null));
        }

        else if ((category == 2) && (numbers.size() == 0)){
            numbers.add(new SingleItem("0 zero", R.drawable.zero, false, null));
            numbers.add(new SingleItem("1 jeden", R.drawable.jeden, false, null));
            numbers.add(new SingleItem("2 dwa", R.drawable.dwa, false, null));
            numbers.add(new SingleItem("3 trzy", R.drawable.trzy, false, null));
            numbers.add(new SingleItem("4 cztery", R.drawable.cztery, false, null));
            numbers.add(new SingleItem("5 pięć", R.drawable.piec, false, null));
            numbers.add(new SingleItem("6 sześć", R.drawable.szesc, false, null));
            numbers.add(new SingleItem("7 siedem", R.drawable.siedem, false, null));
            numbers.add(new SingleItem("8 osiem", R.drawable.osiem, false, null));
            numbers.add(new SingleItem("9 dziewięć", R.drawable.dziewiec, false, null));
        }

        else if ((category == 3) && (colors.size() == 0)){
            colors.add(new SingleItem("Zielony", R.drawable.zielony, false, null));
            colors.add(new SingleItem("Czerwony", R.drawable.czerwony, false, null));
            colors.add(new SingleItem("Niebieski", R.drawable.niebieski, false, null));
            colors.add(new SingleItem("Pomarańczowy", R.drawable.pomaranczowy, false, null));
            colors.add(new SingleItem("Brązowy", R.drawable.brazowy, false, null));
            colors.add(new SingleItem("Żółty", R.drawable.zolty, false, null));
            colors.add(new SingleItem("Różowy", R.drawable.rozowy, false, null));
            colors.add(new SingleItem("Fioletowy", R.drawable.fioletowy, false, null));
            colors.add(new SingleItem("Biały", R.drawable.bialy, false, null));
            colors.add(new SingleItem("Czarny", R.drawable.czarny, false, null));
        }

        else if ((category == 4) && (shapes.size() == 0)){
            shapes.add(new SingleItem("Kwadrat", R.drawable.kwadrat, false, null));
            shapes.add(new SingleItem("Trójkąt", R.drawable.trojkat, false, null));
            shapes.add(new SingleItem("Koło", R.drawable.kolo, false, null));
            shapes.add(new SingleItem("Prostokąt", R.drawable.prostokat, false, null));
            shapes.add(new SingleItem("Serce", R.drawable.serce, false, null));
            shapes.add(new SingleItem("Gwiazda", R.drawable.gwiazda, false, null));
        }

        else if ((category == 5) && (directions.size() == 0)){
            directions.add(new SingleItem("W górę", R.drawable.wgore, false, null));
            directions.add(new SingleItem("W dół", R.drawable.wdol, false, null));
            directions.add(new SingleItem("W lewo", R.drawable.wlewo, false, null));
            directions.add(new SingleItem("W prawo", R.drawable.wprawo, false, null));
        }

        else if ((category == 6) && (questions.size() == 0)){
            questions.add(new SingleItem("Co?", R.drawable.co, false, null));
            questions.add(new SingleItem("Kto?", R.drawable.kto, false, null));
            questions.add(new SingleItem("Ile?", R.drawable.ile, false, null));
            questions.add(new SingleItem("Jaki kolor?", R.drawable.jakikolor, false, null));
            questions.add(new SingleItem("Gdzie?", R.drawable.gdzie, false, null));
            questions.add(new SingleItem("Kiedy?", R.drawable.kiedy, false, null));
            questions.add(new SingleItem("O której godzinie?", R.drawable.oktorejgodzinie, false, null));
            questions.add(new SingleItem("Czyj?", R.drawable.czyj, false, null));
        }

        else if ((category == 7) && (persons.size() == 0)){
            persons.add(new SingleItem("Ja", R.drawable.ja, false, null));
            persons.add(new SingleItem("Ty", R.drawable.ty, false, null));
            persons.add(new SingleItem("On", R.drawable.on, false, null));
            persons.add(new SingleItem("Ona", R.drawable.ona, false, null));
            persons.add(new SingleItem("My", R.drawable.my, false, null));
            persons.add(new SingleItem("Wy", R.drawable.wy, false, null));
            persons.add(new SingleItem("Oni", R.drawable.oni, false, null));
            persons.add(new SingleItem("One", R.drawable.one, false, null));
            persons.add(new SingleItem("Oni", R.drawable.oni1, false, null));
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}

