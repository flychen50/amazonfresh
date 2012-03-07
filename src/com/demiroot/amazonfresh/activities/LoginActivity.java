// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.content.*;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.AsyncRequest;
import com.demiroot.amazonfresh.listeners.EnterKeyListener;
import com.demiroot.freshclient.AmazonFreshBase;
import com.demiroot.freshclient.FreshAPIException;

// Referenced classes of package com.demiroot.amazonfresh.activities:
//            NewCustomerActivity

public class LoginActivity extends AFBaseActivity
{
    static class NewCustomerException extends FreshAPIException
    {

        public NewCustomerException()
        {
            super(0, "", "");
        }
    }


    public LoginActivity()
    {
        msgReceiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent)
            {
                String s = intent.getStringExtra("MESSAGE");
                if(s != null)
                    showMessage(s);
            }

            final LoginActivity this$0;

            
            {
                this$0 = LoginActivity.this;
                super();
            }
        }
;
        siginButtonListener = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                submitLogin();
            }

            final LoginActivity this$0;

            
            {
                this$0 = LoginActivity.this;
                super();
            }
        }
;
        emailTextListener = new EnterKeyListener() {

            public void onEnter()
            {
                ((TextView)findViewById(0x7f0b006d)).requestFocus();
            }

            final LoginActivity this$0;

            
            {
                this$0 = LoginActivity.this;
                super();
            }
        }
;
        passwordTextListener = new EnterKeyListener() {

            public void onEnter()
            {
                submitLogin();
            }

            final LoginActivity this$0;

            
            {
                this$0 = LoginActivity.this;
                super();
            }
        }
;
        loginRunnable = new Runnable() {

            public void run()
            {
                getAmazonFreshBase().login(email, password);
                if(!getAmazonFreshBase().isExistingCustomer())
                {
                    getAmazonFreshBase().logout();
                    throw new NewCustomerException();
                } else
                {
                    return;
                }
            }

            final LoginActivity this$0;

            
            {
                this$0 = LoginActivity.this;
                super();
            }
        }
;
        afterLoginRunnable = new Runnable() {

            public void run()
            {
                loginSuccess();
            }

            final LoginActivity this$0;

            
            {
                this$0 = LoginActivity.this;
                super();
            }
        }
;
        loginFailureRunnable = new com.demiroot.amazonfresh.Async.OnExceptionAction() {

            public boolean onException(Exception exception)
            {
                String s = getResources().getString(0x7f070035);
                if(exception instanceof NewCustomerException)
                {
                    Intent intent = new Intent(LoginActivity.this, com/demiroot/amazonfresh/activities/NewCustomerActivity);
                    startActivity(intent);
                } else
                {
                    if(exception instanceof FreshAPIException)
                    {
                        if(((FreshAPIException)exception).getErrorCode() == 23 || ((FreshAPIException)exception).getErrorCode() == 17)
                            s = ((FreshAPIException)exception).getReason();
                    } else
                    {
                        s = getResources().getString(0x7f070088);
                    }
                    showMessage(s);
                }
                return false;
            }

            final LoginActivity this$0;

            
            {
                this$0 = LoginActivity.this;
                super();
            }
        }
;
    }

    private void loadLayout()
    {
        ((TextView)findViewById(0x7f0b006e)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)findViewById(0x7f0b0070)).setMovementMethod(LinkMovementMethod.getInstance());
        SharedPreferences sharedpreferences = getPreferences(0);
        boolean flag = sharedpreferences.getBoolean(REMEMBER_ME_KEY, true);
        String s = sharedpreferences.getString(EMAIL_KEY, "");
        EditText edittext = (EditText)findViewById(0x7f0b006c);
        edittext.setText(s);
        edittext.setOnKeyListener(emailTextListener);
        EditText edittext1 = (EditText)findViewById(0x7f0b006d);
        edittext1.setOnKeyListener(passwordTextListener);
        if(s.length() != 0)
            edittext1.requestFocus();
        ((CheckBox)findViewById(0x7f0b0071)).setChecked(flag);
        ((Button)findViewById(0x7f0b006f)).setOnClickListener(siginButtonListener);
    }

    private void loginSuccess()
    {
        AFBaseActivity.loggingIn = false;
        finish();
    }

    public static void signout(final AmazonFreshBase base)
    {
        (new Thread() {

            public void run()
            {
                base.logout();
            }

            private final AmazonFreshBase val$base;

            
            {
                base = amazonfreshbase;
                super();
            }
        }
).start();
    }

    private void submitLogin()
    {
        String s = ((TextView)findViewById(0x7f0b006c)).getText().toString();
        String s1 = ((TextView)findViewById(0x7f0b006d)).getText().toString();
        remember = ((CheckBox)findViewById(0x7f0b0071)).isChecked();
        email = s.trim();
        password = s1.trim();
        if(email.length() == 0 || password.length() == 0)
        {
            showMessage(getResources().getString(0x7f070034));
        } else
        {
            android.content.SharedPreferences.Editor editor = getPreferences(0).edit();
            editor.putString(EMAIL_KEY, email);
            editor.putBoolean(REMEMBER_ME_KEY, remember);
            editor.commit();
            AsyncRequest.buildRequest(this, getHandler(), loginRunnable).setSuccessAction(afterLoginRunnable).setFailureAction(loginFailureRunnable).setWaitMessage(getString(0x7f070033)).execute();
        }
    }

    protected void onCreate(Bundle bundle)
    {
        requiresLogin = false;
        super.onCreate(bundle);
        setContentView(0x7f030012);
        loadLayout();
    }

    protected void onResume()
    {
        super.onResume();
        if(getIntent().getBooleanExtra("INVALID_CUSTOMER", false))
            showMessage(getResources().getString(0x7f070036));
    }

    protected void onStart()
    {
        super.onStart();
        AFBaseActivity.loggingIn = true;
        registerReceiver(msgReceiver, new IntentFilter("com.demiroot.freshclient.LoginActivity.Msg"));
    }

    protected void onStop()
    {
        super.onStop();
        unregisterReceiver(msgReceiver);
    }

    public static final String CLEAR_SAVED_PASSWORD = "CLEAR_SAVED_PASSWORD";
    private static String EMAIL_KEY = "email_key";
    public static final String INVALID_CUSTOMER = "INVALID_CUSTOMER";
    public static final String MESSAGE = "MESSAGE";
    private static String REMEMBER_ME_KEY = "remember_me_key";
    public static final String START_GATEWAY = "START_GATEWAY";
    Runnable afterLoginRunnable;
    private String email;
    EnterKeyListener emailTextListener;
    com.demiroot.amazonfresh.Async.OnExceptionAction loginFailureRunnable;
    Runnable loginRunnable;
    private BroadcastReceiver msgReceiver;
    private String password;
    EnterKeyListener passwordTextListener;
    private boolean remember;
    android.view.View.OnClickListener siginButtonListener;





}
