package com.example.piktomowafraglist;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StripeFragment extends Fragment {

    protected GridView gridViewTalk;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<SingleItem> stripe = new ArrayList<>();
    int size = 0;

    public StripeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StripeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StripeFragment newInstance(String param1, String param2) {
        StripeFragment fragment = new StripeFragment();
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

        stripe.add(new SingleItem("", R.drawable.tlozdania, false, null));
        stripe.add(new SingleItem("", R.drawable.tlozdania, false, null));
        stripe.add(new SingleItem("", R.drawable.tlozdania, false, null));
        stripe.add(new SingleItem("", R.drawable.tlozdania, false, null));
        stripe.add(new SingleItem("", R.drawable.tlozdania, false, null));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stripe, container, false);

        gridViewTalk = (GridView) view.findViewById(R.id.ChooseGrid);
        gridViewTalk.setAdapter(new StripeAdapter(getActivity()
                .getApplicationContext(), stripe));

        ImageButton Delete = view.findViewById(R.id.DeleteButton);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (size > 5){
                    stripe.remove(size - 1);
                    --size;
                }
                else if (size <= 0){
                    Toast.makeText(getContext(), "Brak elementów do usunięcia",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    stripe.get(size - 1).image = R.drawable.tlozdania;
                    stripe.get(size - 1).name = "";
                    stripe.get(size - 1).removable = false;
                    -- size;
                }
                gridViewTalk.setAdapter(new StripeAdapter(getActivity()
                        .getApplicationContext(), stripe));
            }
        });
        Delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                stripe.clear();
                stripe.add(new SingleItem("", R.drawable.tlozdania, false, null));
                stripe.add(new SingleItem("", R.drawable.tlozdania, false, null));
                stripe.add(new SingleItem("", R.drawable.tlozdania, false, null));
                stripe.add(new SingleItem("", R.drawable.tlozdania, false, null));                                                                      //hgh
                stripe.add(new SingleItem("", R.drawable.tlozdania, false, null));
                size = 0;

                gridViewTalk.setAdapter(new StripeAdapter(getActivity()
                        .getApplicationContext(), stripe));
                return true;
            }
        });

        return view;
    }

    public void updateStripe(SingleItem clicked){
        if (size < 5){
            for (int i = 0; i < 5; i++)
                if (stripe.get(i).name.equals("")) {
                    stripe.get(i).image = clicked.image;
                    stripe.get(i).path = clicked.path;
                    stripe.get(i).name = clicked.name;
                    stripe.get(i).removable = true;
                    ++ size;
                    break;
                }
        }else {
            stripe.add(new SingleItem(clicked.name, clicked.image, clicked.removable, clicked.path));
            ++ size;
        }
        gridViewTalk.setAdapter(new StripeAdapter(getActivity().getApplicationContext(), stripe));
    }

    // TODO: Rename method, update argument and hook method into UI event
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

class StripeAdapter extends BaseAdapter {

    public Context mContext;
    public List<SingleItem> values;
    View view;
    LayoutInflater layoutInflater;

    public StripeAdapter(Context context, List<SingleItem> values) {

        this.mContext = context;
        this.values = values;
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

            if (values.get(position).image != null){
                imageView.setImageResource(values.get(position).image);
            }else {
                Bitmap bitmap = PicUtils.decodePicture(values.get(position).path, 60, 90);
                imageView.setImageBitmap(bitmap);
            }

            textView.setText(values.get(position).name);

        }
        return view;
    }

}