package com.example.alex.assignment5;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 3/28/2016.
 */
public class Progress extends ListFragment implements OnItemClickListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ProgressInteractionListener talkToActivity;
    private ListView entries;
    private Button add;
    private ProgressEntryAdapter adapter;

    private Notifier theNotifier;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Progress.
     */
    public static Progress newInstance(String param1, String param2)
    {
        Progress fragment = new Progress();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Progress()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.list_fragment, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {

        super.onActivityCreated(savedInstanceState);

        ArrayList<String> temp = new ArrayList<String>();

        // Inserts data into the array adapter
        adapter = new ProgressEntryAdapter(getActivity().getApplicationContext(), temp);
        this.theNotifier = new Notifier("Team2User1",adapter);
        this.theNotifier.getNotificationList();
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        Toast.makeText(getActivity(), "Deleting Notification", Toast.LENGTH_SHORT)
                .show();
        System.out.println("button pressed at" + position);
        //this.theNotifier.removeAt(position);
        this.adapter.removeAt(position);
        this.adapter.notifyDataSetChanged();

    }


        @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            talkToActivity = (ProgressInteractionListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement ProgressInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        talkToActivity = null;
    }

    // These methods pass data from the fragment
    public interface ProgressInteractionListener
    {
        public void sendDeletedData(int data);
    }

    // Custom adapter that allows you to handle interactions with the listview views
    public class ProgressEntryAdapter extends ArrayAdapter<String> //implements View.OnClickListener
    {
        private final Context context;
        // Values that will be displayed
        private final ArrayList<String> values;

        // Constructor that takes the values
        public ProgressEntryAdapter(Context context, ArrayList<String> values)
        {
            super(context, R.layout.progress_entry_layout, values);
            this.context = context;
            this.values = values;
        }
        public void removeAt(int pos){
            values.remove(pos);
        }
        // Draws each visible view
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.progress_entry_layout, parent, false);


            TextView msgText = (TextView) rowView.findViewById(R.id.msgText);
            msgText.setText(getItem(position));

            Log.e("aea",getItem(position));

            return rowView;
        }
    }
}