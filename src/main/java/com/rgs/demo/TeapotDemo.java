package com.rgs.demo;

import javax.swing.*;

import com.rgs.render3d.Teapot;

public class TeapotDemo {

    public static void main(String[] args) {
        DemoRenderLoop gameLoop = new DemoRenderLoop(new Teapot());
        new Timer(1000 / 30, gameLoop).start();
    }
}
