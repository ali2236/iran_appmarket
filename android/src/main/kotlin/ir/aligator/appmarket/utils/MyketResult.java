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

public class MyketResult {
    int mResponse;
    String mMessage;

    public MyketResult(int response, String message) {
        mResponse = response;
        if (message == null || message.trim().length() == 0) {
            mMessage = MyketSupportHelper.getResponseDesc(response);
        } else {
            mMessage = message + " (response: " + MyketSupportHelper.getResponseDesc(response) + ")";
        }
    }

    public int getResponse() {
        return mResponse;
    }

    public String getMessage() {
        return mMessage;
    }

    public boolean isSuccess() {
        return mResponse == MyketSupportHelper.RESPONSE_RESULT_OK;
    }

    public boolean isFailure() {
        return !isSuccess();
    }

    public String toString() {
        return "MyketResult: " + getMessage();
    }
}

