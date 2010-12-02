/*
 * Copyright (C) 2010 The iDroid Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server.status;

import com.android.internal.R;
import com.android.internal.util.CharSequences;

import android.os.ServiceManager;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.util.Log;

public class SoftButtons implements Runnable
{
	private View v;
	private int buttonEvent;
	
	public SoftButtons(View v)
	{
		this.v = v;
	}

	public void run()
	{
		int viewId = v.getId();
		switch(viewId) {
			case R.id.exp_idroid_btn_1:
			buttonEvent = KeyEvent.KEYCODE_CALL;
			break;
			case R.id.exp_idroid_btn_2:
			buttonEvent = KeyEvent.KEYCODE_HOME;
			break;
			case R.id.exp_idroid_btn_3:
			buttonEvent = KeyEvent.KEYCODE_BACK;
			break;
			case R.id.exp_idroid_btn_4:
			buttonEvent = KeyEvent.KEYCODE_ENDCALL;
			break;
			default:
			buttonEvent = KeyEvent.KEYCODE_BACK;
			break;			
		}
		
		press(buttonEvent);
	}
	
	public void press(int key) {
		sendKey(new KeyEvent(KeyEvent.ACTION_DOWN, key));
		sendKey(new KeyEvent(KeyEvent.ACTION_UP, key));
	}
	
	public void sendKey(KeyEvent event) {
		try {
			IWindowManager.Stub.asInterface(ServiceManager.getService("window"))
													.injectKeyEvent(event, true);
		} catch (RemoteException e) {
			Log.e(TAG, "sendKey exception " + e);
		}
	}
}