package com.wift.objects;

import com.wift.utils.CantOpenCloseException;
import com.wift.utils.Entity;

public interface OpenCloseble {
    public void close(Entity by) throws CantOpenCloseException;
    public void open(Entity by) throws CantOpenCloseException;
    public boolean isOpen();
}
