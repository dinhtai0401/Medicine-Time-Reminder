package com.assignment.diabetesrecords.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.assignment.diabetesrecords.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class FoodExerciseFragment extends Fragment{


    public static Context mContext;
    public static AppCompatActivity mParentActivity;

    final List<String> list = new ArrayList<>();

    public FoodExerciseFragment() {
        // Required empty public constructor
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    public static FoodExerciseFragment newInstance(Context context) {
        FoodExerciseFragment foodExerciseFragment = new FoodExerciseFragment();
        mContext = context;
        mParentActivity = (AppCompatActivity) context;
        return foodExerciseFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View parent= inflater.inflate(R.layout.fragment_food_exercise, container, false);
        ListView listView = parent.findViewById(R.id.listView);
        final TextApdater adapter = new TextApdater();
        readInfo();
        adapter.setData(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("Delete this task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(position);
                                adapter.setData(list);
                                saveInfo();
                            }
                        })
                        .setNegativeButton("No", null)
                        .create();
                dialog.show();
            }
        });

        Button newTaskButton = parent.findViewById(R.id.newTaskButton);

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText taskInput = new EditText(getContext());
                taskInput.setSingleLine();
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("Add a new task")
                        .setMessage("What is your new task?")
                        .setView(taskInput)
                        .setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.add(taskInput.getText().toString());
                                adapter.setData(list);
                                saveInfo();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });

        Button deleteAllTaksButton = parent.findViewById(R.id.deleteAllTasksButton);

        deleteAllTaksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("Delete All Tasks?")
                        .setMessage("Do you really want to delete all the tasks?")
                        .setPositiveButton("Delete All Tasks", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.clear();
                                adapter.setData(list);
                                saveInfo();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });



        return parent;
    }



    private void saveInfo(){
        try {
            File  file = new File(mParentActivity.getFilesDir(), "saved");

            FileOutputStream fOut = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fOut));

            for(int i = 0; i < list.size(); i ++) {
                bw.write(list.get(i));
                bw.newLine();
            }

            bw.close();
            fOut.close();
        }catch ( Exception e ){
            e.printStackTrace();
        }
    }

    private void readInfo(){
        File file = new File(mParentActivity.getFilesDir(), "saved");
        if(!file.exists()){
            return;
        }

        try{
            FileInputStream is = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while (line != null){
                list.add(line);
                line = reader.readLine();
            }
        } catch (Exception e ){
            e.printStackTrace();
        }
    }

    class TextApdater extends BaseAdapter{

        List<String> list = new ArrayList<>();

        void setData(List<String> mList){
            list.clear();
            list.addAll(mList);
            notifyDataSetChanged();
        }

        @Override
        public int getCount(){
            return list.size();
        }

        @Override
        public Object getItem(int position){
            return null;
        }

        @Override
        public long getItemId(int position){
            return 0;
        }

        @Override
        public View getView(int postion, View convertView, ViewGroup parent){
            if(convertView==null){
                LayoutInflater inflater = (LayoutInflater) mParentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item, parent, false);
            }

            final TextView textView = convertView.findViewById(R.id.task);
            textView.setText(list.get(postion));
            return convertView;
        }
    }

}
