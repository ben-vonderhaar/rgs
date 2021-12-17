package com.rgs.vector;

public interface IVector {
    double getLength();
    IVector normalize();
    IVector scale(double scale);
}
