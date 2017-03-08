package skwow.com.mymp3;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskDenied;
import com.vistrav.ask.annotations.AskGranted;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import skwow.com.mymp3.R;

public class mp3List extends Fragment {

    private int read;
    private Activity activity = getActivity();
    private RecyclerView rView;
    private ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_mp3_list, container, false);
        read=0;
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        rView = (RecyclerView) root.findViewById(R.id.rview);
        progressBar.setVisibility(View.VISIBLE);
        t.start();

        return root;
    }

    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            readSongs();
        }
    });


    public void readSongs() {
        System.out.println("reading songs");
        ArrayList<HashMap<String,String>> songList = getPlayList(Environment.getExternalStorageDirectory().getAbsolutePath());
        if(songList!=null)
        {
            read=1;
            //System.out.println(songList.size());
            //Toast.makeText(activity,String.valueOf(songList.size()),Toast.LENGTH_LONG).show();
            //System.out.println(getActivity(),getContext());
            final dataAdapter dAdapter = new dataAdapter(getContext(), songList);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rView.setAdapter(dAdapter);
                    rView.setLayoutManager(new LinearLayoutManager(activity));
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
        else
        {
            System.out.println("No sd card found");
            //Toast.makeText(activity,"no SD card",Toast.LENGTH_LONG).show();
        }
    }



    private ArrayList<HashMap<String,String>> getPlayList(String rootPath)
    {
        ArrayList<HashMap<String,String>> fileList = new ArrayList<>();
        try {
            File rootFolder = new File(rootPath);
            File[] files = rootFolder.listFiles(); //here you will get NPE if directory doesn't contains  any file,handle it like this.
            for (File file : files) {
                if (file.isDirectory()) {
                    if (getPlayList(file.getAbsolutePath()) != null) {
                        fileList.addAll(getPlayList(file.getAbsolutePath()));
                    } else {
                        break;
                    }
                } else if (file.getName().endsWith(".mp3")) {
                    HashMap<String, String> song = new HashMap<>();
                    song.put("file_path", file.getAbsolutePath());
                    song.put("file_name", file.getName());
                    fileList.add(song);
                    System.out.println(fileList.size());
                }
            }
            return fileList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
