package com.example.xm486.mode;

public class ButtonInfo {

	private int value;
	private String control_1;
	private String control_2;
	private String control_3;
	private boolean control_1Clicked;
	private boolean control_2Clicked;
	private boolean control_3Clicked;
	private int buttonNum=2;
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getControl_1() {
		return control_1;
	}
	public void setControl_1(String control_1) {
		this.control_1 = control_1;
	}
	public String getControl_2() {
		return control_2;
	}
	public void setControl_2(String control_2) {
		this.control_2 = control_2;
	}
	public String getControl_3() {
		return control_3;
	}
	public void setControl_3(String control_3) {
		this.control_3 = control_3;
	}
	public boolean isControl_1Clicked() {
		return control_1Clicked;
	}
	public void setControl_1Clicked(boolean control_1Clicked) {
		this.control_1Clicked = control_1Clicked;
	}
	public boolean isControl_2Clicked() {
		return control_2Clicked;
	}
	public void setControl_2Clicked(boolean control_2Clicked) {
		this.control_2Clicked = control_2Clicked;
	}
	public boolean isControl_3Clicked() {
		return control_3Clicked;
	}
	public void setControl_3Clicked(boolean control_3Clicked) {
		this.control_3Clicked = control_3Clicked;
	}
	public int getButtonNum() {
		return buttonNum;
	}
	public void setButtonNum(int buttonNum) {
		this.buttonNum = buttonNum;
	}

}
