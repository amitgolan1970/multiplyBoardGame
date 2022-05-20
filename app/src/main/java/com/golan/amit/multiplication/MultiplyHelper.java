package com.golan.amit.multiplication;

import android.util.Log;

public class MultiplyHelper {

    public static final int HIGHLIMMIT = 10;
    public static final int HIGHERLIMMIT = 20;
    public static final int ROUNDS = 10;
    public static final int ALLOWEDFAILATTEMPTS = 3;


    public static final String BEGINNER = "beginner";
    public static final String NOVICE = "novice";
    public static final String EXPERT = "expert";

    private String _mode;
    private int _leftNum;
    private int _rightNum;
    private int _roundCounter;
    private int _fails;
    private int _total_fails;

    public MultiplyHelper() {
        set_mode(NOVICE); set_roundCounter(1);
    }

    public String get_mode() {
        return _mode;
    }

    public void set_mode(String _mode) {
        this._mode = _mode;
    }

    public int get_leftNum() {
        return _leftNum;
    }

    public void set_leftNum(int _leftNum) {
        this._leftNum = _leftNum;
    }

    public int get_rightNum() {
        return _rightNum;
    }

    public void set_rightNum(int _rightNum) {
        this._rightNum = _rightNum;
    }

    public void generateNums() {
        _leftNum = (int)(Math.random() * (HIGHERLIMMIT - 2)) + 2;
        _rightNum = (int)(Math.random() * (HIGHLIMMIT - 2)) + 2;
        if(get_mode().equals(BEGINNER)) {
            _leftNum = (int)(Math.random() * (HIGHLIMMIT - 2)) + 2;
        } else if(get_mode().equals(EXPERT)) {
            _rightNum = (int)(Math.random() * (HIGHERLIMMIT - 2)) + 2;
        }
        if(MainActivity.DEBUG) {
            Log.d(MainActivity.DEBUGTAG, "mode: " + get_mode() + ", left: " + get_leftNum() + ", right: " + get_rightNum());
        }
    }

    public int result() {
        return _leftNum * _rightNum;
    }

    public  String formattedRepresentation() {
        return "" + _roundCounter + ") " + _leftNum + " * " + _rightNum + " = ";
    }

    public int get_roundCounter() {
        return _roundCounter;
    }

    public void set_roundCounter(int _roundCounter) {
        this._roundCounter = _roundCounter;
    }

    public void increaseRoundCounter() { this._roundCounter++; }

    public void resetRoundCounter() {
        set_roundCounter(1);
    }

    /**
     * Fails section
     * @return
     */

    public int get_total_fails() {
        return _total_fails;
    }

    public void set_total_fails(int _total_fails) {
        this._total_fails = _total_fails;
    }

    public void increaseTotalFails() { this._total_fails++; }

    public void resetTotalFails() { this._total_fails = 0; }

    public int get_fails() {
        return _fails;
    }

    public void set_fails(int _fails) {
        this._fails = _fails;
    }

    public void increaseFails() { this._fails++; }

    public void resetFails() { this._fails = 0; }
}
