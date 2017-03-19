package edu.csb.cs.cs185.jordanang.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static edu.csb.cs.cs185.jordanang.habittracker.MainActivity.habitList;

public class DeleteFragment extends DialogFragment {

    int position;
    String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_delete_fragment, container, false);

        Button yesButton = (Button) v.findViewById(R.id.yesButton);
        Button noButton = (Button) v.findViewById(R.id.noButton);

        position = getArguments().getInt("POSITION");

        title = habitList.get(position).habitTitle;

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habitList.remove(position);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                Toast.makeText(getContext(), title + " has been deleted", Toast.LENGTH_LONG);
                getActivity().finish();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return v;
    }
}
