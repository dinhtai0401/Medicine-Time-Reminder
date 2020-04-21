package com.assignment.diabetesrecords.modules.add_doctor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.diabetesrecords.R;
import com.assignment.diabetesrecords.common.helper.sql_lite_helper.DBHelper;
import com.assignment.diabetesrecords.modules.app_appointment.AddAppointmentActivity;
import com.assignment.diabetesrecords.entity.Doctor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.security.AccessController;
import java.util.List;

//==Start UserAdapter Class==========================================================
public class DoctorAdapter extends
        RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    DBHelper helper;
    Context context;
    int previousPosition = 0;

    /**
     * The Class ViewHolder.
     */
    public  class ViewHolder extends RecyclerView.ViewHolder{



        /** The title. */
        private TextView tvName, tvPhone, tvEmail, tvDoctorId;
        private Button bAppointment, deleteDoctor;


        /**
         * Instantiates a new view holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(final View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            tvDoctorId = (TextView) itemView.findViewById(R.id.tvDoctorId);
            bAppointment = (Button) itemView.findViewById(R.id.bAppointment);
            deleteDoctor = itemView.findViewById(R.id.deleteDoctor);



        }



    }

    /** The Context. */
    private Context mContext;

    /** The Inflater. */
    private LayoutInflater mInflater;

    /** The Adapter data. */
    private List<Doctor> mAdapterData;
    int selected_position = 0;

    /**
     * Instantiates a new filter adapter.
     *
     * @param context the context
     * @param filteredResult the filtered result
     */
    public DoctorAdapter(Context context, List<Doctor> filteredResult) {
        this.context = context;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mAdapterData = filteredResult;

    }

    /* (non-Javadoc)
     * @see android.support.v7.widget.RecyclerView.Adapter#getItemCount()
     */
    @Override
    public int getItemCount() {

        return mAdapterData.size();
        //return mAdapterData.length;
    }

    /* (non-Javadoc)
     * @see android.support.v7.widget.RecyclerView.Adapter#onCreateViewHolder(android.view.ViewGroup, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.row_doctor, parent,
                false);

        ViewHolder holder = new ViewHolder(convertView);

        return holder;
    }


    /* (non-Javadoc)
     * @see android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder, int)
     */

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Doctor record = mAdapterData.get(position);

        holder.tvName.setText(record.getName().toString() );
        holder.tvPhone.setText(record.getPhone());
        holder.tvEmail.setText(record.getEmailid());
        holder.tvDoctorId.setText(String.valueOf(record.getId()));

        holder.bAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(mContext, AddAppointmentActivity.class);
                in.putExtra("DoctorId", holder.tvDoctorId.getText());
                in.putExtra("DoctorName", holder.tvName.getText());
                in.putExtra("DoctorPhone", holder.tvPhone.getText());
                in.putExtra("DoctorEmail", holder.tvEmail.getText());
                mContext.startActivity(in);
            }
        });

        holder.deleteDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDoctor(record.getId(), record.getName(), record.getPhone(), record.getEmailid());
                mAdapterData.remove(position);
                notifyDataSetChanged();
            }
        });

        //----------------------------------
    }


    public void deleteDoctor(int id, String name, String phone, String emailid){
        DoctorManager manager = new DoctorManager(mContext);
        try {
            manager.deleteDoctor(id, name, phone,emailid);
        } catch (Exception ex) {
            Toast.makeText(mContext, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }


}

//===End UserAdapter Class===============================================================