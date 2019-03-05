package android.mobileapp.qrcode.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    distributedDaggerComponents();
    setContentView(getLayoutRes());
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
    super.onDestroy();
  }
}
