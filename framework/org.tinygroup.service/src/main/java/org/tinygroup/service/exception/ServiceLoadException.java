/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.service.exception;

public class ServiceLoadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 217650765317476197L;

	public ServiceLoadException() {
		super("服务装载失败！");
	}

	public ServiceLoadException(Throwable cause) {
		super(cause);
	}
}
