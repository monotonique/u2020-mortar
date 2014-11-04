package co.lemonlabs.mortar.example.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.lemonlabs.mortar.example.R;
import co.lemonlabs.mortar.example.ui.screens.LobbyScreen;
import mortar.Mortar;

/**
 * Created by george on 2014/11/4.
 */
public class LobbyView extends FrameLayout {

    @Inject LobbyScreen.Presenter presenter;

    @InjectView(R.id.btn_back_to_entry) Button btnBackToEntry;

    public LobbyView(Context context, AttributeSet attrs) {
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

    @OnClick(R.id.btn_back_to_entry)
    public void onClick() {
        presenter.backToEntry();
    }
}
