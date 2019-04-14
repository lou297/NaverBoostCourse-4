package com.practice.mymovie.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.practice.mymovie.DataClass.CommentItem;
import com.practice.mymovie.DataClass.ReadCommentList.Comment;
import com.practice.mymovie.View.CommentItemView;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    private ArrayList<Comment> list;
    private Context context;

    public CommentAdapter(Context context, ArrayList<Comment> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentItemView view;
        if(convertView==null) {
            view = new CommentItemView(context);
        } else {
            view = (CommentItemView) convertView;
        }
        Comment item = list.get(position);

        view.setCommentInfo(item);
        return view;
    }
}
