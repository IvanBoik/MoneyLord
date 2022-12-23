package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewWithID {
    private final View view;
    private final ImageView imageView;
    private final TextView titleView;
    private final TextView valueView;
    private final TextView transactionsView;

    public ViewWithID(View view, int imageID, int titleID, int valueID, int transactionsID) {
        this.view = view;
        this.imageView = view.findViewById(imageID);
        this.titleView = view.findViewById(titleID);
        this.valueView = view.findViewById(valueID);
        this.transactionsView = view.findViewById(transactionsID);
    }

    public View getView() {
        return view;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getValueView() {
        return valueView;
    }

    public TextView getTransactionsView() {
        return transactionsView;
    }
}
