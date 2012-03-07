// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.ui;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.*;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.AsyncRequest;
import com.demiroot.amazonfresh.util.StringUtils;
import com.demiroot.freshclient.*;
import java.util.List;

public class AddressBookAdapter extends ArrayAdapter
{
    static class ViewHolder
    {

        TextView address;
        LinearLayout border;
        Button chooseAddress;
        TextView missingAddressInfo;
        TextView name;

        ViewHolder()
        {
        }
    }


    public AddressBookAdapter(Context context, List list)
    {
        super(context, 0, list);
        inflater = LayoutInflater.from(context);
        activity = (AFBaseActivity)context;
        addressBook = list;
    }

    public int getCount()
    {
        return 1 + addressBook.size();
    }

    public Address getItem(int i)
    {
        return (Address)addressBook.get(i);
    }

    public volatile Object getItem(int i)
    {
        return getItem(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        ViewHolder viewholder;
        if(view == null)
        {
            view = inflater.inflate(0x7f030005, null);
            viewholder = new ViewHolder();
            viewholder.border = (LinearLayout)view.findViewById(0x7f0b0019);
            viewholder.name = (TextView)view.findViewById(0x7f0b001a);
            viewholder.address = (TextView)view.findViewById(0x7f0b001b);
            viewholder.chooseAddress = (Button)view.findViewById(0x7f0b001c);
            viewholder.missingAddressInfo = (TextView)view.findViewById(0x7f0b001d);
            viewholder.missingAddressInfo.setMovementMethod(LinkMovementMethod.getInstance());
            view.setTag(viewholder);
        } else
        {
            viewholder = (ViewHolder)view.getTag();
        }
        if(i == addressBook.size())
        {
            viewholder.name.setVisibility(8);
            viewholder.address.setVisibility(8);
            viewholder.chooseAddress.setVisibility(8);
            viewholder.missingAddressInfo.setVisibility(0);
        } else
        {
            viewholder.name.setVisibility(0);
            viewholder.address.setVisibility(0);
            viewholder.chooseAddress.setVisibility(0);
            viewholder.missingAddressInfo.setVisibility(8);
            final Address address = getItem(i);
            viewholder.name.setText(address.getAddressName());
            viewholder.address.setText(Html.fromHtml(StringUtils.getAddressHtml(address, true, false)));
            viewholder.chooseAddress.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view1)
                {
                    final Intent intent = activity.getIntent();
                    intent.putExtra("externalAddressId", address.getAddressId());
                    if(((CheckBox)activity.findViewById(0x7f0b0017)).isChecked())
                    {
                        final String externalAddressId = address.getAddressId();
                        AsyncRequest.buildRequest(activity, activity.getHandler(), new Runnable() {

                            public void run()
                            {
                                CustomerDefault.updateDefaultAddressId(activity.getAmazonFreshBase(), externalAddressId);
                            }

                            final _cls1 this$1;
                            private final String val$externalAddressId;

                    
                    {
                        this$1 = _cls1.this;
                        externalAddressId = s;
                        super();
                    }
                        }
).setFailureAction(new com.demiroot.amazonfresh.Async.OnExceptionAction() {

                            public boolean onException(Exception exception)
                            {
                                boolean flag;
                                if(exception instanceof FreshAPIException)
                                {
                                    String s = ((FreshAPIException)exception).getReason();
                                    Toast.makeText(activity, s, 0).show();
                                    flag = false;
                                } else
                                {
                                    flag = true;
                                }
                                return flag;
                            }

                            final _cls1 this$1;

                    
                    {
                        this$1 = _cls1.this;
                        super();
                    }
                        }
).setSuccessAction(new Runnable() {

                            public void run()
                            {
                                Toast.makeText(activity, activity.getString(0x7f0700d3), 0).show();
                                activity.setResult(-1, intent);
                                activity.finish();
                            }

                            final _cls1 this$1;
                            private final Intent val$intent;

                    
                    {
                        this$1 = _cls1.this;
                        intent = intent1;
                        super();
                    }
                        }
).setWaitMessage(activity.getString(0x7f0700d4)).execute();
                    } else
                    {
                        activity.setResult(-1, intent);
                        activity.finish();
                    }
                }

                final AddressBookAdapter this$0;
                private final Address val$address;


            
            {
                this$0 = AddressBookAdapter.this;
                address = address1;
                super();
            }
            }
);
        }
        return view;
    }

    AFBaseActivity activity;
    List addressBook;
    LayoutInflater inflater;
}
