package com.assignment.diabetesrecords.modules.medicine_log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.diabetesrecords.R;
import com.assignment.diabetesrecords.common.my_validator.MyValidator;
import com.assignment.diabetesrecords.entity.MedicineRecord;
import com.assignment.diabetesrecords.modules.add_doctor.DoctorManager;
import com.assignment.diabetesrecords.modules.diabetes_entry.EntryManager;
import com.assignment.diabetesrecords.modules.medicine.MedicineManager;

import java.util.List;

//==Start UserAdapter Class==========================================================
public class MedicineAdapter extends
        RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    /**
     * The Class ViewHolder.
     */
    public  class ViewHolder extends RecyclerView.ViewHolder {



        /** The title. */
        private TextView tvTabletInfo, tvDate, tvTime, tvMedicineTaken, tvTitle, tvDescription, tvIDMedicine;
        private Button delete;

        private LinearLayout LLUserSelected;


        /**
         * Instantiates a new view holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(View itemView) {
            super(itemView);


            tvTabletInfo = (TextView) itemView.findViewById(R.id.tvTabletInfo);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);

            tvMedicineTaken = (TextView) itemView.findViewById(R.id.tvMedicineTaken);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);


        }
    }

    /** The Context. */
    private Context mContext;

    /** The Inflater. */
    private LayoutInflater mInflater;

    /** The Adapter data. */
    private List<MedicineRecord> mAdapterData;
    int selected_position = 0;

    /**
     * Instantiates a new filter adapter.
     *
     * @param context the context
     * @param filteredResult the filtered result
     */
    public MedicineAdapter(Context context, List<MedicineRecord> filteredResult) {
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
        View convertView = mInflater.inflate(R.layout.row_medicine_record, parent,
                false);

        ViewHolder holder = new ViewHolder(convertView);

        return holder;
    }

    /* (non-Javadoc)
     * @see android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder, int)
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MedicineRecord record = mAdapterData.get(position);

        holder.tvTabletInfo.setText(record.getInsulineInformation() );
        holder.tvDate.setText(MyValidator.getDateInddmmyyyy(record.getEntryDate().toString()) );
        holder.tvTime.setText(MyValidator.getTimeInAMPMFormat(record.getEntryTime().toString()));

        holder.tvMedicineTaken.setText(record.getFoodTakenStatus().toString());
        holder.tvTitle.setText(record.getTitle().toString());
        holder.tvDescription.setText(record.getDescription().toString());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAdapterData.remove(position);
                notifyDataSetChanged();
            }
        });


        //----------------------------------
    }

    public void deleteMedicine(int id, String entry_date, String entry_time, String food_taken_status, String title,String description, String insulineinformation){
        MedicineManager medicineManager= new MedicineManager(mContext);
        try {
            medicineManager.deleteMedicine(id, entry_date, entry_time, food_taken_status,title,description,insulineinformation);
        } catch (Exception ex) {
            Toast.makeText(mContext, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}

//===End UserAdapter Class===============================================================