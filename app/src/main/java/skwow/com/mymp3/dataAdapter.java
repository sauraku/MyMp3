package skwow.com.mymp3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by skwow on 3/6/2017.
 */

public class dataAdapter extends RecyclerView.Adapter<dataAdapter.dataAdaperHolder> {

    private LayoutInflater inflater;
    public ArrayList<HashMap<String,String>> datas;

    public dataAdapter(Context c, ArrayList<HashMap<String,String>> d)
    {
        inflater = LayoutInflater.from(c);
        datas= d;
    }

    @Override
    public dataAdaperHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cards,parent,false);
        dataAdaperHolder holder = new dataAdaperHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(dataAdaperHolder holder, int position)
    {
        //System.out.println(position);
        holder.txt.setText(datas.get(position).get("file_name"));
        //holder.d=  datas.get(position);;
    }



    @Override
    public int getItemCount() {
        return datas.size();
    }

    class dataAdaperHolder extends RecyclerView.ViewHolder
    {
        //HashMap<String,String> d;
        TextView txt;

        public dataAdaperHolder(final View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.songName);
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String u = new String(String.valueOf(d.url));
                    Intent i = new Intent(itemView.getContext(),webpage.class);
                    i.putExtra("uri",u);
                    itemView.getContext().startActivity(i);
                }
            });*/
        }
    }
}
