package co.lemonlabs.mortar.example.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.lemonlabs.mortar.example.R;
import co.lemonlabs.mortar.example.ui.screens.EntryScreen;
import mortar.Mortar;

/**
 * Created by george on 2014/11/3.
 */
public class EntryView extends FrameLayout {

    @Inject EntryScreen.Presenter presenter;

    @InjectView(R.id.btn_goto_next) Button btnGotoNext;

    public EntryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Mortar.inject(context, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        presenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }

    @OnClick(R.id.btn_goto_next)
    public void onClickGoto() {
        showToast("AHHHHH");
    }

    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
