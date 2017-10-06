package com.example.sagee.bmiapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private List<ListItem> mlistItems;
    private Context mContext;
    private History mHistory;

    public MyAdapter(List<ListItem> listItem, Context context, History history)
    {
        this.mlistItems = listItem;
        this.mContext = context;
        this.mHistory = history;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        final ListItem listItem = mlistItems.get(position);

        holder.mTextViewData.setText(listItem.getDate());
        holder.mTextViewWeight.setText(listItem.getWeight());
        holder.mTextViewHeight.setText(listItem.getHeight());
        holder.mTextViewBMIresult.setText(listItem.getBMIresult());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder a_builder = new AlertDialog.Builder(mContext);
                a_builder.setMessage("Delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)

                            public void onClick(DialogInterface dialog, int id)
                            {
                                mlistItems.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, mlistItems.size());

                                DataBaseHelper.getInstance(mContext).deleteData(listItem.getId());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = a_builder.create();
                alert.show();

                // set text color of AlertDialog answers to red
                Button buttonPositive = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setTextColor(ContextCompat.getColor(mContext, R.color.colorAlertDialogText));
                Button buttonNegative = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setTextColor(ContextCompat.getColor(mContext, R.color.colorAlertDialogText));

                return false;
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mlistItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextViewData,
                        mTextViewWeight,
                        mTextViewHeight,
                        mTextViewBMIresult;

        public ViewHolder(View itemView)
        {
            super(itemView);

            mTextViewData = (TextView)itemView.findViewById(R.id.textViewData);
            mTextViewWeight = (TextView)itemView.findViewById(R.id.textViewWeight);
            mTextViewHeight = (TextView)itemView.findViewById(R.id.textViewHeight);
            mTextViewBMIresult = (TextView)itemView.findViewById(R.id.textViewBMIresult);
        }
    }
}
