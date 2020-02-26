/* Copyright (c) 2015 Myket Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ir.aligator.appmarket.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.myket.api.IMyketDeveloperApi;

/**
 * Myket Developer API support (MDAS) is a tool to provide convenience methods
 * for checking update of your app. You can create one instance of this class
 * for your application and use it in onCreate of your MainActivity to become
 * notify of the update states. Also, if you want to know the purchase status
 * of your app in Myket, you can implement
 * <a href="https://developer.android.com/google/play/licensing/index.html">
 * Google App Licensing</a> with Myket intent. You can find the
 * TODO: Complete below link
 * documentation in <a href="">Myket Licensing Document</a>.
 * <p/>
 * This class provides synchronous (blocking) and asynchronous (non-blocking)
 * methods, as well as automatic signature verification.
 * <p/>
 * After instantiating, you must perform setup in order to start using the
 * object. To perform setup, call the {@link #startSetup} method and provide a
 * listener; that listener will be notified when setup is complete, after which
 * (and not before) you may call other methods.
 * When you are done with this object, don't forget to call {@link #dispose} to
 * ensure proper cleanup. This object holds a binding to the Myket developer api
 * service, which will leak unless you dispose of it correctly. If you created
 * the object on an Activity's onCreate method, then the recommended place to
 * dispose of it is the Activity's onDestroy method.
 * <p/>
 * A note about threading: When using this object from a background thread, you
 * may call the blocking versions of methods; when using from a UI thread, call
 * only the asynchronous versions and handle the results via callbacks. Also,
 * notice that you can only call one asynchronous operation at a time;
 * attempting to start a second asynchronous operation while the first one has
 * not yet completed will result in an exception being thrown.
 */
public class MyketSupportHelper {

	// Developer API response codes
	public static final int RESPONSE_RESULT_OK = 0;
	public static final int RESPONSE_RESULT_UNAVAILABLE = 1;
	public static final int RESPONSE_RESULT_DEVELOPER_ERROR = 2;
	public static final int RESPONSE_RESULT_ERROR = 3;
	// Response code
	public static final String RESPONSE_CODE = "RESPONSE_CODE";
	public static final String RESPONSE_APP_UPDATE_AVAILABLE = "RESPONSE_APP_UPDATE_AVAILABLE";
	public static final String RESPONSE_APP_UPDATE_DESCRIPTION = "RESPONSE_APP_UPDATE_DESCRIPTION";
	public static final String RESPONSE_APP_VERSION_CODE = "RESPONSE_APP_VERSION_CODE";
	// Myket support Helper error codes
	public static final int MYKET_HELPER_ERROR_BASE = -2000;
	public static final int MYKET_REMOTE_EXCEPTION = -2001;
	public static final int MYKET_BAD_RESPONSE = -2002;

	public static final String MYKET_PACKAGE_NAME = "ir.mservices.market";
	// the version of this helper
	private static final int DEVELOPER_API_VERSION = 1;
	private static String mDebugTag = "MyketSupportHelper";
	// TODO: It should be removed because mContext has the packageName
	public final String mPackageName;
	// Context we were passed during initialization
	private final Context mContext;
	// Is debug logging enabled?
	private boolean mDebugLog = true;
	// Is setup done?
	private boolean mSetupDone = false;
	// Is an asynchronous operation in progress?
	// (only one at a time can be in progress)
	private boolean mAsyncInProgress = false;
	// (for logging/debugging)
	// if mAsyncInProgress == true, what asynchronous operation is in progress?
	private String mAsyncOperation = "";
	// Connection to the service
	private IMyketDeveloperApi mService;
	private ServiceConnection mServiceConn;

	/**
	 * Creates an instance. After creation, it will not yet be ready to use. You
	 * must perform setup by calling {@link #startSetup} and wait for setup to
	 * complete. This constructor does not block and is safe to call from a UI
	 * thread.
	 *
	 * @param ctx Your application or Activity context. Needed to bind to the
	 *            Myket developer api service.
	 */
	public MyketSupportHelper(Context ctx) {
		this.mContext = ctx.getApplicationContext();
		this.mPackageName = mContext.getPackageName();
		logDebug("MDAS helper created.");
	}

