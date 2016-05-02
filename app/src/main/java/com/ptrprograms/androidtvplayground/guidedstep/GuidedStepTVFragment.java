package com.ptrprograms.androidtvplayground.guidedstep;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;
import android.util.Log;

import com.ptrprograms.androidtvplayground.R;

import java.util.ArrayList;
import java.util.List;

public class GuidedStepTVFragment extends GuidedStepFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
        return new GuidanceStylist.Guidance("Guided Step Title", "description", "breadcrumb", getResources().getDrawable(R.mipmap.ic_launcher));
    }

    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        actions.add(new GuidedAction.Builder(getActivity())
                .infoOnly(true)
                .title("First Action")
                .id(0)
                .build());

        List<GuidedAction> secondActions = new ArrayList<GuidedAction>();

        secondActions.add(new GuidedAction.Builder(getActivity())
                .id(10)
                .title("First Action")
                .build());

        secondActions.add(new GuidedAction.Builder(getActivity())
                .id(11)
                .title("Open second fragment")
                .build());

        secondActions.add(new GuidedAction.Builder(getActivity())
                .id(12)
                .title("Close this!")
                .build());

        actions.add(new GuidedAction.Builder(getActivity())
                .id(2)
                .title("Second action")
                .description("Second action description")
                .subActions(secondActions)
                .build());

        GuidedAction guidedAction = new GuidedAction.Builder(getActivity())
                .id(3)
                .title("Third action")
                .description("Checked item")
                .checkSetId(0)
                .icon(R.mipmap.ic_launcher)
                .build();
        guidedAction.setChecked(true);

        actions.add(guidedAction);
    }

    @Override
    public boolean onSubGuidedActionClicked(GuidedAction action) {
        if( action.getId() == 12 ) {
            getActivity().finish();
            return true;
        } else if( action.getId() == 11 ) {
            GuidedStepFragment.add(getFragmentManager(), new GuidedStepTVFragment());
            return true;
        }

        return super.onSubGuidedActionClicked(action);
    }

    @Override
    public void onGuidedActionClicked(GuidedAction action) {
        super.onGuidedActionClicked(action);
        if( action.getId() == 3 ) {
            action.setChecked(!action.isChecked());
        }
    }
}
