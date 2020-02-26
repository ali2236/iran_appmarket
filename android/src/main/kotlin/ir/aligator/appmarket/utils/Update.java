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

public class Update {
	private boolean isUpdateAvailable;
	private String description;
	private int versionCode;

	public Update(boolean isUpdateAvailable, String description, int versionCode) {
		this.isUpdateAvailable = isUpdateAvailable;
		this.description = description;
		this.versionCode = versionCode;
	}

	public boolean isUpdateAvailable() {
		return isUpdateAvailable;
	}

	public String getDescription() {
		return description;
	}

	public int getVersionCode() {
		return versionCode;
	}
}
