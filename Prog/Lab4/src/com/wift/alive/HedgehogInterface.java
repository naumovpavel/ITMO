package com.wift.alive;

import com.wift.utils.Direction;

interface HedgehogInterface extends AliveInterface{
    public void wriggle(Direction derction);
    public void squint(States how);

}
