package com.oxygen.wechat.button;

import com.oxygen.wechat.util.HttpRequestUtil;
import com.oxygen.wechat.util.TokenUtil;
import net.sf.json.JSONObject;

import java.beans.Visibility;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestButton {
    public static void main(String[] args) {

        String weChaturl = "http://www.qcopen.cn:8081";
        //String weChaturl = "http://3cf06dfb.r10.cpolar.top";
        //创建一级菜单
        Button button = new Button();
        List<AbstractButton> buttons = new ArrayList<>();


//        subButtons.add(new ViewButton("我要还",weChaturl+"/weChat/getOpenIdByReturn"));
//        subButtons.add(new ViewButton("我要买",weChaturl+"/weChat/getOpenIdByPurchase"));
        //subButtons1.add(new ViewButton("订单详情",weChaturl+"/weChat/getOpenId"));


        //一级菜单中的第二个按钮http://www.qcopen.cn/#/login
        ViewButton viewButton = new ViewButton("倾城之旅",weChaturl+"/weChat/getOpenIdByBorrow");
        //一级菜单中的第三个按钮

        SubButton sub_button = new SubButton("制氧机");
        List<AbstractButton> subButtons = new ArrayList<>();
        sub_button.setSubButton(subButtons);
        //二级菜单的第一个按钮
        subButtons.add(new ViewButton("我要借",weChaturl+"/weChat/getOpenIdByBorrow"));
        //subButtons.add(new ViewButton("我要还",weChaturl+"/weChat/getOpenIdByReturn"));
 //       subButtons.add(new ViewButton("我要买",weChaturl+"/weChat/getOpenIdByPurchase"));
        subButtons.add(new ViewButton("订单详情",weChaturl+"/weChat/getOpenId"));
        //subButtons.add(new ViewButton("订单详情",weChaturl+"/weChat/getOpenId"));

        //一级菜单中的第二个按钮
        SubButton sub_buttona = new SubButton("后台管理员");
        List<AbstractButton> subButtons1 = new ArrayList<>();
        sub_buttona.setSubButton(subButtons1);
        //二级菜单的第一个按钮
        subButtons1.add(new ViewButton("我的订单",weChaturl+"/weChat/getOpenIdByPttion"));
        subButtons1.add(new ViewButton("制氧机入库",weChaturl+"/weChat/getOpenIdByPttionScan"));
        //把一级菜单的三个按钮添加到集合中

        //buttons.add(viewButton);

        buttons.add(sub_button);
        buttons.add(sub_buttona);

        button.setButton(buttons);

        //转换成json字符串
        JSONObject jsonObject = JSONObject.fromObject(button);
        String jsonString = jsonObject.toString();
        System.out.println(jsonString);
        String url = String.format("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s", TokenUtil.getAccessToken());

        //发送请求
        System.out.println(url);
        String result = HttpRequestUtil.doPostToJson(url, jsonString);
        System.out.println(result);


    }
}
