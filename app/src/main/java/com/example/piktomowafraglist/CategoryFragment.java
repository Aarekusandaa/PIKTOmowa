package com.example.piktomowafraglist;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    private CategoryFragmentListener listener;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    protected GridView gridViewSelect;

    protected ArrayList<SingleItem> category = new ArrayList<>();
    protected static final String CATEGORY_JSON = "category.json";

    public interface CategoryFragmentListener{
        void ViewCategory (int data);
    }

    public CategoryFragment() {
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

        gridViewSelect = (GridView) view.findViewById(R.id.CategoryGrid);
        gridViewSelect.setAdapter(new CategoryAdapter(getContext(), category, CATEGORY_JSON));

        gridViewSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int cat = 0;

                if (position == 0)
                    cat = 1;
                else if (position == 1)
                    cat = 2;
                else if (position == 2)
                    cat = 3;
                else if (position == 3)
                    cat = 4;
                else if (position == 4)
                    cat = 5;
                else if (position == 5)
                    cat = 6;
                else if (position == 6)
                    cat = 7;
                else if (position == 7)
                    cat = 8;

                listener.ViewCategory(cat);
            }
        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        if (context instanceof CategoryFragmentListener) {
            listener = (CategoryFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CategoryFragmentListener");
        }

        if (category.size() == 0){
            category.add(new SingleItem("Alfabet", R.drawable.alfabet, false, null));
            category.add(new SingleItem("Cyfry", R.drawable.cyfry, false, null));
            category.add(new SingleItem("Kolory", R.drawable.kolory, false, null));
            category.add(new SingleItem("Kształty", R.drawable.ksztalty, false, null));
            category.add(new SingleItem("Kierunki", R.drawable.kierunki, false, null));
            category.add(new SingleItem("Pytania", R.drawable.pytania, false, null));
            category.add(new SingleItem("Osoby", R.drawable.osoby, false, null));
            category.add(new SingleItem("Własne Piktogramy", R.drawable.wlasnepikto, false, null));
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


class CategoryAdapter extends BaseAdapter {

    private Context mContext;
    private final ArrayList<SingleItem> values;
    private View view;
    private LayoutInflater layoutInflater;

    public CategoryAdapter(Context context, ArrayList<SingleItem> values, String json) {

        this.mContext = context;
        this.values = restoreFromJson(json, values, context);
    }

    public int getCount(){
        return values.size();
    }

    public Object getItem(int position){
        return null;
    }

    public long getItemId(int position){
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            view = new View(mContext);
            view = layoutInflater.inflate(R.layout.singleitem, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.SingleItemImg);
            TextView textView = (TextView) view.findViewById(R.id.SingleItemText);

            if ((values.get(position).image != null) && (values.get(position).path == null)){
                imageView.setImageResource(values.get(position).image);
                textView.setText(values.get(position).name);
            }else if ((values.get(position).image == null) && (values.get(position).path != null)){
                Bitmap bitmap = PicUtils.decodePicture(values.get(position).path, 60, 60);
                imageView.setImageBitmap(bitmap);
                textView.setText(values.get(position).name);
            }
        }
        return view;
    }

    public ArrayList<SingleItem> restoreFromJson (String fileJson, ArrayList<SingleItem> newValues,
                                                  Context context){
        FileInputStream inputStream;
        int DEFAULT_BUFFER_SIZE = 10000;
        Gson gson = new Gson();
        String readJson;

        try {

            inputStream = context.openFileInput(fileJson);
            FileReader reader = new FileReader(inputStream.getFD());
            char[] buf = new char[DEFAULT_BUFFER_SIZE];
            int n;
            StringBuilder builder = new StringBuilder();
            while ((n = reader.read(buf))>=0 ){
                String tmp = String.valueOf(buf);
                String substring = (n<DEFAULT_BUFFER_SIZE) ? tmp.substring(0, n) : tmp;
                builder.append(substring);
            }
            reader.close();
            readJson = builder.toString();
            Type collectionType = new TypeToken<ArrayList<SingleItem>>(){}.getType();
            ArrayList<SingleItem> o = gson.fromJson(readJson, collectionType);
            if (o != null){
                newValues.clear();
                for (SingleItem task : o){
                    newValues.add(task);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return newValues;
    }
    
}