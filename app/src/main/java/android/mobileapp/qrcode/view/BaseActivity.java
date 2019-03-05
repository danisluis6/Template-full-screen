package android.mobileapp.qrcode.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

  private Unbinder mUnbinder;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    distributedDaggerComponents();
    setContentView(getLayoutRes());
    mUnbinder = ButterKnife.bind(this);
    initAttributes();
    initViews();
  }

  public abstract void distributedDaggerComponents();

  protected abstract int getLayoutRes();

  protected abstract void initAttributes();

  protected abstract void initViews();

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  protected void onDestroy() {
    if (mUnbinder != null) {
      mUnbinder.unbind();
    }
    super.onDestroy();
  }
}
