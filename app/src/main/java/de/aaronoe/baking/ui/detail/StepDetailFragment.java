package de.aaronoe.baking.ui.detail;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Step;

/**
 * Created by private on 8/5/17.
 *
 */

@EFragment(R.layout.step_detail_fragment)
public class StepDetailFragment extends Fragment {

    @ViewById(R.id.detail_step_tv)
    TextView numberTv;
    @ViewById(R.id.detail_step_description_tv)
    TextView nameTv;
    @ViewById(R.id.detail_step_video_view)
    SimpleExoPlayerView mExoPlayerView;

    Step mStep;

    @AfterViews
    void init() {
        numberTv.setText(getActivity().getString(R.string.step_number, mStep.getId() + 1));
        nameTv.setText(mStep.getDescription());
    }

    public void setmStep(@FragmentArg Step step) {
        this.mStep = step;
    }
}
