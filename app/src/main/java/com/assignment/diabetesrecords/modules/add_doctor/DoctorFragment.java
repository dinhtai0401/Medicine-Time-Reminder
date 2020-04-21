package com.assignment.diabetesrecords.modules.add_doctor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.assignment.diabetesrecords.R;
import com.assignment.diabetesrecords.entity.Doctor;

import java.util.ArrayList;


public class DoctorFragment extends Fragment {

    int iPageCounter;
    ArrayList<Doctor> mList;
    DoctorAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Button bAddDoctor;
    private EditText nameet, phoneet, emailidet;

    public static Context mContext;
    public static AppCompatActivity mParentActivity;

    public DoctorFragment() {
        // Required empty public constructor
        int i = 0;

    }

    public static DoctorFragment newInstance(Context context) {
        DoctorFragment fragment = new DoctorFragment();

        mContext = context;
        mParentActivity = (AppCompatActivity) context;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_doctor, container, false);
        bAddDoctor = (Button) parent.findViewById(R.id.bAddDoctor);

        bAddDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), AddDoctorActivity.class);
                getActivity().startActivity(in);
            }
        });
        //-------------------------------------
        DoctorManager manager = new DoctorManager(getActivity());

        Button deleteBtn = (Button) parent.findViewById(R.id.Delete);
        final EditText input = new EditText(getContext());
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("Delete All Tasks?")
                        .setMessage("Do you really want to delete all the tasks?")
                        .setPositiveButton("Delete All Tasks", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delete();
                                onResume();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });


        //Begin -- Load Data in List--------------------------------
        mRecyclerView = (RecyclerView) parent.findViewById(R.id.itemlist);
        iPageCounter = 1;
        mList = manager.getAll(iPageCounter);
        mAdapter = new DoctorAdapter(getActivity(), mList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //End -- Load Data in List--------------------------------
        //------------------------------------------


        return parent;
    }

    public void onResume() {
        super.onResume();
        DoctorManager manager = new DoctorManager(getActivity());
        //ArrayList<Category> clist;


        //Begin -- Load Data in List--------------------------------

        iPageCounter = 1;
        mList = manager.getAll(iPageCounter);
        mAdapter = new DoctorAdapter(getActivity(), mList);


        //     RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        //   mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
    private void delete() {
        DoctorManager manager = new DoctorManager(getContext());
        long l = 0;
        try {
            l = manager.delete(0);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }


}
