package com.example.app1.Adapters;


import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.app1.R;

import java.util.ArrayList;

public class TableGenerator {
    public static void populateTable(Context context, TableLayout tableLayout,ArrayList<String> headers, ArrayList<ArrayList<String>> data) {
        tableLayout.setPadding(10, 10, 10, 10);
        // Clear existing rows, if any
        tableLayout.removeAllViews();

        // Add header row
        TableRow headerRow = new TableRow(context);
        headerRow.setBackgroundColor(context.getResources().getColor(R.color.header_background_color));
        for (String header : headers) {
            addHeaderCell(context, headerRow, header);
        }
        tableLayout.addView(headerRow);

        // Add data rows
        for (ArrayList<String> rowData : data) {
            TableRow dataRow = new TableRow(context);
            dataRow.setBackgroundColor(context.getResources().getColor(R.color.data_background_color));
            for (String cellData : rowData) {
                addDataCell(context, dataRow, cellData, context.getResources().getColor(R.color.data_text_color), 1f);
            }
            tableLayout.addView(dataRow);   //add data into the table row
        }
    }
    private static void addHeaderCell(Context context, TableRow row, String text) {
        TextView textView = createTextView(context, text, context.getResources().getColor(R.color.header_text_color), 1f);
        row.addView(textView);
    }

    private static void addDataCell(Context context, TableRow row, String text, int textColor, float weight) {
        TextView textView = createTextView(context, text, textColor, weight);
        row.addView(textView);
    }

    private static TextView createTextView(Context context, String text, int textColor, float weight) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextColor(textColor);
        textView.setPadding(16, 16, 16, 16);
        textView.setGravity(Gravity.CENTER);

        // Set the layout weight to achieve equal column width
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, weight);
        textView.setLayoutParams(params);

        return textView;
    }

}


