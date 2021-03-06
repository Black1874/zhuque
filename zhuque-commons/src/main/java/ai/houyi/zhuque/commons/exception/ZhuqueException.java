/*
 * Copyright 2017-2019 The OpenAds Project
 *
 * The OpenAds Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package ai.houyi.zhuque.commons.exception;

/**
 * 自定义异常
 * 
 * @author weiping wang
 */
@SuppressWarnings("serial")
public class ZhuqueException extends RuntimeException {
	private int code;

	public ZhuqueException() {
		super();
	}

	public ZhuqueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ZhuqueException(String message, Throwable cause) {
		super(message, cause);
	}

	public ZhuqueException(String message) {
		super(message);
	}

	public ZhuqueException(Throwable cause) {
		super(cause);
	}

	public ZhuqueException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
