package com.du.codewarriorcontact.settings;

import com.du.codewarriorcontact.settings.IStepServiceCallback;

interface IStepService {
		boolean isRunning();
		void setSensitivity(int sens);
		void registerCallback(IStepServiceCallback cb);
		void unregisterCallback(IStepServiceCallback cb);
}