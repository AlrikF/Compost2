package com.example.dataauth2;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DynamicViewGrey {




    public TextView descriptionTextView(Context context,String text,Integer id)
    {
        final ViewGroup.LayoutParams lparams= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView=new TextView(context);
        textView.setLayoutParams(lparams);
        //textView.setId(Id);
        // textView.setPadding(20,0,0,0);
        textView.setId(id);
        textView.setLayoutParams(new ViewGroup.LayoutParams(250, ViewGroup.LayoutParams.WRAP_CONTENT));


        textView.setTextSize(14);
        textView.setText(text);

        //  textView.setMaxEms(0);
        return  textView;
    }

    public EditText receivedNumberEditText(Context context,Integer id)
    {
        final  ViewGroup.LayoutParams lparams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText editText =new EditText(context);

        //editText.setId(id);
        // editText.setPadding(20,10,10,10);
        // editText.setMinEms(2);
        editText.setLayoutParams(new ViewGroup.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setId(id);
        //editText.setTextColor(Color.rgb(0,0,0));
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        return editText;

    }


    public TextView dummy(Context context,String text)
    {
        final ViewGroup.LayoutParams lparams= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView=new TextView(context);
        textView.setLayoutParams(lparams);
        textView.setLayoutParams(new ViewGroup.LayoutParams(100,20));
        //textView.setId(Id);
        // textView.setPadding(20,0,0,0);
        textView.setTextSize(10);
        textView.setText(text);
        //  textView.setMaxEms(0);
        return  textView;
    }

}
