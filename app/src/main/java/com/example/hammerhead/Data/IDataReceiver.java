package com.example.hammerhead.Data;

import com.example.hammerhead.Models.IItem;

import java.util.List;

public interface IDataReceiver {

    void dataReceived(List<IItem> data);
}
