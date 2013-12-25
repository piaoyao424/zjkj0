package com.iawu.msgcenter;


public class EventArg {
	private Object[] args = null;

	public EventArg() {
	}

	public EventArg(Object... args) {
		this.args = args;
	}

	public int getArgLength() {
		if (args == null) {
			return 0;
		} else {
			return args.length;
		}
	}

	public Object getArg(int index) {
		if (args == null)
			return null;
		if(index<0)
			return null;
		if (args.length <= index) {
			return null;
		} else {
			return args[index];
		}
	}

	public boolean hasArg() {
		return args.length > 0;
	}

	@Override
	public String toString() {
		if (args == null)
			return "";
		return args.toString();
	}
}