	/**
	 * Returns a human-readable description for the given response code.
	 *
	 * @param code The response code
	 * @return A human-readable string explaining the result code. It also
	 * includes the result code numerically.
	 */
	public static String getResponseDesc(int code) {
		String[] mdas_msgs = ("0:OK/1:Support unavailable/2:Error/").split("/");
		String[] mdas_helper_msgs = ("0:OK/-2001:Remote exception/-2002:Bad response received/".split("/"));

		if (code <= MYKET_HELPER_ERROR_BASE) {
			int index = MYKET_HELPER_ERROR_BASE - code;
			if (index >= 0 && index < mdas_helper_msgs.length)
				return mdas_helper_msgs[index];
			else
				return String.valueOf(code) + ":Unknown MDAS Helper Error";
		} else if (code < 0 || code >= mdas_msgs.length)
			return String.valueOf(code) + ":Unknown";
		else
			return mdas_msgs[code];
	}

	/**
	 * Starts the setup process. This will start up the setup process
	 * asynchronously. You will be notified through the listener when the setup
	 * process is complete. This method is safe to call from a UI thread.
	 *
	 * @param listener The listener to notify when the setup process is complete.
	 */
	public void startSetup(final MyketSupportHelper.OnMyketSetupFinishedListener listener) {
		// If already set up, can't do it again.
		if (mSetupDone)
			throw new IllegalStateException("MDAS helper is already set up.");

		// Connection to MDAS service
		logDebug("Starting Myket developer api setup.");

		Intent serviceIntent = new Intent("ir.mservices.market.MyketDeveloperApiService.BIND");
		serviceIntent.setPackage(MYKET_PACKAGE_NAME);
		if (!mContext.getPackageManager().queryIntentServices(serviceIntent, 0).isEmpty()) {
			mServiceConn = new ServiceConnection() {
				@Override
				public void onServiceDisconnected(ComponentName name) {
					logDebug("Support service disconnected.");
					mService = null;
				}

				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
					logDebug("Myket developer support service connected.");
					mService = IMyketDeveloperApi.Stub.asInterface(service);
					try {
						logDebug("Checking for Myket developer version 1 support.");

						// check for Myket developer version 1 support
						int response = mService.isDeveloperApiSupported(DEVELOPER_API_VERSION);
						if (response != RESPONSE_RESULT_OK) {
							// myket developer api is not supported
							if (listener != null)
								listener.onMyketSetupFinished(new MyketResult(response,
										"Error checking for Myket developer version 1 support."));

							return;
						}
						logDebug("Myket developer version 1 supported for " + mPackageName);

						mSetupDone = true;
					} catch (RemoteException e) {
						if (listener != null) {
							listener.onMyketSetupFinished(new MyketResult(MYKET_REMOTE_EXCEPTION,
									"RemoteException while setting up Myket developer support."));
						}
						e.printStackTrace();

						new Handler().post(new Runnable() {
							@Override
							public void run() {
								dispose();
							}
						});
						return;
					}

					if (listener != null) {
						listener.onMyketSetupFinished(new MyketResult(RESPONSE_RESULT_OK, "Setup " +
								"successful" +

								"."));
					}
				}
			};

			// service available to handle that Intent
			mContext.bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
		} else {
			// no service available to handle that Intent
			if (listener != null) {
				listener.onMyketSetupFinished(new MyketResult(RESPONSE_RESULT_UNAVAILABLE,
						"Myket developer support service is unavailable on this device."));
			}
		}
	}

	/**
	 * Check if startSetup successfully finished.
	 */
	public boolean isSetupDone() {
		return mSetupDone;
	}

	/**
	 * Dispose of object, releasing resources. It's very important to call this
	 * method when you are done with this object. It will release any resources
	 * used by it such as service connections. Naturally, once the object is
	 * disposed of, it can't be used again.
	 */
	public void dispose() {
		logDebug("Disposing.");
		mSetupDone = false;
		if (mServiceConn != null) {
			logDebug("Unbinding from service.");
			if (mContext != null)
				mContext.unbindService(mServiceConn);
			mServiceConn = null;
			mService = null;
		}
	}

	/**
	 * Asynchronous wrapper for app update state. This will perform your app
	 * update state as described in {@link #getAppUpdateState}, but will do so
	 * asynchronously and call back the specified listener upon completion. This
	 * method is safe to call from a UI thread.
	 *
	 * @param listener The listener to notify when get state finishes.
	 */
	public void getAppUpdateStateAsync(final CheckAppUpdateListener listener) {
		final Handler handler = new Handler();
		checkSetupDone("getAppUpdateState");
		flagStartAsync("update state");
		(new Thread(new Runnable() {
			public void run() {
				MyketResult result = new MyketResult(RESPONSE_RESULT_OK, "This app is updated.");
				Update update = null;
				try {
					update = getAppUpdateState();
				} catch (MyketException ex) {
					result = ex.getResult();
				}

				flagEndAsync();

				final MyketResult finalResult = result;
				final Update finalUpdate = update;
				handler.post(new Runnable() {
					public void run() {
						listener.onCheckAppUpdateFinished(finalResult, finalUpdate);
					}
				});
			}
		})).start();
	}

	/**
	 * Provides the state fo update for your app, which can be "has update" or "updated".
	 */
	public Update getAppUpdateState() throws MyketException {
		checkSetupDone("getAppUpdateState");
		try {
			Bundle bundle = mService.getAppUpdateState(DEVELOPER_API_VERSION, mPackageName);
			if (bundle == null || !bundle.containsKey(RESPONSE_CODE) || bundle.getInt(RESPONSE_CODE) !=
					RESPONSE_RESULT_OK) {
				throw new MyketException(MYKET_BAD_RESPONSE, "Response code is not Ok");
			}

			return new Update(bundle.getBoolean(RESPONSE_APP_UPDATE_AVAILABLE),
					bundle.getString(RESPONSE_APP_UPDATE_DESCRIPTION),
					bundle.getInt(RESPONSE_APP_VERSION_CODE));
		} catch (RemoteException e) {
			throw new MyketException(MYKET_REMOTE_EXCEPTION,
					"Remote exception while getting app update state.", e);
		}
	}

	// Checks that setup was done; if not, throws an exception.
	private void checkSetupDone(String operation) {
		if (!mSetupDone) {
			logError("Illegal state for operation (" + operation + "): MDAS helper is not set up.");
			throw new IllegalStateException("MDAS helper is not set up. Can't perform operation: " +
					operation);
		}
	}

	private void flagStartAsync(String operation) {
		if (mAsyncInProgress)
			throw new IllegalStateException("Can't start async operation (" + operation
					+ ") because another async operation(" + mAsyncOperation + ") is in progress.");
		mAsyncOperation = operation;
		mAsyncInProgress = true;
		logDebug("Starting async operation: " + operation);
	}

	private void flagEndAsync() {
		logDebug("Ending async operation: " + mAsyncOperation);
		mAsyncOperation = "";
		mAsyncInProgress = false;
	}

	private void logDebug(String msg) {
		if (mDebugLog)
			Log.d(mDebugTag, msg);
	}

	private void logError(String msg) {
		Log.e(mDebugTag, "Myket developer api error: " + msg);
	}

	private void logWarn(String msg) {
		Log.w(mDebugTag, "Myket developer api warning: " + msg);
	}

	/**
	 * Enables or disable debug logging through LogCat.
	 */
	public void enableDebugLogging(boolean enable, String tag) {
		mDebugLog = enable;
		mDebugTag = tag;
	}

	public void enableDebugLogging(boolean enable) {
		mDebugLog = enable;
	}

	/**
	 * Callback for setup process. This listener's {@link #onMyketSetupFinished}
	 * method is called when the setup process is complete.
	 */
	public interface OnMyketSetupFinishedListener {
		/**
		 * Called to notify that a developer api is supported by Myket service.
		 *
		 * @param result The result of the operation.
		 */
		void onMyketSetupFinished(MyketResult result);
	}

	/**
	 * Listener that notifies when an getting update state is finished.
	 */
	public interface CheckAppUpdateListener {
		/**
		 * Called to notify that getting app update state operation completed.
		 *
		 * @param result The result of the operation.
		 * @param update The detail of the updates of app.
		 */
		void onCheckAppUpdateFinished(MyketResult result, Update update);
	}
}
