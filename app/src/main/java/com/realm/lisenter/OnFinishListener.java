package com.realm.lisenter;

public interface OnFinishListener<T> {
	void onSuccess(T t);

	void onFailure(String message);
}
