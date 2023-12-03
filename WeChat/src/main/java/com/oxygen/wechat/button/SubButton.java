package com.oxygen.wechat.button;

import java.util.List;

public class SubButton extends AbstractButton{
    public SubButton(String name) {
        super(name);
    }

    private List<AbstractButton> sub_button;

    public List<AbstractButton> getSub_button() {
        return sub_button;
    }

    public void setSubButton(List<AbstractButton> sub_button) {
        this.sub_button = sub_button;
    }
}
