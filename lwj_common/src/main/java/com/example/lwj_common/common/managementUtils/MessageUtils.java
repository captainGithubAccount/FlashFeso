package com.example.lwj_common.common.managementUtils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MessageUtils {

    private static Uri SMS_URI_ALL= Uri.parse("content://sms/");

    @SuppressLint("Range")
    public static List<MessageBean> getMessage(Context context) {

        ContentResolver cr = context.getContentResolver();
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        Cursor cur = cr.query(SMS_URI_ALL, projection, null, null, "date desc");
        if (null == cur) {
            Log.i("ooc", "************cur == null");
            return null;
        }
        List<MessageBean> messageBeanList = new ArrayList<>();
        while (cur.moveToNext()) {
             String address = cur.getString(cur.getColumnIndex("address"));//发件人地址，即手机号，如+8613811810000
            String person = cur.getString(cur.getColumnIndex("person"));//发件人，若是发件人在通信录中则为具体姓名，陌生人为null
            String body = cur.getString(cur.getColumnIndex("body"));//短信具体内容
            String date = cur.getString(cur.getColumnIndex("date"));//日期,long型
            String type = cur.getString(cur.getColumnIndex("type"));//type:短信类型1是接收到的，2是已发出
            //至此就得到了短信的相关的内容, 如下是把短信加入map中，构建listview,非必要。
            MessageBean messageBean = new MessageBean();

            messageBean.setMessageDateTime(date);
            messageBean.setMessageContent(body);
            messageBean.setName(person);
            messageBean.setType(type);
            messageBean.setAddress(address);
            messageBeanList.add(messageBean);
        }
        return messageBeanList;
    }

    /**
     * String number = cur.getString(cur.getColumnIndex("想得到的属性")); //获取方法
     * sms主要结构：
     *  _id：短信序号，如100
     *  thread_id：对话的序号，如100，与同一个手机号互发的短信，其序号是相同的
     *  address：发件人地址，即手机号，如+8613811810000
     *  person：发件人，若是发件人在通信录中则为具体姓名，陌生人为null
     *  date：日期，long型，如1256539465022，能够对日期显示格式进行设置
     *  protocol：协议0SMS_RPOTO短信，1MMS_PROTO彩信
     *  read：是否阅读0未读，1已读
     *  status：短信状态-1接收，0complete,64pending,128failed
     *  type：短信类型1是接收到的，2是已发出
     *  body：短信具体内容
     *  service_center：短信服务中心号码编号，如+8613800755500
     */
}
