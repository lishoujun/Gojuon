package com.gracecode.android.gojuon.ui.dialog;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.gracecode.android.common.helper.UIHelper;
import com.gracecode.android.gojuon.R;
import com.gracecode.android.gojuon.common.Gojuon;
import com.gracecode.android.gojuon.helper.ExamHelper;
import com.gracecode.android.gojuon.ui.activity.ExamActivity;

public class ExamResultDialog extends BaseDialogFragment {
    private final ExamActivity mExamActivity;
    private static Typeface mCustomTypeface;
    private final ExamHelper mExamHelper;
    private TextView mExamPercent;
    private Button mExamButtonTryAgain;
    private Button mExamButtonRedoWrong;
    private Button mExamButtonConfigure;

    public ExamResultDialog(ExamActivity activity) {
        mExamActivity = activity;
        mExamHelper = activity.getExamHelper();
        mCustomTypeface = Typeface.createFromAsset(mExamActivity.getAssets(), Gojuon.CUSTOM_FONT_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_result, null);
        if (view != null) {
            mExamPercent = (TextView) view.findViewById(R.id.exam_percent);
            mExamPercent.setTypeface(mCustomTypeface);

            mExamButtonTryAgain = (Button) view.findViewById(R.id.exam_try_again);
            mExamButtonRedoWrong = (Button) view.findViewById(R.id.exam_redo_wrong);
            mExamButtonConfigure = (Button) view.findViewById(R.id.exam_configure);
        }

        return view;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.exam_redo_wrong:
                    mExamActivity.redoWrongTopic();
                    break;

                case R.id.exam_try_again:
                    mExamActivity.startExam();
                    break;

                case R.id.exam_configure:
                    UIHelper.showShortToast(getActivity(), "Configure!!");
                    break;
            }

            dismiss();
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCancelable(false);

        mExamButtonTryAgain.setOnClickListener(mOnClickListener);
        mExamButtonRedoWrong.setOnClickListener(mOnClickListener);
        mExamButtonConfigure.setOnClickListener(mOnClickListener);
    }


    @Override
    public void onResume() {
        super.onResume();
        mExamPercent.setText(String.format("%.0f%%",
                (1 - (mExamHelper.getWrongCount() * 1f / mExamHelper.getTotalCount())) * 100));

        mExamButtonRedoWrong.setEnabled((mExamHelper.getWrongCount() > 0));
    }
}
