package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Category> categories;
    private final int recyclerViewID;
    private final int itemImageID;
    private final int itemTitleID;
    private final int itemValueID;
    private final int itemTransactionsID;

    public CategoryAdapter(Context context, List<Category> categories, int recyclerViewID, int itemImageID, int itemTitleID, int itemValueID, int itemTransactionsID) {
        this.inflater = LayoutInflater.from(context);
        this.categories = categories;
        this.recyclerViewID = recyclerViewID;
        this.itemImageID = itemImageID;
        this.itemTitleID = itemTitleID;
        this.itemValueID = itemValueID;
        this.itemTransactionsID = itemTransactionsID;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(recyclerViewID, parent, false);
        return new ViewHolder(new ViewWithID(view, itemImageID, itemTitleID, itemValueID, itemTransactionsID));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = categories.get(position);
        holder.image.setImageResource(category.getImageResource());
        holder.title.setText(category.getName());
        DecimalFormat df = new DecimalFormat("0.##");
        String s = df.format(category.getSum());
        holder.value.setText("$" + s);
        if (!(holder.transactions.getId() == R.id.empty))
            holder.transactions.setText(category.getTransactions().size() + " transaction" + (category.getTransactions().size()==1 ? "" : "s"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpensesPage expensesPage = (ExpensesPage) inflater.getContext();
                expensesPage.transactionOfCategory(categories.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView image;
        final TextView title, value, transactions;
        ViewHolder(ViewWithID viewWithID){
            super(viewWithID.getView());
            image = viewWithID.getImageView();
            title = viewWithID.getTitleView();
            value = viewWithID.getValueView();
            transactions = viewWithID.getTransactionsView();
        }
    }
}
