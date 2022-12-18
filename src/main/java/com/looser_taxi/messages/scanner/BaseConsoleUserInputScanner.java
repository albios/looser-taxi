package com.looser_taxi.messages.scanner;

import java.util.Scanner;

public abstract class BaseConsoleUserInputScanner<T> implements UserInputScanner<T> {
    protected Scanner scanner = new Scanner(System.in);
}
