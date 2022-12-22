package com.wift.objects;

import com.wift.utils.CantOpenCloseException;
import com.wift.utils.Entity;

public class Jar extends Entity implements OpenCloseble {
    protected boolean isOpen;
    public Jar(String name, boolean isOpen) {
        super(name);
        this.isOpen = isOpen;
    }

    @Override
    public void close(Entity by) throws CantOpenCloseException {
        if (isOpen()) {
            isOpen = false;
            System.out.println(by + " закрыл(-и) крышку");
        } else {
            throw new CantOpenCloseException("уже закрыто", isOpen(), this);
        }
    }

    @Override
    public void open(Entity by) throws CantOpenCloseException {
        if (!isOpen()) {
            isOpen = true;
            System.out.println(by + " открыл(-и) крышку");
        } else {
            throw new CantOpenCloseException("уже открыто", isOpen(), this);
        }
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }
}
