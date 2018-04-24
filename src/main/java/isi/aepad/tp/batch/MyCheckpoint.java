package isi.aepad.tp.batch;

import java.io.Serializable;

public class MyCheckpoint implements Serializable {
	private int lastId = 0;

	public void setLastId(int i) {
		lastId=i;
	}

	public int lastId() {
		return lastId;
	}
}