package com.corners.game;

public interface ActionResolver {
    public void showToast(CharSequence toastMessage, int toastDuration);
    public void showAlertBox(String alertBoxTitle, String alertBoxMessage, String alertBoxButtonText);
}
