package com.rgs.common;

public interface IVector {
    double getLength();
    IVector normalize();
    IVector scale(double scale);
}
