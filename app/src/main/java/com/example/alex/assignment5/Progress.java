package com.example.alex.assignment5;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class Progress extends Fragment
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

    private String userid;
    private String id;
    private String msgtype;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Progress.
     */
    // TODO: Rename and change types and number of parameters
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

    public void getNotifications(){
        Log.d("Try", "hello");
        Retrofit client = new Retrofit.Builder().baseUrl("https://2bj29vv7f3.execute-api.us-east-1.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PushClient service = client.create(PushClient.class);
        Call<PushMessage> call = service.getData("Team2User1");
        call.enqueue(new Callback<PushMessage>()
        {
            @Override
            public void onResponse(Call<PushMessage> call, Response<PushMessage> response)
            {
                Response<PushMessage> resp = response;
                if (response.isSuccessful())
                {
                    PushMessage result = response.body();
                    List<Message> nsg = result.getMessages();
                    Message mm = nsg.get(0);
                    id = mm.getMessageId();
                    MSG msg = mm.getMSG();
                    msgtype = msg.msg_type;
                    Body bb = msg.body;


                    Log.d("good", "Gucci");

                } else
                {
                    Log.d("bad", "fafa");
                }
            }

            @Override
            public void onFailure(Call<PushMessage> call, Throwable t)
            {
                Log.d("bad2", "tata");

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        ArrayList<String> temp = new ArrayList<String>();


        //temp.add(new ProgressEntry(id, msgtype, 0, 0));

        // Links Java side with the xml side for the ListView
        entries = (ListView) view.findViewById(R.id.progressList);

        // Inserts data into the array adapter
        adapter = new ProgressEntryAdapter(getActivity().getApplicationContext(), temp);
        entries.setAdapter(adapter);

        Notifier notifier = new Notifier("Team2User1",adapter);
        ArrayList<String> notiflist = notifier.getNotificationList();
        return view;
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
    public class ProgressEntryAdapter extends ArrayAdapter<String> implements View.OnClickListener
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

        // Draws each visible view
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.progress_entry_layout, parent, false);

            // Initializes GUI elements
            /*Button saveButton = (Button) rowView.findViewById(R.id.saveButton);
            Button deleteButton = (Button) rowView.findViewById(R.id.deleteButton);
            EditText idText = (EditText) rowView.findViewById(R.id.idEntry);
            EditText typeText = (EditText) rowView.findViewById(R.id.typeEntry);
            EditText floorText = (EditText) rowView.findViewById(R.id.floorEntry);
            EditText roomText = (EditText) rowView.findViewById(R.id.roomEntry);*/

            TextView msgText = (TextView) rowView.findViewById(R.id.msgText);
            msgText.setText(getItem(position));
            // Retrieves data elements for the position and populates the entry
            //NotificationDisplay current = getItem(position);
            /*String tempID = current.id;
            idText.setText("ID: " + tempID);
            String tempType = current.msgtype;
            typeText.setText("Type: " + tempType);
            String tempFloor = Float.toString(current.floor);
            floorText.setText("Floor: " + tempFloor);
            String tempRoom = Float.toString(current.room);
            roomText.setText("Room: " + tempRoom);
            */
            /*idText.setText("Type: " + getItem(position));
            typeText.setText("Type: " + getItem(position));*/

            Log.e("aea",getItem(position));

            // In order to determine button presses, each buttons must be assigned a tag
            /*saveButton.setTag(new String[]{((Integer) position).toString(), "save"});
            deleteButton.setTag(new String[]{((Integer) position).toString(), "delete"});

            // Sets button listeners
            saveButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
    */
            return rowView;
        }

        // Called whenever 'save' or 'delete' is pressed
        @Override
        public void onClick(View view)
        {
            // If 'delete' was pressed
            if (((String[]) view.getTag())[1] == "delete")
            {
                // Retrieves the entry ID
                int entry = Integer.parseInt(((String[]) view.getTag())[0]);

                // Displays which entry was deleted
                Toast.makeText(context, "Delete Pressed for Entry: " + entry, Toast.LENGTH_SHORT).show();

                // Communicates to AppActivity which entry was deleted
                talkToActivity.sendDeletedData(entry);

                // "this" refers to the ArrayAdapter. You can remove things from ArrayAdpter by saying .remove(<object>)
                this.remove(this.getItem(Integer.parseInt(((String[]) view.getTag())[0])));

                // Required to ensure that the GUI remains free of glitches
                this.notifyDataSetChanged();
            }

            // If 'save' was pressed
            if (((String[]) view.getTag())[1] == "save")
            {
                // The index of the entry to be saved
                int indexInAdapter = Integer.parseInt(((String[]) view.getTag())[0]);

                // The index of the first visible listview view
                int firstVisible = entries.getFirstVisiblePosition();

                // Displays which entry was saved
                Toast.makeText(context, "Save Pressed for Entry: " + indexInAdapter, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class ProgressEntry
    {
        public String id;
        public String msgtype;
        public int floor;
        public int room;

        public ProgressEntry(String id, String msgtype, int floor, int room)
        {
            this.id = id;
            this.msgtype = msgtype;
            this.floor = floor;
            this.room = room;
        }
    }
}